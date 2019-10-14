package by.sivko.cashsaving.services;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
