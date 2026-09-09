package com.example.caloriescalculator;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProgressController {
    private final UserAccountService accountService;
    private final ProgressService progressService;

    public ProgressController(UserAccountService accountService, ProgressService progressService) {
        this.accountService = accountService;
        this.progressService = progressService;
    }

    @GetMapping("/history")
    public String history(@RequestParam(defaultValue = "7") int days, Model model, Principal principal) {
        int selectedDays = days == 30 ? 30 : 7;
        AppUser user = accountService.requireByEmail(principal.getName());
        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("selectedDays", selectedDays);
        model.addAttribute("report", progressService.report(user, selectedDays));
        return "history";
    }
}
