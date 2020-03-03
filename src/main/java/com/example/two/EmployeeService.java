package com.example.two;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private EmployeeRepository repository;
    private ApplicationEventPublisher publisher;

    public Flux<Employee> findAll() {return repository.findAll();}

    public Mono<Employee> create(String name, double salary, String region){
        return repository.save(new Employee(null,name, salary, region));
    }

    public Mono<Employee> update(Long id, String name, double salary, String region){
        return repository.findById(id)
                .map(employee -> new Employee(employee.getId(), name, salary, region))
                .flatMap(employee -> repository.save(employee).thenReturn(employee));
    }
}


