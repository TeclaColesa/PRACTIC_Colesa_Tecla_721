package org.example.service;

import org.example.model.EventType;
import org.example.model.Vechicle;
import org.example.model.VechicleStatus;
import org.example.model.VehicleType;
import org.example.repository.FineRepository;
import org.example.repository.TrafficEventRepository;
import org.example.repository.VechicleRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class VechicleService {
    private VechicleRepository vehicleRepository;
    private TrafficEventRepository trafficEventRepository;
    private FineRepository fineRepository;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public VechicleService(VechicleRepository vehicleRepository, TrafficEventRepository trafficEventRepository, FineRepository fineRepository) {
        this.vehicleRepository = vehicleRepository;
        this.trafficEventRepository = trafficEventRepository;
        this.fineRepository = fineRepository;
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

            List<Vechicle> filteredVechicles = vehicleRepository.findAll().stream()
                    .filter(v -> v.getType() == VehicleType.valueOf(type.toUpperCase())
                            && v.getStatus() == VechicleStatus.valueOf(status.toUpperCase()))
                    .collect(Collectors.toList());


            System.out.println("Filtered vehicles (by type and status):");
            filteredVechicles.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Eroare la citirea inputului");
        }

    }

    //Sortierung der Fahrzeuge
    //Sortieren Sie die Liste aller Fahrzeuge wie folgt:
    //● aufsteigend nach ownerCity
    //● bei gleichem ownerCity absteigend nach id
    //Geben Sie die sortierte Liste anschließend auf der
    //Konsole aus. Die Ausgabe erfolgt im gleichen Format wie
    //in Aufgabe 1.
    public void sortByOwnerCityAndId(){
        List<Vechicle> sortedVechicles = vehicleRepository.findAll().stream()
                .sorted((v1, v2) -> {
                    int cityComparison = v1.getOwnerCity().compareTo(v2.getOwnerCity());
                    if (cityComparison != 0) {
                        return cityComparison;
                    }
                    return Integer.compare(v2.getId(), v1.getId());
                })
                .collect(Collectors.toList());

        System.out.println("Sorted vehicles (by ownerCity ascending, then by id descending):");
        sortedVechicles.forEach(System.out::println);
    }

    //write list of vehicles in the file  vehicles_sorted.txt
    public void saveToFile(){
        List<Vechicle> sortedVechicles = vehicleRepository.findAll().stream()
                .sorted((v1, v2) -> {
                    int cityComparison = v1.getOwnerCity().compareTo(v2.getOwnerCity());
                    if (cityComparison != 0) {
                        return cityComparison;
                    }
                    return Integer.compare(v2.getId(), v1.getId());
                })
                .collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter("vehicles_sorted.txt")) {
            for (Vechicle v : sortedVechicles) {
                writer.println(v.toString());
            }
            System.out.println("Datele au fost scrise in fisier txt");
        } catch (Exception e) {
            throw new RuntimeException("cannot write to file: " + "vehicles_sorted.txt", e);
        }
    }




    //    Berechnung des Risikoscores
//  Für jedes Verkehrsereignis wird ein Risikowert (riskScore) berechnet. Implementieren Sie eine Methode,
//  die den Risikowert ausschließlich anhand des Event-Typs und der Schwere (severity) berechnet.
//    Regeln zur Berechnung:
//            ● SPEEDING → riskScore = severity * 2
//            ● RED_LIGHT → riskScore = severity * 3
//            ● ACCIDENT → riskScore = severity * 5
//            ● PRIORITY_PASS → riskScore = severity * 1

    public int calculateRiskScore(EventType eventType, int severity){
        if (eventType == EventType.SPEEDING) {
            return severity * 2;
        } else if (eventType == EventType.RED_LIGHT) {
            return severity * 3;
        } else if (eventType == EventType.ACCIDENT) {
            return severity * 5;
        } else {
            return severity * 1;
        }
    }

    public String riskScoreFormat(int no, EventType eventType, int severity){
        return "Event " + no + "-> severity=" + severity + " -> RiskScore=" + calculateRiskScore(eventType, severity);
    }




//    Ranking der Fahrzeuge
//    Berechnen Sie für jedes Fahrzeug den Gesamtscore totalRisk:
//    totalRisk(Vehicle) = Summe(riskScore aus den Events des
//            Fahrzeugs) - Summe(amount aus den Bußgeldern des Fahrzeugs)
    public int calculateTotalRisk(Vechicle vehicle){
        int vehicleId = vehicle.getId();

        int sumOfRiskScoresFromEvents = trafficEventRepository.findAll().stream()
                .filter(e -> e.getVehicleId() == vehicleId)
                .mapToInt(e -> calculateRiskScore(e.getType(), e.getSeverity()))
                .sum();

        int sumOfFines = fineRepository.findAll().stream()
                .filter(e -> e.getVehicleId() == vehicleId)
                .mapToInt(e -> e.getAmount())
                .sum();

        return sumOfRiskScoresFromEvents - sumOfFines;
    }
}

