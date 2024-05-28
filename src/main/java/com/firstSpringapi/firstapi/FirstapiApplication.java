package com.firstSpringapi.firstapi;

import com.firstSpringapi.firstapi.entity.Employee;
import com.firstSpringapi.firstapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstapiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FirstapiApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public void run(String... args) throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Ridoy");
		employee.setLastName("Hossain");
		employee.setEmailId("ridoy.java@gmail.com");
		employeeRepository.save(employee);

		Employee employee1 = new Employee();
		employee1.setFirstName("Halim");
		employee1.setLastName("Hossain");
		employee1.setEmailId("halim.java@gmail.com");
		employeeRepository.save(employee1);
	}
}
