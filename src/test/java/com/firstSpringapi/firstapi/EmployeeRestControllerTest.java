package com.firstSpringapi.firstapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstSpringapi.firstapi.entity.Employee;
import com.firstSpringapi.firstapi.exceptions.ResouceNotFountExceptions;
import com.firstSpringapi.firstapi.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void getAllEmployees() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createEmployee() throws Exception {
        String body = "{\"name\":\"Ridoy\",\"grp\":\"Hossain\",\"email\":\"ridoy@gmail.com\"}";

        mockMvc.perform(post("/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

//    @Test
//    void getEmployeeById() throws Exception {
//        mockMvc.perform(get("/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//    }
@Test
void getEmployeeById_success() throws Exception {
    Employee employee = new Employee();
    employee.setId(1L);
    employee.setFirstName("John Doe");
    employee.setLastName("Engineering");
    employee.setEmailId("john.doe@example.com");

    when(employeeService.getEmployeeById(1L)).thenReturn(employee);

    mockMvc.perform(get("/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.grp").value("Engineering"))
            .andExpect(jsonPath("$.email").value("john.doe@example.com"));
}

    @Test
    void getEmployeeById_notFound() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenThrow(new ResouceNotFountExceptions("Employee not exists with id 1"));

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEmployee() throws Exception {
        long id = 1L;
        Employee employeeDetails = new Employee();
        employeeDetails.setFirstName("Ridoy");
        employeeDetails.setLastName("Hossain ");
        employeeDetails.setEmailId("ridoy@example.com");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setFirstName("Ridoy Updated");
        updatedEmployee.setLastName("Hossain Updated");
        updatedEmployee.setEmailId("hridoy@example.com");

        Mockito.when(employeeService.updateEmployee(id, employeeDetails)).thenReturn(updatedEmployee);

        mockMvc.perform(put("/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
