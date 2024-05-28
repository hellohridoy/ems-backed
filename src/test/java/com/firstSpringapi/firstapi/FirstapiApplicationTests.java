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

//void getEmployeeById() {
//	long id = 1;
//	Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployeeById(id));
//
//}

	@Test
	void contextLoads() {
	}

}
