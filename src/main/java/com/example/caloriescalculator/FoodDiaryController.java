package com.example.caloriescalculator;

import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FoodDiaryController {
    private final UserAccountService accountService;
    private final FoodDiaryService diaryService;
    private final CustomFoodService customFoodService;

    public FoodDiaryController(UserAccountService accountService, FoodDiaryService diaryService,
                               CustomFoodService customFoodService) {
        this.accountService = accountService;
        this.diaryService = diaryService;
        this.customFoodService = customFoodService;
    }

    @GetMapping("/diary")
    public String diary(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        Model model, Principal principal) {
        LocalDate selectedDate = date == null ? LocalDate.now() : date;
        FoodEntryForm form = new FoodEntryForm();
        form.setLogDate(selectedDate);
        model.addAttribute("foodEntry", form);
        addDiaryModel(model, principal, selectedDate);
        return "diary";
    }

    @PostMapping("/diary")
    public String addFood(@Valid @ModelAttribute("foodEntry") FoodEntryForm foodEntry,
                          BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            addDiaryModel(model, principal,
                    foodEntry.getLogDate() == null ? LocalDate.now() : foodEntry.getLogDate());
            return "diary";
        }
        AppUser user = accountService.requireByEmail(principal.getName());
        diaryService.add(user, foodEntry);
        return "redirect:/diary?date=" + foodEntry.getLogDate() + "&saved";
    }

    @GetMapping("/diary/{id}/edit")
    public String editFood(@PathVariable Long id, Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        FoodEntry entry = diaryService.requireEntry(user, id);
        model.addAttribute("foodEntry", FoodEntryForm.from(entry));
        model.addAttribute("editingEntryId", id);
        addDiaryModel(model, principal, entry.getLogDate());
        return "diary";
    }

    @GetMapping("/diary/{id}/repeat")
    public String repeatFood(@PathVariable Long id,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             Model model, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        FoodEntryForm form = FoodEntryForm.from(diaryService.requireEntry(user, id));
        form.setLogDate(date);
        model.addAttribute("foodEntry", form);
        model.addAttribute("repeatingEntry", true);
        addDiaryModel(model, principal, date);
        return "diary";
    }

    @PostMapping("/diary/{id}")
    public String updateFood(@PathVariable Long id,
                             @Valid @ModelAttribute("foodEntry") FoodEntryForm foodEntry,
                             BindingResult bindingResult, Model model, Principal principal) {
        LocalDate date = foodEntry.getLogDate() == null ? LocalDate.now() : foodEntry.getLogDate();
        if (bindingResult.hasErrors()) {
            model.addAttribute("editingEntryId", id);
            addDiaryModel(model, principal, date);
            return "diary";
        }
        AppUser user = accountService.requireByEmail(principal.getName());
        diaryService.update(user, id, foodEntry);
        return "redirect:/diary?date=" + date + "&updated";
    }

    @PostMapping("/diary/{id}/delete")
    public String deleteFood(@PathVariable Long id,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        diaryService.delete(user, id);
        return "redirect:/diary?date=" + date + "&deleted";
    }

    @PostMapping("/diary/copy-meal")
    public String copyMeal(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sourceDate,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate,
                           @RequestParam String mealType, Principal principal) {
        AppUser user = accountService.requireByEmail(principal.getName());
        int copied = diaryService.copyMeal(user, sourceDate, targetDate, mealType);
        return "redirect:/diary?date=" + targetDate + (copied == 0 ? "&nothingToCopy" : "&copied=" + copied);
    }

    private void addDiaryModel(Model model, Principal principal, LocalDate date) {
        AppUser user = accountService.requireByEmail(principal.getName());
        List<FoodEntry> entries = diaryService.entriesFor(user, date);
        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("selectedDate", date);
        model.addAttribute("entries", entries);
        model.addAttribute("summary", diaryService.summarize(user, date, entries));
        model.addAttribute("customFoods", customFoodService.list(user));
        if (!model.containsAttribute("customFood")) {
            model.addAttribute("customFood", new CustomFoodForm());
        }
    }
}
