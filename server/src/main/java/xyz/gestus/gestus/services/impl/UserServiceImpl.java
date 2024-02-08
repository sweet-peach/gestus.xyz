package xyz.gestus.gestus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.gestus.gestus.dto.RegistrationRequestDto;
import xyz.gestus.gestus.models.Role;
import xyz.gestus.gestus.models.UserModel;
import xyz.gestus.gestus.repositories.UserRepository;
import xyz.gestus.gestus.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegistrationRequestDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new UsernameNotFoundException("A username associated with this email already exists");
        }

        UserModel userModel = new UserModel();
        userModel.setFirstName(registerDto.getFirstName());
        userModel.setLastName(registerDto.getLastName());
        userModel.setEmail(registerDto.getEmail());
        userModel.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userModel.setRole(Role.valueOf(registerDto.getRole()));

        userRepository.save(userModel);
    }
}
