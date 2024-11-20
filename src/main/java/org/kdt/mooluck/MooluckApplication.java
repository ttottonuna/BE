package org.kdt.mooluck;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = " 반려식물 아바타 '무럭이' API 명세서",
				version = "1.0",
				description = "스프링 리트리버"
		)
)
@SpringBootApplication
@MapperScan("org.kdt.mooluck.domain.**.mapper")
//@MapperScan("org.kdt.mooluck.elder.mapper")

public class MooluckApplication {
	public static void main(String[] args) {
		SpringApplication.run(MooluckApplication.class, args);
	}
}

