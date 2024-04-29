package com.ensf480final.AirlineManager;

import com.ensf480final.AirlineManager.model.Aircraft;
import com.ensf480final.AirlineManager.service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AirlineManagerApplication implements CommandLineRunner {

	@Autowired
	private StartupService startupService;
	public static void main(String[] args) {
		SpringApplication.run(AirlineManagerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		startupService.addAccountsOnStartup();
		startupService.createAircraftsAndCrewAndFlights();
	}

}
