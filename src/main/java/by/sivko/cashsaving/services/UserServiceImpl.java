package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.repositories.AuthorityRepository;
import by.sivko.cashsaving.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

    @Value("${hostname}")
    private String hostname;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, MailSenderService mailSender) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSender;
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
        this.userRepository.save(user);
        this.sendMessage(user);
        return user;
    }

    private void sendMessage(User user) {
        if (!user.getEmail().isEmpty()) {
            String message = String.format("Hello, %s!" +
                    "Welcome to Sweater Please visit next link: http://%s/activate/%s", user.getUsername(), hostname, user.getActivationCode());
            this.mailSenderService.send(user.getEmail(), "Activation code", message);
        }
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
}
