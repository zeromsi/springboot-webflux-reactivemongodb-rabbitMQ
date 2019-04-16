package com.msi;
import com.msi.model.Employee;
import com.msi.repository.EmployeeRepository;

import reactor.core.publisher.Mono;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.UUID;
import java.util.stream.Stream;
@EnableReactiveMongoRepositories
@SpringBootApplication
public class Application {
	
	
	
	
	public static void main(String[] args)throws InterruptedException{
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);

        final MongoOperations mongoOperations = application.getBean(MongoOperations.class);
        final EmployeeRepository employeeRepository = application.getBean(EmployeeRepository.class);

        if(mongoOperations.collectionExists("employee")) {
           mongoOperations.dropCollection("employee");
        }

        // Capped collections need to be created manually
        mongoOperations.createCollection("employee", CollectionOptions.empty().capped().size(9999999L).maxDocuments(100L));

        final Mono<Employee> saveTicketOne = employeeRepository.save(new Employee(UUID.randomUUID().toString(),
				"Peter", 23000L));
        final Mono<Employee> saveTicketTwo = employeeRepository.save(new Employee(UUID.randomUUID().toString(),
				"Peter", 23000L));

        saveTicketOne.subscribe();

        Thread.sleep(200);

        employeeRepository.findEmployeeByName("Peter")
            .flatMap(ticket -> printTicketInformation(ticket))
            .subscribe();

        System.out.println("Let's wait a bit before saving the second ticket, the tailable cursor stays open for new events");
        Thread.sleep(2000);

        saveTicketTwo.subscribe();

        System.out.println("Will wait for the information to be printed from the database");
        Thread.sleep(1000);
    }

    private static Mono<Employee> printTicketInformation(Employee employee) {
        System.out.println(String.format("Ticket Artist: %s Buyer: %s", employee.getId(), employee.getName()));
        return Mono.just(employee);
    }
}
	
	
	
	
	
	
	


//	@Bean
//	CommandLineRunner employees(EmployeeRepository  employeeRepository) {
//
//		return args -> {
//			employeeRepository
//					.deleteAll()
//			.subscribe(null, null, () -> {
//
//				Stream.of(new Employee(UUID.randomUUID().toString(),
//						"Peter", 23000L),new Employee(UUID.randomUUID().toString(),
//						"Sam", 13000L),new Employee(UUID.randomUUID().toString(),
//						"Ryan", 20000L),new Employee(UUID.randomUUID().toString(),
//						"Chris", 53000L)
//						)
//						.forEach(employee -> {
//				employeeRepository
//						.save(employee)
//						.subscribe(System.out::println);
//
//						});
//
//			})
//			;
//		};
//
//	}
//
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//}
