package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.repositories.AuthorityRepository;
import by.sivko.cashsaving.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${mq.register.routing.key}")
    private String registerRoutingKey;

    @Value("${mq.restore.routing.key}")
    private String restoreRoutingKey;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, AmqpTemplate amqpTemplate) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.amqpTemplate = amqpTemplate;
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        this.userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.addAuthority(this.authorityRepository.findByType(AuthorityType.ROLE_USER));
        log.info("Save user [%s] in db");
        this.userRepository.save(user);
        log.info("Success saving user [%s] in db");
        log.info("Send user [%s] in mq", user);
        this.amqpTemplate.convertAndSend(registerRoutingKey, user);
        log.info("Success sending user [%s] in mq", user);
        return user;
    }

    @Transactional
    @Override
    public void removeUser(User user) {
        this.userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Transactional
    @Override
    public void removeUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void activateUser(String code) {
        log.info(String.format("Activation user with code[%s]", code));
        Optional<User> userOptional = this.userRepository.findByActivationCode(code);
        User user = userOptional.orElseThrow(NotFoundEntityException::new);
        user.setEnabled(true);
        user.setActivationCode(null);
        this.userRepository.save(user);
        log.info(String.format("Success activation user [%s]", user));
    }

    @Transactional
    @Override
    public void setActivateCodeAndSendEmailForRestorePassword(String email) {
        log.info("Restore password at user with email [%s]", email);
        User user = this.userRepository.findByEmail(email).orElseThrow(NotFoundEntityException::new);
        user.setActivationCode(UUID.randomUUID().toString());
        this.amqpTemplate.convertAndSend(restoreRoutingKey, user);
        log.info("Success send activation code for restore password to user [%s]", user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExistUserByActivateCode(String code) {
        return this.userRepository.findByActivationCode(code).isPresent();
    }

    @Transactional
    @Override
    public void changePassword(String userCode, String password) {
        User user = this.userRepository.findByActivationCode(userCode).orElseThrow(NotFoundEntityException::new);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setActivationCode(null);
    }
}
