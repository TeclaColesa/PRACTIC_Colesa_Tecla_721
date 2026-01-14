package org.example.repository;

import org.example.model.Fine;

public class FineRepository extends JsonRepository<Fine>{
    public FineRepository() {
        super("fines.json", Fine.class);
    }
}
