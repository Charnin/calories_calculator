package com.example.caloriescalculator;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    private final CalorieCalculatorService calculatorService;
    private final BodyFatEstimatorService bodyFatEstimatorService;
    private final UserAccountService accountService;
    private final CalculationRecordRepository recordRepository;

    public HomeController(CalorieCalculatorService calculatorService,
                          BodyFatEstimatorService bodyFatEstimatorService,
                          UserAccountService accountService,
                          CalculationRecordRepository recordRepository) {
        this.calculatorService = calculatorService;
        this.bodyFatEstimatorService = bodyFatEstimatorService;
        this.accountService = accountService;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        List<CalculationRecord> history = recordRepository.findTop10ByUserOrderByCalculatedAtDesc(user);
        UserProfileForm profile = history.isEmpty() ? new UserProfileForm() : profileFrom(history.getFirst());
        model.addAttribute("profile", profile);
        addUserAndHistory(model, user, history);
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@Valid @ModelAttribute("profile") UserProfileForm profile,
                            BindingResult bindingResult, Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        boolean estimatedBodyFat = bodyFatEstimatorService.prepareBodyFat(profile, bindingResult);
        if (bindingResult.hasErrors()) {
            addUserAndHistory(model, user, recordRepository.findTop10ByUserOrderByCalculatedAtDesc(user));
            return "index";
        }

        CalculationResult result = calculatorService.calculate(profile);
        recordRepository.save(new CalculationRecord(user, profile, result));
        model.addAttribute("result", result);
        model.addAttribute("saved", true);
        model.addAttribute("estimatedBodyFat", estimatedBodyFat);
        addUserAndHistory(model, user, recordRepository.findTop10ByUserOrderByCalculatedAtDesc(user));
        return "index";
    }

    private void addUserAndHistory(Model model, AppUser user, List<CalculationRecord> history) {
        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("history", history);
    }

    private UserProfileForm profileFrom(CalculationRecord record) {
        UserProfileForm profile = new UserProfileForm();
        profile.setAge(record.getAge());
        profile.setSex(record.getSex());
        profile.setWeightKg(record.getWeightKg());
        profile.setHeightCm(record.getHeightCm());
        profile.setBodyFatPercent(record.getBodyFatPercent());
        profile.setBodyFatMode(record.getBodyFatPercent() == null ? "skip" : "direct");
        profile.setActivityLevel(record.getActivityLevel());
        profile.setGoal(record.getGoal());
        return profile;
    }
}
