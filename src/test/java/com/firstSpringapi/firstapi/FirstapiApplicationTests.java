package com.firstSpringapi.firstapi;

import com.firstSpringapi.firstapi.entity.Employee;
import com.firstSpringapi.firstapi.repository.EmployeeRepository;
import com.firstSpringapi.firstapi.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


@SpringBootTest
class FirstapiApplicationTests {
@Mock
private EmployeeRepository employeeRepository;
@InjectMocks
private EmployeeService employeeService;

private static AutoCloseable autoCloseable;
@BeforeEach
void setUp() {
	autoCloseable= MockitoAnnotations.openMocks(this);
	employeeService = new EmployeeService(employeeRepository);
}
@AfterAll
static void tearDown() throws Exception {
	autoCloseable.close();
}
@Test
void createEmployee() {
	Employee employee = new Employee(4,"hossain","ridoy","ridoy@gmail.com");
	employeeService.createEmployee(employee);
	ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
	verify(employeeRepository).save(captor.capture());
	Employee capturedEmployee = captor.getValue();
	Assertions.assertThat(capturedEmployee).isEqualTo(employee);
}
@Test
void getEmployeeById() {
	long id = 1;
	Optional<Employee> getEmployee = employeeRepository.findById(id);
	verify(employeeRepository).findById(id);
	assertThat(Optional.empty()).isEqualTo(getEmployee);
}

@Test
void getAllEmployees(){
	List<Employee> employees = employeeService.getAllEmployees();
	verify(employeeRepository).findAll();
	assertThat(employeeRepository.count()).isEqualTo(employees.size());
}
@Test
void deleteEmployee() {
	long id = 2;
	employeeRepository.deleteById(id);
	verify(employeeRepository).deleteById(id);
}
	@Test
	void updateEmployee() {
		long updateId = 2;
		Optional<Employee> getEmployee = employeeRepository.findById(updateId);

		// Check if the Optional contains a value before calling get()
		if (getEmployee.isPresent()) {
			Employee updateEmployee = getEmployee.get();
			updateEmployee.setFirstName("Hridoy");
			updateEmployee.setLastName("Hossain");
			updateEmployee.setEmailId("ridoy@gmail.com");
		} else {

		}
	}


	@Test
	void contextLoads() {
	}

}
