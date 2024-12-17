package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NumberRepository implements PanacheRepository<NumberEntity> {
    public boolean existsByNumber(String number) {
    return find("number", number).count() > 0;
     }

    public void remove(NumberEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    } 
}

