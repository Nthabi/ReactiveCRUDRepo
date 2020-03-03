package com.example.two;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SeedDb implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    DatabaseClient databaseClient;

    @Autowired
    EmployeeRepository employeeRepository;

    public SeedDb(EmployeeRepository repo){
        employeeRepository = repo;

    }

    public void create(){
        //create your db
        databaseClient.execute(
                "create table employee (" +
                        "id int auto_increment primary key, " +
                        "name varchar, " +
                        "salary number, " +
                        "region varchar);")
                .then()
                .subscribe(e -> log.info("Created"));
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event){
        Employee employee1 = new Employee(null, "Mahlogonolo", 1000, "Free state");
        Employee employee2 = new Employee(null, "Lebo", 1500, "Free state");
        Employee employee3 = new Employee(null, "Refilwe", 1000, " North west");
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
       employeeRepository.deleteAll()
               .thenMany(Flux.fromIterable(employees))
               .flatMap(employee -> employeeRepository.save(employee))
               .subscribe(employee -> log.info("New employees added to DB" + employee));
//               .map(employees -> new Employee(UUID.randomUUID(), name, salary, region))
//               .flatMap(employee -> employeeRepository.save(employee))
//               .thenMany(employeeRepository.findAll())
//               .subscribe(employee -> log.info("New employees created " + employee));

    }
    //bootstrap data in the db

}
