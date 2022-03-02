package couch.exhibition.config;

import com.google.firebase.auth.FirebaseAuth;
import couch.exhibition.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private FirebaseAuth firebaseAuth;

    public SecurityConfig(FirebaseAuth firebaseAuth, UserDetailsService userDetailsService) {
        this.firebaseAuth = firebaseAuth;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 스프링 시큐리티 해제, 36번째 줄 - postman 500 에러 방지
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/v3/**", "health", "/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new JwtFilter(userDetailsService, firebaseAuth),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin();



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 유저 관련 페이지, 메인페이지, 전시회 DB 저장 페이지, 리소스
        web.ignoring().antMatchers(HttpMethod.POST, "/members")
                .antMatchers("/")
                .antMatchers("/resources/**")
                .antMatchers("/index")
                .antMatchers("/exhibitions/search/**")
                .antMatchers(HttpMethod.GET, "/exhibitions/**/viewAllReviews")
                .antMatchers("/exhibitions/**/likes/count")
                .antMatchers("/v3/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security","/swagger-ui/index.html", "/swagger-ui.html", "/webjars/**","/swagger/**","/swagger-ui/**")
                .antMatchers("/css/**")
                .antMatchers("/static/**")
                .antMatchers("/js/**")
                .antMatchers("/img/**")
                .antMatchers("/fonts/**")
                .antMatchers("/vendor/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/pages/**");
    }

}
