package com.snnu.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogUtils {

    /**
     * 告诉Spring每个方法都什么时候运行
     * try{
     *      @Before 前置通知
     *     method.invoke(obj, args);
     *      @AfterReturning 后置通知
     * }catch(e){
     *     @AfterThrowing 返回通知
     * }finally{
     *     @After 异常通知
     * }
     * @Around 环绕通知
     *
     * execution(访问权限符 返回值类型 方法签名)
     */
    /**
     * 抽取可重用的切入点表达式
     * 1.随便声明一个没有实现的返回void的空方法
     * 2.给方法上标注@Pointcut注解
     */
    @Pointcut("execution(public * com.snnu.Controller.UserController.*(..))")
    public void myPoint() {

    }

    /**
     * 拿到目标方法的详细信息
     * 1.参数列表写一个参数：JoinPoint joinPoint：封装了当前目标方法的详细信息
     */
    @Before("myPoint()")
    public static void logStart(JoinPoint joinPoint) {
        //获取到目标方法运行时使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println(name + "方法开始执行，参数为" + Arrays.toString(args));
    }

    //Spring对通知方法的要求不严格，可以有返回值，private
    //唯一要求是方法的参数列表不能乱写，通知方法是Spring利用反射调用，要确定这个方法的参数表的值
    @AfterReturning(value = "myPoint()", returning = "result")
    public static void logReturn(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法正常执行完成，结果为" + result);
    }

    @AfterThrowing(value = "myPoint()", throwing = "exception")
    public static void logException(JoinPoint joinPoint, Exception exception) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法执行抛出异常，异常信息是" + exception);
    }

    @After(value = "myPoint()")
    public static void logEnd(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法执行结束");
    }
}
