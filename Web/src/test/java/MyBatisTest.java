import com.snnu.Controller.ContactController;
import com.snnu.Controller.UserController;
import com.snnu.Dao.UserDao;
import com.snnu.POJO.Contact;
import com.snnu.POJO.User;
import com.snnu.Utils.MailUtil;
import com.snnu.Utils.RandomUUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class MyBatisTest {
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    UserController userController;
    @Autowired
    MailUtil mailUtil;
    @Autowired
    ContactController contactController;


    public void initSqlSessionFactory() throws IOException {

        //1.根据全局配置文件创建出一个SqlSessionFactory
        //SqlSessionFactory：是SqlSession工厂，负责创建SqlSession对象
        //SqlSession：sql会话（代表和数据库的一次会话）
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws IOException {

        User userByUID = null;

        SqlSession sqlSession = null;
        try {
            //2.获取和数据库的一次会话:getConnection()
            sqlSession = sqlSessionFactory.openSession();

            //3.使用SqlSession操作数据库,获取dao接口的实现
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userByUID = userDao.getUserByUID(1);
        } finally {
            sqlSession.close();
        }

        System.out.println(userByUID);
    }

    /*@Test
    public void loginTest(){
        System.out.println(userController.login("13566667770", "123"));
    }*/

    @Test
    public void UUid(){
        RandomUUID randomUUID = new RandomUUID();
        for (int i = 0; i < 5; i++) {
            System.out.println(randomUUID.getRandomUUID());
        }
    }

    @Test
    public void IPAddress(){

        try {
//            System.out.println(new MailUtil().getIPV4Address());
            mailUtil.sendMail("750211923@qq.com","123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void register(){
//        if (userController.isExist("李四")) {
//            System.out.println("数据库有这个人");
//        }else System.out.println("数据库没有这个人");
        /*int uid, String username, String pwd, int gender, int role, String email,
         String phone, String address, Date birthdate, String remarks, String uuid*/
        String result = userController.register(new User(0, "李四", "132", 1, 62, 0, "42134258@qq.com", "13503422658", "test", new Date(), "", ""));
        System.out.println(result);
    }

    @Test
    public void mathTest(){
        int time = (int)(375/27.0*1000);
        System.out.println(375/27.0+"    "+time);
    }

    @Test
    public void contactTest(){
        Contact contact = new Contact(1,"李明","3038375568@qq.com","12312312312");
        //int result = contactController.addContact();
        //System.out.println(result);
    }
}
