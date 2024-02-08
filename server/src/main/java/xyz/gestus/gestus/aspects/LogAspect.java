package xyz.gestus.gestus.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.gestus.gestus.annotations.Log;
import xyz.gestus.gestus.models.LogModel;
import xyz.gestus.gestus.models.UserModel;
import xyz.gestus.gestus.repositories.LogRepository;
import xyz.gestus.gestus.repositories.UserRepository;
import xyz.gestus.gestus.security.JwtTokenProvider;

import java.util.Date;

@Aspect
@Component
public class LogAspect {

    private LogRepository logRepository;
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;

    @Autowired
    public LogAspect(LogRepository logRepository,JwtTokenProvider tokenProvider, UserRepository userRepository){
        this.logRepository = logRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Pointcut("@annotation(log)")
    public void callAt(Log log) {}

    @Around(value = "callAt(log)", argNames = "pjp,log")
    public Object around(ProceedingJoinPoint pjp, Log log) throws Throwable {
        Object result = pjp.proceed();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        UserModel user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found")); // Предполагается, что у вас есть метод для получения пользователя по email

        LogModel logEntity = new LogModel();
        logEntity.setName(log.name());
        logEntity.setDate(new Date());
        logEntity.setUser(user);
        logRepository.save(logEntity);

        return result;
    }
}
