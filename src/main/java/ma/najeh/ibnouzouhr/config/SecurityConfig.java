package ma.najeh.ibnouzouhr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by youssef on 12/3/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomLoginAndLogoutHandler loginAndLogoutHandler;
    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] unsecuredResources = {"/", "/home", "/about","/imgs/**","/rest/**","/inscription"};
        web.ignoring().antMatchers(unsecuredResources);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**","/css/**","/assets/**","/front/**").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("super_admin")
                .antMatchers("/scolarity/**").hasAnyAuthority("scolarity")
                .antMatchers("/administration/**").hasAnyAuthority("scolarity","super_admin")
                .antMatchers("/student/**").hasAnyAuthority("student")
                .antMatchers("/config/**").hasAnyAuthority("config")
                .antMatchers("/teacher/**").hasAnyAuthority("teacher")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login?error=true")
                .successHandler(loginAndLogoutHandler).and().csrf()
                .disable().logout().logoutUrl("/logout")
                .logoutSuccessHandler(loginAndLogoutHandler)
                .and().exceptionHandling().accessDeniedPage("/403");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
