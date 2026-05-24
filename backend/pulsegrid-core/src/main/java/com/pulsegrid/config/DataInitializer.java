package com.pulsegrid.config;

import com.pulsegrid.domain.Responder;
import com.pulsegrid.repository.ResponderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(ResponderRepository repo) {
        return args -> {

            repo.save(new Responder(null, "Dr Ishita", "ish@gmail.com", "Sr. Doctor", "678272773", true));
            repo.save(new Responder(null, "Kareena", "kar@gmail.com", "Nurse", "338272773", false));
            repo.save(new Responder(null, "Dr Sukhman", "skk4@gmail.com", "junior doctor", "998272773", true));
            repo.save(new Responder(null, "Dr. Trisha", "trish@gmail.com", "Doctor", "99999999", true));
        };
    }
}