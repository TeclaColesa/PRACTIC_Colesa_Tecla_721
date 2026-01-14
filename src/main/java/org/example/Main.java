package org.example;

import org.example.model.Fine;
import org.example.model.TrafficEvent;
import org.example.model.Vechicle;
import org.example.repository.FineRepository;
import org.example.repository.TrafficEventRepository;
import org.example.repository.VechicleRepository;

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
        System.out.println("Anzahl der Bussgelder: " + fineRepository.findAll().size() );
        for (Vechicle vechicle : vechicleRepository.findAll()) {
            System.out.println(vechicle);
        }
        System.out.println("Anzahl der Fahrzeuge: " + vechicleRepository.findAll().size());

        System.out.println("---------------------------------------------------------------------");

    }
}
