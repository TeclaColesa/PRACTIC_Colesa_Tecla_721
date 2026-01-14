package org.example;

import org.example.model.Fine;
import org.example.model.TrafficEvent;
import org.example.model.Vechicle;
import org.example.repository.FineRepository;
import org.example.repository.TrafficEventRepository;
import org.example.repository.VechicleRepository;
import org.example.service.VechicleService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TrafficEventRepository trafficEventRepository = new TrafficEventRepository();
        FineRepository fineRepository = new FineRepository();
        VechicleRepository vechicleRepository = new VechicleRepository();

        for (TrafficEvent event : trafficEventRepository.findAll()) {
            System.out.println(event);
        }
        System.out.println("Anzahl der Ereignisse: " + trafficEventRepository.findAll().size());
        for (Fine fine : fineRepository.findAll()) {
            System.out.println(fine);
        }
        for (Vechicle vechicle : vechicleRepository.findAll()) {
            System.out.println(vechicle);
        }

        System.out.println("---------------------------------------------------------------------");
        VechicleService vechicleService = new VechicleService(vechicleRepository);
        vechicleService.filterByVehicleTypeAndStatus();

        System.out.println("---------------------------------------------------------------------");
        vechicleService.sortByOwnerCityAndId();

        System.out.println("---------------------------------------------------------------------");
        vechicleService.saveToFile();
    }
}
