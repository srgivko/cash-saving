package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RabbitMQListenerImpl {

    @Value("${hostname}")
    private String hostname;

    private final MailSenderService mailSenderService;

    @Autowired
    public RabbitMQListenerImpl(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @RabbitListener(queues = "${mq.register.queue.name}")
    public void sendMailForRegisterUser(@Payload User user) {
        String message = String.format("http://%s/activate/%s", hostname, user.getActivationCode());
        this.sendMessage(user, "Activation code for registration", message);
    }

    @RabbitListener(queues = "${mq.restore.queue.name}")
    public void sendMailForRestorePassword(@Payload User user) {
        String message = String.format("http://%s/login/forget/%s", hostname, user.getActivationCode());
        this.sendMessage(user, "Activation code for restore password", message);
    }

    private void sendMessage(User user, String subject, String url) {
        log.info(String.format("Receive message from queue user[%s]", user));
        String message = String.format("Hello, %s!\n" +
                "Welcome to CashSaving\nPlease visit next link: %s", user.getUsername(), url);
        if (!user.getEmail().isEmpty()) {
            this.mailSenderService.send(user.getEmail(), subject, message);
        }
        log.info(String.format("Success sending message to user[%s]", user));
    }

}
