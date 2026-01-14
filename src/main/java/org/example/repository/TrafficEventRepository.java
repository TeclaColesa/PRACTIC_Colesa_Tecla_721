package org.example.repository;

import org.example.model.TrafficEvent;

public class TrafficEventRepository extends JsonRepository<TrafficEvent>{
    public TrafficEventRepository() {
        super("events.json", TrafficEvent.class);
    }
}
