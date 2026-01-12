import java.util.ArrayList;

public class Building {

    private int minFloor;
    private int maxFloor;
    private int numElevators;
    private double elevatorSpeed;
    private int groundFloor;
    private double busyness;
    private boolean addTravellers;
    private int startingTravellers;

    ArrayList<Dataset> datasets = new ArrayList<Dataset>();
    ArrayList<Elevator> elevators = new ArrayList<Elevator>();
    ArrayList<Traveller> travellers = new ArrayList<Traveller>();

    //fully customizable building constructor. Creates elevators. Busyness is a double between 0 and 1.
    public Building(int minFloor, int maxFloor, int numElevators, double elevatorSpeed, int groundFloor, double busyness, int startingTravellers, boolean addTravellers){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.numElevators = numElevators;
        this.elevatorSpeed = elevatorSpeed;
        this.groundFloor = groundFloor;
        this.busyness = busyness;
        this.startingTravellers = startingTravellers;
        this.addTravellers = addTravellers;
        createElevators();
        createTravellers();
    }

    //creates elevators until there are NumElevators elevators
    public void createElevators(){
        for (int i = elevators.size(); i < numElevators; i++){
            elevators.add(new Elevator(elevatorSpeed,groundFloor,this));
        }
    }

    //creates travellers until there are startingTravellers travellers
    public void createTravellers(){
        for (int i = travellers.size(); i < startingTravellers; i++){
            travellers.add(new Traveller(minFloor,maxFloor,groundFloor));
        }
    }

    //runs the simulation for some integer number of ticks. Gets a random double from 0 to 1, adds (i/ticks), and compares it to busyness.
    //if our number is less than busyness, we add a new traveller.
    //travellers should be more likely early in the day due to this.
    public void simulate(int ticks){
        for (int i = 0; i < ticks; i++){
            tick();

            double travellerChance = 1 - Math.random() + (double) i/ticks;
            if (travellerChance < busyness && addTravellers) {
                travellers.add(new Traveller(minFloor, maxFloor, groundFloor));
            }
        }
        getData(ticks);
    }

    //runs the simulation for one tick.
    private void tick(){
        Elevator.tick();
        Traveller.tick();
    }

    //retrieves data from all elevators and travellers, and passes it to the master dataset.
    public void getData(int ticks) {

        //gets elevator statistics, puts in arrays
        double[] eleTravelDist = new double[elevators.size()];
        for (int i = 0; i < elevators.size(); i++) {
            eleTravelDist[i] = elevators.get(i).getTotalDistance();
        }
        int[] eleIdleTime = new int[elevators.size()];
        for (int i = 0; i < elevators.size(); i++) {
            eleIdleTime[i] = elevators.get(i).getIdleTime();
        }
        int[] eleTimesCalled = new int[elevators.size()];
        for (int i = 0; i < elevators.size(); i++) {
            eleTimesCalled[i] = elevators.get(i).getTimesCalled();
        }

        //gets traveller statistics, puts in arrays
        int[] trTotalTime = new int[travellers.size()];
        for (int i = 0; i < travellers.size(); i++) {
            trTotalTime[i] = travellers.get(i).getTotalTime();
        }
        int[] trTimeWaiting = new int[travellers.size()];
        for (int i = 0; i < travellers.size(); i++) {
            trTimeWaiting[i] = travellers.get(i).getTotalTime();
        }
        int[] trNumCalls = new int[travellers.size()];
        for (int i = 0; i < travellers.size(); i++) {
            trNumCalls[i] = travellers.get(i).getNumCalls();
        }

        //creates a new dataset, passes through all the values we just got, plus some building values.
        datasets.add(new Dataset(ticks, minFloor, maxFloor, eleTravelDist, eleIdleTime, eleTimesCalled, trTotalTime, trTimeWaiting, trNumCalls));
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
