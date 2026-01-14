package org.example.repository;

import org.example.model.Vechicle;

public class VechicleRepository extends JsonRepository<Vechicle>{
    public VechicleRepository() {
        super("vechicles.json", Vechicle.class);
    }
}
