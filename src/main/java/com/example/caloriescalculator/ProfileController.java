package com.example.caloriescalculator;

import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    private final UserAccountService accountService;
    private final CalculationRecordRepository recordRepository;

    public ProfileController(UserAccountService accountService, CalculationRecordRepository recordRepository) {
        this.accountService = accountService;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        ProfileForm form = new ProfileForm();
        form.setDisplayName(user.getDisplayName());
        model.addAttribute("account", form);
        addModel(model, user);
        return "profile";
    }

    @PostMapping("/profile")
    public String update(@Valid @ModelAttribute("account") ProfileForm form,
                         BindingResult bindingResult, Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        if (bindingResult.hasErrors()) {
            addModel(model, user);
            return "profile";
        }
        accountService.updateDisplayName(user, form.getDisplayName());
        return "redirect:/profile?saved";
    }

    private void addModel(Model model, AppUser user) {
        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("latestPlan", recordRepository.findFirstByUserOrderByCalculatedAtDesc(user).orElse(null));
    }
}
