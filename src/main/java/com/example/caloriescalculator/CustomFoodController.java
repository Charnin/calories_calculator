package com.example.caloriescalculator;

import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomFoodController {
    private final UserAccountService accountService;
    private final CustomFoodService customFoodService;

    public CustomFoodController(UserAccountService accountService, CustomFoodService customFoodService) {
        this.accountService = accountService;
        this.customFoodService = customFoodService;
    }

    @PostMapping("/foods/custom")
    public String add(@Valid @ModelAttribute("customFood") CustomFoodForm form,
                      BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/diary?foodInvalid";
        }
        AppUser user = accountService.requireByEmail(principal.getName());
        customFoodService.add(user, form);
        return "redirect:/diary?foodCreated";
    }

    @PostMapping("/foods/custom/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        customFoodService.delete(user, id);
        return "redirect:/diary?foodDeleted";
    }

    @PostMapping("/foods/custom/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("customFood") CustomFoodForm form,
                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/diary?foodInvalid#custom-foods";
        }
        AppUser user = accountService.requireByEmail(principal.getName());
        customFoodService.update(user, id, form);
        return "redirect:/diary?foodUpdated#custom-foods";
    }
}
