package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserAccountServiceTest {
    @Autowired private AppUserRepository userRepository;
    @Autowired private UserAccountService accountService;

    @Test
    void updatesDisplayNamePersistently() {
        AppUser user = userRepository.save(new AppUser("ชื่อเดิม", "profile-test@example.com", "hash"));

        accountService.updateDisplayName(user, "ชื่อใหม่");

        assertThat(userRepository.findById(user.getId()).orElseThrow().getDisplayName()).isEqualTo("ชื่อใหม่");
    }
}
