package couch.exhibition.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import couch.exhibition.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter{

    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    public JwtFilter(UserDetailsService userDetailsService, FirebaseAuth firebaseAuth) {
        this.userDetailsService = userDetailsService;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)// not annotated parameter overrides @nonnullapi parameter 라는 경고가 떠서 @NonNull 붙여보았음.
            throws ServletException, IOException {
        // get the token from the request
        FirebaseToken decodedToken;
        try{
            String header = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
            log.info(header);
            decodedToken = firebaseAuth.verifyIdToken(header);
        } catch (FirebaseAuthException | IllegalArgumentException e) {
            // ErrorMessage 응답 전송
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        // User를 가져와 SecurityContext에 저장한다.
        try{
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(NoSuchElementException e){
            // ErrorMessage 응답 전송
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}