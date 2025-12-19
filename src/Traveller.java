import java.util.ArrayList;

public class Traveller {

    private double position; //in reference to floor number. Will be equivalent to an integer unless in an elevator.
    private int destination;
    private boolean inElevator;
    private int minFloor;
    private int maxFloor;
    private int movementChance = 1;
    private Elevator currentElevator = new Elevator();
    private Elevator imprintedElevator = new Elevator(); //an elevator the traveller plans to enter, but has not yet.

    private static ArrayList<Traveller> travellerList = new ArrayList<Traveller>();

    //generic constructor (no params)
    public Traveller() {
        position = 0;
        destination = 0;
        inElevator = false;
        minFloor = 0;
        maxFloor = 999;
        travellerList.add(this);
    }

    //Three param constructor. Pass in the building's minFloor, maxFloor, and groundFloor values.
    public Traveller(int minFloor, int maxFloor, int groundFloor) {
        position = groundFloor;
        destination = groundFloor;
        inElevator = false;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        travellerList.add(this);
    }

    //Sets the Traveller's destination to a random floor between minFloor and maxFloor that it is not currently on.
    public void randomDestination() {
        int rand = -99999;
        while (rand == -99999 || rand == position) {
            rand = (int) (Math.random() * maxFloor) + 1 + minFloor;
        }
        destination = rand;
    }

    //runs all traveller's behavior for a tick.
    public static void tick() {
        for (Traveller ticking : travellerList){
            ticking.move();
            System.out.println(ticking);
        }
    }

    //runs one traveller's behavior for a given tick.
    //If the traveller's destination == position, it has a 1 in (movementChance) chance to get a random destination.
    //If the traveller has a destination, and isn't in an elevator, it will call an elevator.
    //If the traveller is in an elevator, it sets its position to the elevator's position.
    public void move() {
        if (destination == position) {
            if ((int) (Math.random() * movementChance) == 0) {
                randomDestination();
            }
        } else {
            if (!inElevator && imprintedElevator.getIsPlaceholder()) { //if the traveller has called an elevator already, does not run
                call();
            } else {
                if (!currentElevator.getIsPlaceholder()) {
                    position = currentElevator.getPosition();
                }
            }
        }
    }

    //runs to ask a traveller if they want to get into or out of an elevator.
    public void enterExit(Elevator e){
        if (!inElevator) {
            if (e == imprintedElevator) { // == is used here because the elevators must have the same reference
                currentElevator = e;
                inElevator = true;
            }
        } else {
            position = currentElevator.getPosition();
            if (position == destination) {
                currentElevator = new Elevator();
                imprintedElevator = new Elevator();
                inElevator = false;
            }
        }
    }

    //calls an elevator and imprints on it
    public void call(){
        System.out.println("called");
        imprintedElevator = Elevator.callElevator((int) position,destination);
    }

    public void setCurrentElevator(Elevator currentElevator){
        this.currentElevator = currentElevator;
    }

    public void setInElevator(boolean inElevator){
        this.inElevator = inElevator;
    }

    //toString. Prints position and destinatinon.
    public String toString() {
        double roundedPos = ((int) (position * 100))/100.0;
        return ("Pos: " + roundedPos + " Dest: " + destination + " In Elevator: " + currentElevator.getID() + " Imprinted: " + imprintedElevator.getID());
    }

    public double getPosition() {
        return position;
    }

    public int getDestination() {
        return destination;
    }

    public boolean isInElevator() {
        return inElevator;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getMovementChance() {
        return movementChance;
    }

    public Elevator getCurrentElevator() {
        return currentElevator;
    }

    public static ArrayList<Traveller> getTravellerList() {
        return travellerList;
    }

}