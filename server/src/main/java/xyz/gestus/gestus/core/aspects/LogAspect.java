package xyz.gestus.gestus.core.aspects;

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
import xyz.gestus.gestus.feature.logs.Log;
import xyz.gestus.gestus.feature.user.User;
import xyz.gestus.gestus.feature.logs.LogRepository;
import xyz.gestus.gestus.feature.user.UserRepository;
import xyz.gestus.gestus.core.security.JwtTokenProvider;

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
    public void callAt(xyz.gestus.gestus.core.annotations.Log log) {}

    @Around(value = "callAt(log)", argNames = "pjp,log")
    public Object around(ProceedingJoinPoint pjp, xyz.gestus.gestus.core.annotations.Log log) throws Throwable {
        Object result = pjp.proceed();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found")); // Предполагается, что у вас есть метод для получения пользователя по email

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        Log logEntity = new Log();
        logEntity.setName(log.name());
        logEntity.setDate(new Date());
        logEntity.setUser(user);
        logRepository.save(logEntity);

        return result;
    }
}
