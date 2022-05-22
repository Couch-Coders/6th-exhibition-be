package couch.exhibition.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // couch.exhibition.service 및 하위 패키지의 모든 메서드
    @Pointcut("execution(* couch.exhibition.service..*(..))")
    public void ExService(){} // pointcut signature (method name + parameter)

    // couch.exhibition.controller 및 모든 하위 패키지의 모든 메서드
    @Pointcut("execution(* couch.exhibition.controller..*(..))")
    public void ExController(){}
}