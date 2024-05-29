package com.firstSpringapi.firstapi;

import com.firstSpringapi.firstapi.entity.Employee;
import com.firstSpringapi.firstapi.repository.EmployeeRepository;
import com.firstSpringapi.firstapi.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmployeeServiceUnitTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;


    @Test
    public void testGetAllEmployees() {
        Employee employee = new Employee(1, "Hridoy", "Hossain", "ridoy@gmail.com");
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.add(employee);
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeArrayList);
        assertEquals(1, employeeService.getAllEmployees().size());
    }

    @Test
    public void testFindById() {
        Employee employee = new Employee(9, "Hridoy", "Hossain", "ridoy@gmail.com");
        long employeeId = 4L;
        Mockito.when(employeeRepository.findById(4L)).thenReturn(Optional.of(employee));
        Optional<Employee> result = Optional.ofNullable(employeeService.getEmployeeById(employeeId));
        assertEquals(employee, result.get());
    }

//    @Test
//    public void testDeleteById() {
//        long employeeId = 4L;
//        employeeService.deleteEmployee(employeeId);
//        verify(employeeRepository).deleteById(employeeId);
//    }
//    @Test
//    public void testDeleteById() {
//        Employee employee = new Employee(9, "Hridoy", "Hossain", "ridoy@gmail.com");
//
//        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
//        Mockito.when(employeeRepository.deleteById(employee.getId())).thenReturn(void);
//    }

    @Test
    public void testDeleteById() {
        long employeeId = 4;
        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    public void testCreateEmployee(){
        Employee employee = new Employee(1, "Hridoy", "Hossain", "ridoy@gmail.com");
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.add(employee);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employeeArrayList.get(0));
        assertEquals(employee.getId(),employeeService.createEmployee(employee).getId());
    }

    @Test
    public void testUpdateEmployee() {
        long employeeId = 1;
        Employee employee = new Employee(4, "Hridoy", "Hossain", "ridoy@gmail.com");
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee updatedEmployee = employeeService.updateEmployee(employeeId,employee);
        assertEquals(employee, updatedEmployee);
    }

}
