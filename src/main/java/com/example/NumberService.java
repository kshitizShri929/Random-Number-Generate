package com.example;

import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class NumberService {
    @Inject
    NumberRepository repository;
    private Set <String> valley=new HashSet<>();
    private final Random random= new Random();
    @Transactional
    public void generateRandomNumbers(int count){
        for(int i=0; i<count;i++){
            String number=generateRandom12Digit();
            NumberEntity entity= new NumberEntity();
            entity.setNumber(number);
            repository.persist(entity);
            
        }
    }  
    private String generateRandom12Digit() {
        return String.format("%012d", random.nextLong() & Long.MAX_VALUE);
    }
    public void loadNumbersIntoValley() {
        List<NumberEntity> entities = repository.listAll();
        for (NumberEntity entity : entities) {
            valley.add(entity.getNumber());
        }
    }
    public void insertIntoValley(String number) {
        if (valley.contains(number)) {
            throw new IllegalArgumentException("Duplicate key detected: " + number);
        }
        valley.add(number);
    }

public Set<String> getValley() {
    return valley;
}
}
