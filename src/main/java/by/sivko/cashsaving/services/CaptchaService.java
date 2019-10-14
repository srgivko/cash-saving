package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.dto.CaptchaResponseDto;

public interface CaptchaService {
    CaptchaResponseDto checkCaptcha(String captchaResponse);
}
