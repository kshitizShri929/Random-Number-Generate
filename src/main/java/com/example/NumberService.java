
package com.example;

import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class NumberService {

    @Inject
    NumberRepository repository;
    
    private final Random random = new Random();

    // Create
    @Transactional
    public void generateRandomNumbers(int count) {
        for (int i = 0; i < count; i++) {
            String number = generateRandom12Digit();
            NumberEntity entity = new NumberEntity();
            entity.setNumber(number);
            repository.persist(entity);
        }
    }

    // Read
    public List<String> getAllNumbers() {
        List<NumberEntity> entities = repository.listAll();
        List<String> numbers = new ArrayList<>();
        for (NumberEntity entity : entities) {
            numbers.add(entity.getNumber());
        }
        return numbers;
    }

    // Update
    @Transactional
    public void updateNumber(String oldNumber, String newNumber) {
        NumberEntity entity = repository.find("number", oldNumber).firstResult();
        if (entity != null) {
            entity.setNumber(newNumber);
            repository.persist(entity);
        } else {
            throw new IllegalArgumentException("Number not found: " + oldNumber);
        }
    }

    // Delete
    @Transactional
    public void deleteNumber(String number) {
        NumberEntity entity = repository.find("number", number).firstResult();
        if (entity != null) {
            repository.remove(entity);
        } else {
            throw new IllegalArgumentException("Number not found: " + number);
        }
    }

    // Verify if the number exists in the database
    public boolean existsInDb(String number) {
        NumberEntity entity = repository.find("number", number).firstResult();
        return entity != null;
    }

    // Utility method to generate a random 12-digit number
    private String generateRandom12Digit() {
        return String.format("%012d", random.nextLong() & Long.MAX_VALUE);
    }

    // Insert a number into the database, ensuring no duplicates
    @Transactional
    public void insertNumber(String number) {
        if (existsInDb(number)) {
            throw new IllegalArgumentException("Duplicate key detected: " + number);
        }
        NumberEntity entity = new NumberEntity();
        entity.setNumber(number);
        repository.persist(entity);
    }
}

