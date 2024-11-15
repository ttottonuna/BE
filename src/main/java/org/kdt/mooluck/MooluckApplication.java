package org.kdt.mooluck;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "[스프링 리트리버] '무럭이' API 명세서",
				version = "1.0",
				description = "수호 있는 수호팀 화이팅!"
		)
)
@SpringBootApplication
@MapperScan("org.kdt.mooluck.elder.mapper") // ElderMapper가 위치한 패키지를 정확하게 지정
public class MooluckApplication {
	public static void main(String[] args) {
		SpringApplication.run(MooluckApplication.class, args);
	}
}

