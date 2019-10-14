package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate;

    @Autowired
    public CaptchaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CaptchaResponseDto checkCaptcha(String captchaResponse) {
        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        return restTemplate.postForObject(url, Collections.EMPTY_LIST, CaptchaResponseDto.class);
    }

}