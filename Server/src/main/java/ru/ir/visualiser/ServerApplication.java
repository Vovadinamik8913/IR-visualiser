package ru.ir.visualiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ir.visualiser.files.Config;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		Config.deserializeFromJson();
		SpringApplication.run(ServerApplication.class, args);
	}

}
