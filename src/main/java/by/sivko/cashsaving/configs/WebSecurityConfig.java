package by.sivko.cashsaving.configs;

import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

// TODO: 9/29/19 change web security configs (it was all copy)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] UNSECURED_RESOURCE_LIST = new String[]{"/resources/**"};

    private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[]{ "/login*", "/registration*"};

    @Configuration
    @Profile({"dev"})
    protected static class BasicUserDetailsServiceSetup  {

        private final PasswordEncoder passwordEncoder;

        @Autowired
        public BasicUserDetailsServiceSetup(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public UserDetailsService getBasicUserDetailsService() {
            return new BasicUserDetailsService();
        }

        public class BasicUserDetailsService implements UserDetailsService {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new User(username, passwordEncoder.encode(""), Collections.singletonList(new Authority(AuthorityType.ROLE_USER)));
            }
        }
    }

    @Configuration
    @Profile({"live"})
    protected static class MyUserDetailsServiceSetup {

        private final UserService userService;

        @Autowired
        public MyUserDetailsServiceSetup(UserService userService) {
            this.userService = userService;
        }

        @Bean
        public UserDetailsService getMyUserDetailsService() {
            return new MyUserDetailsService(userService);
        }

        public class MyUserDetailsService implements UserDetailsService {

            private final UserService userService;

            MyUserDetailsService(UserService userService) {
                this.userService = userService;
            }

            @Override
            @Transactional(readOnly = true)
            public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
                Optional<by.sivko.cashsaving.models.User> user = userService.findByUsername(usernameOrEmail);
                if (user.isPresent()) return user.get();
                user = this.userService.findByEmail(usernameOrEmail);
                if (!user.isPresent()) throw new UsernameNotFoundException(usernameOrEmail);
                return user.get();
            }
        }
    }

    @Configuration
    @Profile({"live"})
    public static class LiveWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final DaoAuthenticationProvider daoAuthenticationProvider;

        @Autowired
        public LiveWebSecurityConfigurerAdapter(DaoAuthenticationProvider daoAuthenticationProvider) {
            this.daoAuthenticationProvider = daoAuthenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoAuthenticationProvider);
        }

        @Override
        public void configure(WebSecurity web) {
            //@formatter:off
            web
                    .ignoring()
                    .antMatchers(UNSECURED_RESOURCE_LIST);
            //@formatter:on
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //@formatter:off
            http
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()
                    .authorizeRequests()
                    .antMatchers(UNAUTHORIZED_RESOURCE_LIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    //.successHandler((request, response, authentication) -> response.sendRedirect(authentication.getName()))
                    .permitAll()
                    .and()
                    .headers()
                    .cacheControl()
                    .and()
                    .frameOptions()
                    .deny()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access?error")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/?logout")
                    .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/login?expired");
            // @formatter:on
//            http
//                    .csrf()
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                    .and()
//                    .headers()
//                    .frameOptions()
//                    .sameOrigin()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers(UNAUTHORIZED_RESOURCE_LIST)
//                    .permitAll()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers(UNSECURED_RESOURCE_LIST)
//                    .permitAll()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/home*")
//                    .hasRole("USER")
//                    .and()
//                    .headers()
//                    .cacheControl()
//                    .and()
//                    .frameOptions()
//                    .deny()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/home", true)
//                    .permitAll()
//                    .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/access?error")
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/?logout");
        }
    }

    @Configuration
    @Profile({"dev", "test"})
    public static class DevAndTestWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


        private final DaoAuthenticationProvider daoAuthenticationProvider;


        @Autowired
        public DevAndTestWebSecurityConfigurerAdapter(DaoAuthenticationProvider daoAuthenticationProvider) {
            this.daoAuthenticationProvider = daoAuthenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoAuthenticationProvider);
        }

        @Override
        public void configure(WebSecurity web) {
            //@formatter:off
            web
                    .ignoring()
                    .antMatchers(UNSECURED_RESOURCE_LIST);
            //@formatter:on
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //@formatter:off
            http
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()
                    .authorizeRequests()
                    .antMatchers(UNAUTHORIZED_RESOURCE_LIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    //.successHandler((request, response, authentication) -> response.sendRedirect(authentication.getName()))
                    .permitAll()
                    .and()
                    .headers()
                    .cacheControl()
                    .and()
                    .frameOptions()
                    .deny()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access?error")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/?logout")
                    .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/login?expired");
            // @formatter:on
        }
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }


}
