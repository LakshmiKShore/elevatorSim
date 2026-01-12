import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        experimentHandler();
    }

    //interfaces with the runExperiment method, asking
    public static void experimentHandler() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        boolean done = false;
        boolean isInt = true;
        String independent = "";

        while (!done) {
            System.out.println("What independent variable would you like to test? (Elevator Number, Elevator Speed, Busyness, Starting Travellers)");
            independent = scanner.next().toLowerCase();
            if (independent.equals("elevator number") || independent.equals("starting travellers")) {
                isInt = true;
                done = true;
            } else if (independent.equals("elevator speed") || independent.equals("busyness")) {
                isInt = false;
                done = true;
            } else {
                System.out.println("I'm sorry, I don't understand.");
            }
        }
        done = false;

        System.out.println("How many values would you like to test?");
        int valueSize = scanner.nextInt();
        int[] intTestValues = new int[0];
        double[] doubleTestValues = new double[0];

        if (isInt) {
            intTestValues = new int[valueSize];
            for (int i = 0; i < valueSize; i++) {
                System.out.println("What is testing value number " + i + "?");
                intTestValues[i] = scanner.nextInt();
            }
        } else {
            doubleTestValues = new double[valueSize];
            for (int i = 0; i < valueSize; i++) {
                System.out.println("What is testing value number " + i + "?");
                doubleTestValues[i] = scanner.nextDouble();
            }
        }

        System.out.println("What should the minimum floor of the building be?");
        int minFloor = scanner.nextInt();

        System.out.println("What should the maximum floor of the building be?");
        int maxFloor = scanner.nextInt();

        System.out.println("What should the ground floor of the building be?");
        int groundFloor = scanner.nextInt();

        int numElevators = 0;
        if (!independent.equals("elevator number")) {
            System.out.println("How many elevators should there be?");
            numElevators = scanner.nextInt();
        }

        double elevatorSpeed = 0;
        if (!independent.equals("elevator speed")) {
            System.out.println("How fast should the elevators move?");
            elevatorSpeed = scanner.nextDouble();
        }

        double busyness = 0;
        if (!independent.equals("busyness")) {
            System.out.println("How busy should the building be? (0-1)");
            busyness = scanner.nextDouble();
        }

        int startingTravellers = 0;
        if (!independent.equals("starting travellers")) {
            System.out.println("How many travellers should start in the building?");
            startingTravellers = scanner.nextInt();
        }

        System.out.println("Should more travellers enter the building during the simulation? (True/False)");
        boolean addTravellers = scanner.nextBoolean();

        System.out.println("How many trials should we run for each value?");
        int numTrials = scanner.nextInt();

        System.out.println("How long should each trial be? (ticks)");
        int ticks = scanner.nextInt();

        Building[] b = new Building[valueSize];
        if (independent.equals("elevator number")) {
            for (int i = 0; i < valueSize; i++) {
                b[i] = new Building(minFloor,maxFloor,intTestValues[i],elevatorSpeed,groundFloor,busyness,startingTravellers,addTravellers);
            }
        } else if (independent.equals("elevator speed")) {
            for (int i = 0; i < valueSize; i++) {
                b[i] = new Building(minFloor,maxFloor,numElevators,doubleTestValues[i],groundFloor,busyness,startingTravellers,addTravellers);
            }
        } else if (independent.equals("busyness")) {
            for (int i = 0; i < valueSize; i++) {
                b[i] = new Building(minFloor,maxFloor,numElevators,elevatorSpeed,groundFloor,doubleTestValues[i],startingTravellers,addTravellers);
            }
        } else if (independent.equals("starting travellers")) {
            for (int i = 0; i < valueSize; i++) {
                b[i] = new Building(minFloor,maxFloor,numElevators,elevatorSpeed,groundFloor,busyness,intTestValues[i],addTravellers);
            }
        }

        runExperiment(b, numTrials, ticks);

    }

    public static void runExperiment(Building[] b, int numTrials, int ticks) {
        for (Building x : b) {
            for (int i = 0; i < numTrials; i++) {
                x.simulate(ticks);
            }
        }
    }
    
}