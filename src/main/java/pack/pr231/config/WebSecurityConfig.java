package pack.pr231.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pack.pr231.config.handler.FailureHandle;
import pack.pr231.config.handler.LoginSuccessHandler;
import pack.pr231.config.handler.LogoutSuccessHandler;
import pack.pr231.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private FailureHandle failureHandle;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/", "/authenticate", "/create", "/resources/**", "/", "/home", "/js/**", "/css/**", "/static/**", "/styles/**").permitAll()
                .antMatchers("/user/awaiting").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/users", "/email/**").hasRole("USER")
                .and()
                .formLogin()
                .permitAll()
                //   .loginPage("/start")
                .loginProcessingUrl("/authenticate")
                .passwordParameter("password")
                .usernameParameter("nickname")
                .successHandler(loginSuccessHandler)
                .failureHandler(failureHandle)
                // .defaultSuccessUrl("/user/",userService.findUserByNickname())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}

