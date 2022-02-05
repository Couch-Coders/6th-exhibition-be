package couch.exhibition.config;

import com.google.firebase.auth.FirebaseAuth;
import couch.exhibition.filter.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 가이드 코드
    //    @Autowired
    //    private UserDetailsService userDetailsService;
    //
    //    @Autowired
    //    private FirebaseAuth firebaseAuth;

    // ---> field injection is not recommended 라는 경고로 아래와 같이 수정해봤습니다.

    private final UserDetailsService userDetailsService;

    private final FirebaseAuth firebaseAuth;

    public SecurityConfig(FirebaseAuth firebaseAuth, UserDetailsService userDetailsService) {
        this.firebaseAuth = firebaseAuth;
        this.userDetailsService = userDetailsService;
    }

    // 아래부터는 가이드 코드 그대로
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated().and()
                .addFilterBefore(new JwtFilter(userDetailsService, firebaseAuth),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    // 추후 수정
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 회원가입, 메인페이지, 리소스
        web.ignoring().antMatchers(HttpMethod.POST, "/users")
                .antMatchers("/")
                .antMatchers("/resources/**");
    }
}
