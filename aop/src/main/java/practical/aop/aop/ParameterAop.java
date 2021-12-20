package practical.aop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ParameterAop {

    @Pointcut("execution(* practical.aop.controller..*.*(..))")
    private void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinpoint) {
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("methodSignature.getName()={}", methodSignature.getName());
        Object[] args = joinpoint.getArgs();
        for (Object obj : args) {
            log.info("type={}", obj.getClass().getSimpleName());
            log.info("value={}", obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        log.info("return object={}", returnObj);
    }
}
