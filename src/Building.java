import java.util.ArrayList;

public class Building {

    private int minFloor;
    private int maxFloor;
    private int numElevators;
    private double elevatorSpeed;
    private int groundFloor;
    private double busyness;

    ArrayList<Elevator> elevators = new ArrayList<Elevator>();
    ArrayList<Traveller> travellers = new ArrayList<Traveller>();

    //fully customizable building constructor. Creates elevators. Busyness is a double between 0 and 1.
    public Building(int minFloor, int maxFloor, int numElevators, double elevatorSpeed, int groundFloor, double busyness){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.numElevators = numElevators;
        this.elevatorSpeed = elevatorSpeed;
        this.groundFloor = groundFloor;
        this.busyness = busyness;
        createElevators();
    }

    //creates elevators until there are NumElevators elevators
    public void createElevators(){
        for (int i = elevators.size(); i < numElevators; i++){
            elevators.add(new Elevator(elevatorSpeed,groundFloor));
        }
    }

    //runs the simulation for some integer number of ticks. Gets a random double from 0 to 1, adds (i/ticks), and compares it to busyness.
    //if our number is less than busyness, we add a new traveller.
    //travellers should be more likely early in the day due to this.
    public void simulate(int ticks){
        for (int i = 0; i < ticks; i++){
            System.out.println("");
            System.out.println("Tick Number " + (i + 1));
            tick();

            double travellerChance = 1 - Math.random() + (double) i/ticks;
            if (travellerChance < busyness) {
                System.out.println("Another traveller has hit the world trade center");
                travellers.add(new Traveller(minFloor, maxFloor, groundFloor));
            }
        }
    }

    //runs the simulation for one tick.
    private void tick(){
        Elevator.tick();
        Traveller.tick();
    }


    //getters
    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getNumElevators() {
        return numElevators;
    }

    public double getElevatorSpeed() {
        return elevatorSpeed;
    }

    public int getGroundFloor() {
        return groundFloor;
    }

    public double getBusyness() {
        return busyness;
    }

    public ArrayList<Traveller> getTravellers() {
        return travellers;
    }
}
