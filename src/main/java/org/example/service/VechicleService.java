package org.example.service;

import org.example.model.*;
import org.example.repository.FineRepository;
import org.example.repository.TrafficEventRepository;
import org.example.repository.VechicleRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


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
                    .collect(toList());


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
                .collect(toList());

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
                .collect(toList());

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

    //calculate total risk for all vehicles
    public void calculateTotalRiskForAllVehicles(){
        List<Vechicle> vehicles = vehicleRepository.findAll();
        vehicles.forEach(v -> System.out.println(v.getId() + " -> " + calculateTotalRisk(v)));
    }

    //first 5 vehicles sorted ascending by total risk and descending by license plate
    public void sortVehiclesByTotalRisk() {
        Comparator<Vechicle> comparator = Comparator.comparingInt(this::calculateTotalRisk)
                .thenComparing(Vechicle::getLicensePlate, Comparator.reverseOrder());
        List<Vechicle> sortedVehicles = vehicleRepository.findAll().stream()
                .sorted(comparator)
                .limit(5) //SE CER DOAR PRIMELE 5 VEHICULE in ordinea sortarii descrescatoare!!!
                .collect(toList());

        int i = 0;
        for (Vechicle v : sortedVehicles) {
            System.out.println(i++ + "." + v.getLicensePlate() + " -> " + calculateTotalRisk(v));
        }
    }

    public void printSafestVechicle(){
        Vechicle safestVechicle = vehicleRepository.findAll().stream()
                .min(Comparator.comparingInt(this::calculateTotalRisk))
                .orElseThrow();
        System.out.println("Safest vehicle: " + safestVechicle.getLicensePlate() + " -> " + calculateTotalRisk(safestVechicle));
    }





//    Erstellen Sie die Datei traffic_report.txt. Der Bericht soll
//    eine Übersicht über die Verkehrsereignisse enthalten.
//    Berechnen Sie auf Basis der Datei events.json die Anzahl der
//    Ereignisse pro EventType und schreiben Sie das Ergebnis in die
//    Datei. Die Ausgabe muss absteigend nach der Anzahl der
//    Ereignisse sortiert sein (bei gleicher Anzahl ist die
//            Reihenfolge beliebig).
//    SPEEDING -> 6
//    RED_LIGHT -> 3
//    ACCIDENT -> 3
//    PRIORITY_PASS -> 2

//so:::::::::::SCRIEM O MAPARE, folosind GROUP BY si HAVING

    public void saveReportToFile() {

        Map<EventType, Long> countsByType = trafficEventRepository.findAll().stream()
                .collect(Collectors.groupingBy(TrafficEvent::getType, Collectors.counting()));

        List<Map.Entry<EventType, Long>> sorted = countsByType.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();

        try (PrintWriter writer = new PrintWriter("traffic_report.txt")) {
            for (var entry : sorted) {
                writer.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println("Raportul de trafic a fost scris in fisier txt");
        } catch (Exception e) {
            throw new RuntimeException("cannot write to file: " + "traffic_report.txt", e);
        }

    }




}

