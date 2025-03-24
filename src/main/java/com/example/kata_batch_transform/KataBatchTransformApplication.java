package com.example.kata_batch_transform;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication


public class KataBatchTransformApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job processJob;

	public static void main(String[] args) {

		SpringApplication.run(KataBatchTransformApplication.class, args);
	}



		@Override
		public void run(String... args) throws Exception {
			jobLauncher.run(processJob, new org.springframework.batch.core.JobParameters());

	}
}
