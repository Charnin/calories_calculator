package com.example.caloriescalculator;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class BodyFatEstimatorService {

    public boolean prepareBodyFat(UserProfileForm profile, BindingResult bindingResult) {
        String mode = profile.getBodyFatMode();
        if ("direct".equals(mode)) {
            if (profile.getBodyFatPercent() == null) {
                bindingResult.rejectValue("bodyFatPercent", "required", "กรุณากรอกเปอร์เซ็นต์ไขมัน");
            } else if (profile.getBodyFatPercent() < 3 || profile.getBodyFatPercent() > 70) {
                bindingResult.rejectValue("bodyFatPercent", "range", "เปอร์เซ็นต์ไขมันต้องอยู่ระหว่าง 3–70%");
            }
            return false;
        }
        if (!"tape".equals(mode)) {
            profile.setBodyFatPercent(null);
            return false;
        }

        if (profile.getNeckCm() == null) {
            bindingResult.rejectValue("neckCm", "required", "กรุณากรอกรอบคอ");
        }
        if (profile.getWaistCm() == null) {
            bindingResult.rejectValue("waistCm", "required", "กรุณากรอกรอบเอว");
        }
        if ("female".equals(profile.getSex()) && profile.getHipCm() == null) {
            bindingResult.rejectValue("hipCm", "required", "กรุณากรอกรอบสะโพก");
        }
        validateRange(bindingResult, "neckCm", profile.getNeckCm(), 20, 80, "กรุณาตรวจสอบรอบคออีกครั้ง");
        validateRange(bindingResult, "waistCm", profile.getWaistCm(), 40, 250, "กรุณาตรวจสอบรอบเอวอีกครั้ง");
        if ("female".equals(profile.getSex())) {
            validateRange(bindingResult, "hipCm", profile.getHipCm(), 50, 250, "กรุณาตรวจสอบรอบสะโพกอีกครั้ง");
        }
        if (bindingResult.hasErrors()) {
            return false;
        }

        double heightInches = profile.getHeightCm() / 2.54;
        double neckInches = profile.getNeckCm() / 2.54;
        double waistInches = profile.getWaistCm() / 2.54;
        double estimated;
        if ("male".equals(profile.getSex())) {
            if (waistInches <= neckInches) {
                bindingResult.rejectValue("waistCm", "invalid", "รอบเอวต้องมากกว่ารอบคอ");
                return false;
            }
            estimated = 86.010 * Math.log10(waistInches - neckInches)
                    - 70.041 * Math.log10(heightInches) + 36.76;
        } else {
            double hipInches = profile.getHipCm() / 2.54;
            if (waistInches + hipInches <= neckInches) {
                bindingResult.rejectValue("waistCm", "invalid", "กรุณาตรวจสอบค่ารอบตัวอีกครั้ง");
                return false;
            }
            estimated = 163.205 * Math.log10(waistInches + hipInches - neckInches)
                    - 97.684 * Math.log10(heightInches) - 78.387;
        }

        profile.setBodyFatPercent(Math.round(estimated * 10.0) / 10.0);
        if (estimated < 3 || estimated > 70) {
            bindingResult.rejectValue("waistCm", "estimate.range", "ผลประเมินอยู่นอกช่วงปกติ กรุณาวัดรอบตัวอีกครั้ง");
            return false;
        }
        return true;
    }

    private void validateRange(BindingResult errors, String field, Double value,
                               double minimum, double maximum, String message) {
        if (value != null && (value < minimum || value > maximum)) {
            errors.rejectValue(field, "range", message);
        }
    }
}
