package com.firstSpringapi.firstapi;
import static org.junit.jupiter.api.Assertions.assertEquals; // Import for JUnit 5

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeRestController {
    @LocalServerPort
    private int port=9090;
    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<String>doRestCall(String uri, MultiValueMap<String,String>queryParams,
                                            MultiValueMap<String,String> headers, String body,
                                            Map<String, String> pathParams, HttpMethod method) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        HttpEntity<String>requestEntity = new HttpEntity<>(body,headers);
        ResponseEntity<String>responseEntity = restTemplate.exchange(builder.buildAndExpand(pathParams).toUri(),method,requestEntity,String.class);
        return responseEntity;
    }

//    String baseUri = "https://example.com/api/employees/{id}";
//    Map<String, String> pathParams = new HashMap<>();
//pathParams.put("id", "123"); // Assuming we want to update employee with ID 123
//
//    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//headers.add("Authorization", "Bearer your-auth-token");
//
//    String requestBody = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\"}";
//
//    HttpMethod method = HttpMethod.PUT;
//
//    ResponseEntity<String> response = doRestCall(baseUri, null, headers, requestBody, pathParams, method);
//
//System.out.println("Response status: " + response.getStatusCode());
//System.out.println("Response body: " + response.getBody());

    @Test
    void getAllEmployees() {
        String url = "http://localhost:" + port;
        Map<String, String> pathVariables = new HashMap<>();
        HttpEntity<String> entity = new HttpEntity<>(null,null);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(pathVariables).toUri(),
                HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createEmployee(){
        String url = "http://localhost:" + port +"/addEmployee";
        String body = "{\"name\":\"Ridoy\",\"grp\":\"Hossain\",\"email\":\"ridoy@gmail.com\"}";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        List<String> appJson = new ArrayList<String>();
        appJson.add("application/json");
        headers.put("content-type", appJson);
        Map<String, String> pathParams = new HashMap<>();
        ResponseEntity<String> response = doRestCall(url,null,headers,body,pathParams,HttpMethod.POST);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getEmployeeById(){
        String url = "http://localhost:" + port+"/{id}";
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "14");
        HttpEntity<String> entity = new HttpEntity<>(null,null);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(pathVariables).toUri(),
                HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteEmployee() {
        // Arrange
        String url = "http://localhost:" + port + "/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", "14");

        // Create an employee first to ensure it exists before deletion
        String createBody = "{\"name\":\"Ridoy\",\"grp\":\"Hossain\",\"email\":\"ridoy@gmail.com\"}";
        HttpEntity<String> createEntity = new HttpEntity<>(createBody, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/employees", createEntity, String.class);

        // Act
        HttpEntity<String> deleteEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, deleteEntity, String.class, pathParams);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
