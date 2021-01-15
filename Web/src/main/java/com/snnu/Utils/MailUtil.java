package com.snnu.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


@Component
public class MailUtil {

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送邮件
     *
     * @param to   收件人
     * @param code 验证码
     * @return
     */
    public boolean sendMail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setFrom("2682003285@qq.com");
            message.setTo(to);
            message.setSubject("摔倒检测及运动饮食推荐邮箱激活");
            message.setText("您好，欢迎注册SeniorGuard！请点击以下链接确认激活您的邮箱<a href='https://[" + getIPV6Address() +
                    "]:8080/FallDetection/registerActive/"+code+"'></a>");

            //System.out.println(message);
            mailSender.send(message);
            System.out.println("发送邮件成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
            return false;
        }
    }

    public boolean sendFallMail(String to, String content, File file, String linkUrl) {

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = null;
        try {
            // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
            // multipart模式
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 设置寄件人，收件人
            messageHelper.setFrom("2682003285@qq.com");
            messageHelper.setTo(to);
            messageHelper.setSubject("Senior Guard摔倒信息通知");
            // true表示启动HTML格式的邮件   onclick="javascript:location.href='www.baidu.com'"
            messageHelper.setText("<font size='3'>"+content+"</font>"+"<a href='"+linkUrl+"'><img src=\"cid:staticMap\" /></a>",true);
            //FileSystemResource img = new FileSystemResource(file);
            messageHelper.addInline("staticMap", file);

            mailSender.send(message);
            System.out.println("发送邮件成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean sendCustomMail(String to, String subject, String content){
        boolean flag = false;
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = null;

        // multipart模式
        try {
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 设置寄件人，收件人
            messageHelper.setFrom("2682003285@qq.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(message);
            System.out.println("发送自定义邮件成功");
            flag = true;
        } catch (MessagingException e) {
            flag = false;
            e.printStackTrace();
        }finally {
            return flag;
        }
    }

    public String getIPV4Address() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();

    }

    public String getIPV6Address() throws SocketException {
        InetAddress address = null;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        Res: while (interfaces.hasMoreElements()){
            NetworkInterface anInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = anInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                address = addresses.nextElement();
                if (address instanceof Inet6Address && ! isReservedAddr(address)){
                    break Res;
                }
            }
        }
        String ipAddr = address.getHostAddress();
        int index = ipAddr.indexOf('%');
        if (index > 0) {
            ipAddr = ipAddr.substring(0, index);
        }
        return ipAddr;
    }

    private boolean isReservedAddr(InetAddress inetAddress){
        if (inetAddress.isAnyLocalAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isLoopbackAddress()){
            return true;
        }
        return false;
    }
}
/*// true表示启动HTML格式的邮件
        messageHelper.setText(" <html><head></head><body><h1>hello!!springimagehtmlmail</h1> " +
                " <imgsrc=\ "cid:aaa\" /></body></html> ", true );

        FileSystemResourceimg = new FileSystemResource(new File(" g:/123.jpg "));

        messageHelper.addInline(" aaa ", img);

        senderImpl.setUsername(" username "); // 根据自己的情况,设置username
        senderImpl.setPassword(" password "); // 根据自己的情况,设置password
        Propertiesprop = new Properties();
        prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put(" mail.smtp.timeout ", " 25000 ");
        senderImpl.setJavaMailProperties(prop);

        // 发送邮件
        senderImpl.send(mailMessage);

        System.out.println(" 邮件发送成功.. ");


        try {
            message.setFrom("2682003285@qq.com");
            message.setTo(to);
            message.setSubject("摔倒检测及运动饮食推荐邮箱激活");
            message.setText("您好，欢迎注册FallDetection！请点击以下链接确认激活您的邮箱<a href='http://" + getIPV4Address() +
                    ":8080/FallDetection/'</a>");

            //System.out.println(message);
            mailSender.send(message);
            System.out.println("发送邮件成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
            return false;
        }*/