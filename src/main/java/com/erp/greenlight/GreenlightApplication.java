package com.erp.greenlight;

import com.erp.greenlight.repositories.AccountRepo;
import com.erp.greenlight.repositories.AdminPanelSettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenlightApplication  {

	public static void main(String[] args) {
		SpringApplication.run(GreenlightApplication.class, args);

	}


}
