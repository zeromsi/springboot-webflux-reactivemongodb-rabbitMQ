package com.msi.resource;

import com.msi.model.Employee;
import com.msi.model.EmployeeEvent;
import com.msi.repository.EmployeeRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeResource {


    private EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public Flux<Employee> getAll() {
		Stream.of(new Employee(UUID.randomUUID().toString(),
		"Peter", 23000L),new Employee(UUID.randomUUID().toString(),
		"Sam", 13000L),new Employee(UUID.randomUUID().toString(),
		"Ryan", 20000L),new Employee(UUID.randomUUID().toString(),
		"Chris", 53000L)
		)
		.forEach(employee -> {
employeeRepository
		.save(employee).subscribe();
		

		});
    	
        return (Flux<Employee>) employeeRepository
                .findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getId(@PathVariable("id") final String empId) {
        return employeeRepository.findById(empId);
    }


    @GetMapping(value = "events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> getEvents() {
        return employeeRepository.findEmployeeByName("Peter");
    }



}
