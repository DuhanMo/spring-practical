package practical.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import practical.aop.dto.User;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
public class DecodeAop {

    @Pointcut("execution(* practical.aop.controller..*.*(..))")
    private void cut() {
    }

    @Pointcut("@annotation(practical.aop.annotation.Decode)")
    private void enableDecode() {

    }

    @Before("cut() && enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof User) {
                User user = User.class.cast(arg);
                String base64Email = user.getEmail();
                byte[] decodeEmailByte = Base64.getDecoder().decode(base64Email);
                String email = new String(decodeEmailByte, "UTF-8");
                user.setEmail(email);
            }
        }
    }

    @AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        if (returnObj instanceof User) {
            User user = User.class.cast(returnObj);
            String email = user.getEmail();
            String encodeEmail = Base64.getEncoder().encodeToString(email.getBytes());
            user.setEmail(encodeEmail);
        }
    }
}
