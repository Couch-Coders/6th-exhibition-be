package couch.exhibition.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExhibitionPointcut {

    @Around("couch.exhibition.aop.Pointcuts.ExService()")
    public Object showTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[transaction start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[transaction commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[transaction rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[transaction end] {}", joinPoint.getSignature());
        }
    }

    @Around("couch.exhibition.aop.Pointcuts.ExService() || couch.exhibition.aop.Pointcuts.ExController()")
    public Object showLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
