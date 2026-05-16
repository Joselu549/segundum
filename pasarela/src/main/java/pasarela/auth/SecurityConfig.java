package pasarela.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GitHubOAuth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .httpBasic().disable()
            .authorizeRequests()
                .antMatchers("/auth/**", "/oauth2/**", "/login/oauth2/**").permitAll()
                .anyRequest().permitAll()
            .and()
            .oauth2Login()
                .successHandler(successHandler);
    }
}
