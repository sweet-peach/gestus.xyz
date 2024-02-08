package xyz.gestus.gestus.services.impl;

import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.RegistrationRequestDto;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void register(RegistrationRequestDto registrationRequestDto) {
        System.out.println("New register request");
    }
}
