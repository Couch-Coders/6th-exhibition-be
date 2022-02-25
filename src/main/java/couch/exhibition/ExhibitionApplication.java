package couch.exhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 추가 설정(관련 설치?)
public class ExhibitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExhibitionApplication.class, args);
	}
}
