package org.example.service;

import org.example.model.Vechicle;
import org.example.model.VechicleStatus;
import org.example.model.VehicleType;
import org.example.repository.VechicleRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class VechicleService {
    private VechicleRepository repository;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public VechicleService(VechicleRepository repository) {
        this.repository = repository;
    }

    //2.
    //Filtern nach Fahrzeugtyp und Status
    //Lesen Sie von der Tastatur:
    //● einen Fahrzeugtyp (VehicleType)
    //● einen Fahrzeugstatus (VehicleStatus)
    //Geben Sie anschließend nur die Fahrzeuge aus, die beide
    //Bedingungen gleichzeitig erfüllen:
    //● type == eingegebener VehicleType
    //● status == eingegebener VehicleStatus
    public void filterByVehicleTypeAndStatus(){

        try {
            System.out.println("vehicle type (to sort by): ");
            String type = reader.readLine();

            System.out.println("vehicle status (to sort by): ");
            String status = reader.readLine();

            List<Vechicle> filteredVechicles = repository.findAll().stream()
                    .filter(v -> v.getType() == VehicleType.valueOf(type.toUpperCase())
                            && v.getStatus() == VechicleStatus.valueOf(status.toUpperCase()))
                    .collect(Collectors.toList());


            System.out.println("Filtered vehicles (by type and status):");
            filteredVechicles.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Eroare la citirea inputului");
        }

    }
}
