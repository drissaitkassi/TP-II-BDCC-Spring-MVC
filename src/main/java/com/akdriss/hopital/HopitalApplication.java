package com.akdriss.hopital;

import com.akdriss.hopital.entities.Patient;
import com.akdriss.hopital.web.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Patient p1=new Patient(null,"mohamed",new Date(),false,23);
        Patient p2=new Patient(null,"ahmed",new Date(),true,33);
        Patient p3=new Patient(null,"hassan",new Date(),false,43);

        patientRepository.save(p1);
        patientRepository.save(p2);
        patientRepository.save(p3);




    }
}
