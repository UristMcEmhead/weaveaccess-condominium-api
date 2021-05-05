package com.waveaccess.condominiumapi.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordMapper {

    private final PasswordEncoder passwordEncoder;

    @Named("pass-hash")
    public String passHash(String password) {
        return passwordEncoder.encode(password);
    }

}
