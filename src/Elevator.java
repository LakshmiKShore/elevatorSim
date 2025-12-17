import java.util.ArrayList;
import java.util.Arrays;

public class Elevator {
    private static int totalElevators = 0;
    private static int nextID = 0;
    static ArrayList<Elevator> elevatorList = new ArrayList<>();
    private boolean isPlaceholder = false;

    private double speed; //speed is in floors per tick
    private int capacity;
    private int maxFloor;
    private int minFloor;
    private int id;

    private double position; //position is in reference to floor number
    private boolean isMoving;
    private int direction; //1 is up, -1 is down
    private int occupants;
    private ArrayList<Integer> destinations = new ArrayList<Integer>();
    private ArrayList<Traveller> occupantList = new ArrayList<Traveller>();

    //constructor takes speed accel and capacity, increments totalelevators, adds itself to the array
    public Elevator(double speed, int capacity, int minFloor, int maxFloor, int startPosition) {
        this.speed = speed;
        this.capacity = capacity;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        id = nextID;
        position = startPosition;

        totalElevators++;
        nextID++;
        elevatorList.add(this);
    }

    //placeholder elevator constructor
    public Elevator() {
        this.isPlaceholder = true;
        id = -1;
    }

    //runs every elevator's movement.
    public static void tick() {
        //is broken :(
        for (Elevator ticking : elevatorList) {
            ticking.move();
        }
    }

    //runs an individual elevator's movement.
    public void move() {
        directionCheck();

        //if there are no destinations, ABORT ABORT ABORT
        if (destinations.isEmpty()) {
            return;
        }

        //If the next destination is further than we can move in a tick, move full speed towards it
        //otherwise, we can safely set our position to our destination
        if (Math.abs(destinations.get(0) - position) >= speed) {
            position += (speed * direction);
        } else {
            position = destinations.get(0);
            openDoors();
            //removes only the immediate next instances of a specific destination
            while ((!destinations.isEmpty()) && (destinations.get(0) == position)) {
                destinations.remove(0);
            }
        }
        System.out.println(this);
    }

    //prompts travellers to enter or exit the elevator
    public void openDoors(){
        for (Traveller checking : Traveller.getTravellerList()) {
            System.out.println("opening doors");
            checking.enterExit(this);
        }
    }

    //finds the elevator that can get to the caller fastest and adds the caller's current floor to its destination list.
    //Returns the elevator that was chosen, so that it can imprint upon the traveller.
    //like in twilight.
    public static Elevator callElevator(int callerFloor, int destinationFloor) {

        int callerDirection;
        //callerDirection is 1 if caller is moving upwards, -1 if downwards.
        if (callerFloor < destinationFloor) {
            callerDirection = 1;
        } else {
            callerDirection = -1;
        }

        Elevator bestElevator = new Elevator();
        double bestDistance = 99999;
        int pickupAppendPosition = 0;
        int dropoffAppendPosition = 0;

        //goes through all elevator objects, and sets bestElevator to the one with the best dist to pickup
        for (int i = 0; i < totalElevators; i++) {
            int tempPickupAppend = 0;
            Elevator elevator = elevatorList.get(i);
            double totalDistance = 0;
            boolean endTest = false;

            //runs for elevators with no destinations, then ends test
            if (elevator.getDestinations().isEmpty()) {
                totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                endTest = true;
            }

            //runs for elevators with 2 or more dests, destinations 1 through n-1
            for (int j = 0; j < (elevator.getDestinations().size() - 1) && !endTest; j++) {

                //if elevator will move past caller add the distance to the caller and end test
                if (
                        ((((int) elevator.getDestinations().get(j)) >= callerFloor)
                                && (callerFloor >= ((int) elevator.getDestinations().get(j))) && (callerDirection == -1))
                        || ((((int) elevator.getDestinations().get(j)) <= callerFloor)
                                && (callerFloor <= ((int) elevator.getDestinations().get(j))) && (callerDirection == 1))
                ) {

                    totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                    endTest = true;
                } else { //otherwise add distance to next destination, increment pickupAppendPosition, and continue test

                    totalDistance += Math.abs((int) elevator.getDestinations().get(j)
                            - (int) elevator.getDestinations().get(j + 1));
                    tempPickupAppend++;
                }
            }

            //runs for the last destination
            //add the distance from the final destination to the caller to totalDistance
            if (!endTest) {
                totalDistance += (Math.abs((int) elevator.getDestinations().get(elevator.getDestinations().size() - 1) - callerFloor));
            }

            //divides totalDistance by speed
            totalDistance /= elevator.speed;

            //checks if totalDistance is better than bestDistance, if so, updates bestDist and bestElevator
            if (totalDistance < bestDistance) {
                bestDistance = totalDistance;
                bestElevator = elevator;
                pickupAppendPosition = tempPickupAppend;
            }
        }

        //finds when the elevator will pass by the dropoff position, or will change directions away from the dropoff

        //if there are no destinations, we found our position
        boolean foundPosition = bestElevator.getDestinations().isEmpty();

        //runs for an elevator with 2+ destinations, runs for dests 1 thru n-1
        for (int i = 0; i < ((int) bestElevator.getDestinations().size() - 1) && !foundPosition; i++) {
            //if it does NOT pass by the dropoff or DOES start moving away, increase the append position
            if (
                    (!bestElevator.passCheck(i,destinationFloor)
                            || (Math.abs((int) bestElevator.destinations.get(i) - destinationFloor) <
                            Math.abs((int) bestElevator.destinations.get(i+1) - destinationFloor)))
            ) {
                dropoffAppendPosition++;
            } else { //otherwise, we found our position
                foundPosition = true;
            }
        }

        //runs for the last destination
        if (!foundPosition) {
            dropoffAppendPosition++;
            foundPosition = true;
        }

        //adjusts dropoff append position to account for pickup being appended
        if (pickupAppendPosition <= dropoffAppendPosition) {
            dropoffAppendPosition++;
        }

        //appends the pickup and dropoff
        bestElevator.addDestination(pickupAppendPosition, callerFloor);
        bestElevator.addDestination(dropoffAppendPosition,destinationFloor);

        //returns bestElevator for imprinting
        return bestElevator;
    }

    //Generic passCheck. Checks if the elevator will EVER pass a floor.
    public boolean passCheck(int floor) {
        if (destinations.isEmpty()) {
            return (false);
        } else {
            for (int i = 0; i < destinations.size() - 1; i++) {
                if (    //if floor is between the elevator at dest. i and where it's going, return true
                        ((int) destinations.get(i) >= floor && floor >= (int) destinations.get(i+1))
                        || ((int) destinations.get(i) <= floor && floor <= (int) destinations.get(i+1))
                ) {
                    return true;
                }
            }

            int d = destinations.size() - 1;
            if (
                    ((int) destinations.get(d) >= floor && floor >= (int) destinations.get(d+1))
                            || ((int) destinations.get(d) <= floor && floor <= (int) destinations.get(d+1))
            ) {
                return true;
            }
        }
        //if we haven't returned true yet, return false
        return false;
    }

    //Specific passCheck. Checks if an elevator passes by a floor as it goes from destination index X to X+1
    public boolean passCheck(int index, int floor) {
        return (((int) destinations.get(index) >= floor && floor >= (int) destinations.get(index + 1))
                || ((int) destinations.get(index) <= floor && floor <= (int) destinations.get(index + 1)));
    }

    //updates the direction that an elevator is moving. Runs every tick and after elevators get new destinations.
    public void directionCheck() {
        if (!destinations.isEmpty()) {
            if ((int) destinations.get(0) > position) {
                direction = 1;
            } else if ((int) destinations.get(0) < position) {
                direction = -1;
            }
        }
    }

    //adds a destination in the middle of an elevator's route. Updates direction immediately.
    public void addDestination(int appendPosition, int floor) {
        destinations.add(appendPosition, floor);
        directionCheck();
        System.out.println(this);
    }

    //adds a destination to the end of an elevator's route. Updates direction immediately.
    public void addDestination(int floor) {
        destinations.add(floor);
        directionCheck();
        System.out.println(this);
    }

    //toString. Prints id, position, and destinations.
    public String toString() {
        double roundedPos = ((int) (position * 100))/100.0;
        return "ID: " + id + ". Loc: " + roundedPos + ". Dests: " + destinations;
    }

    //getter methods
    public static int getTotalElevators() {
        return totalElevators;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public double getPosition() {
        return position;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getDirection() {
        return direction;
    }

    public int getOccupants() {
        return occupants;
    }

    public boolean getIsPlaceholder() {
        return isPlaceholder;
    }

    public ArrayList getElevatorList() {
        return elevatorList;
    }

    public ArrayList getDestinations() {
        return destinations;
    }

    public ArrayList getOccupantList() {
        return occupantList;
    }

    public int getID() {
        return id;
    }
}
