package couch.exhibition.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirebaseInitiializer {
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();
        FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }
}
