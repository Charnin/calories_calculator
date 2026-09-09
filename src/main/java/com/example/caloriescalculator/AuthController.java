package com.example.caloriescalculator;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserAccountService accountService;

    public AuthController(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registration", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") RegistrationForm registration,
                           BindingResult bindingResult) {
        if (!registration.getPassword().equals(registration.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "รหัสผ่านทั้งสองช่องไม่ตรงกัน");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            accountService.register(registration);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("email", "email.duplicate", exception.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }
}
