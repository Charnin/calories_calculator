package com.example.caloriescalculator;

import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser register(RegistrationForm form) {
        String normalizedEmail = form.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException("อีเมลนี้ถูกใช้งานแล้ว");
        }
        return userRepository.save(new AppUser(
                form.getDisplayName().trim(), normalizedEmail, passwordEncoder.encode(form.getPassword())));
    }

    public AppUser requireByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalStateException("ไม่พบบัญชีผู้ใช้ที่ล็อกอิน"));
    }

    @Transactional
    public void updateDisplayName(AppUser user, String displayName) {
        user.updateDisplayName(displayName);
        userRepository.save(user);
    }
}
