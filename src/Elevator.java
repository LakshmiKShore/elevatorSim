import java.util.ArrayList;

public class Elevator {
    private static int totalElevators = 0;
    private static int nextID = 0;
    static ArrayList elevatorList = new ArrayList();
    private boolean isPlaceholder = false;

    private double maxSpeed; //speed is in floors per tick
    private double acceleration; //acceleration is in floors per tick per tick
    private int capacity;
    private int maxFloor;
    private int minFloor;
    private int id;

    private double currentSpeed;
    private double position; //position is in reference to floor number
    private boolean isMoving;
    private int direction; //1 is up, -1 is down
    private int occupants;
    private int totalDestinations;
    private ArrayList destinations = new ArrayList();
    private ArrayList occupantList = new ArrayList();

    //constructor takes speed accel and capacity, increments totalelevators, adds itself to the array
    public Elevator(double maxSpeed, double acceleration, int capacity, int minFloor, int maxFloor, int startPosition) {
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
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
    }

    //runs the elevator's movement for a given tick
    public void tick() {
        //unimplemented
    }

    //finds the elevator that can get to the caller fastest and adds the caller's current floor to its destination list.
    public static void callElevator(int callerFloor, int destinationFloor) {

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
            Elevator elevator = (Elevator) elevatorList.get(i);
            System.out.print("Running for elevator " + elevator.getID() + ": ");
            double totalDistance = 0;
            boolean endTest = false;

            //runs for elevators with no destinations, then ends test
            if (elevator.getTotalDestinations() == 0) {
                totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                endTest = true;
                System.out.print("No destinations. ");
            }

            //runs for elevators with 2 or more dests, destinations 1 through n-1
            for (int j = 0; j < (elevator.getTotalDestinations() - 1) && !endTest; j++) {

                //if elevator will move past caller add the distance to the caller and end test
                if (
                        ((((int) elevator.getDestinations().get(j)) >= callerFloor)
                                && (callerFloor >= ((int) elevator.getDestinations().get(j))) && (callerDirection == -1))
                        || ((((int) elevator.getDestinations().get(j)) <= callerFloor)
                                && (callerFloor <= ((int) elevator.getDestinations().get(j))) && (callerDirection == 1))
                ) {

                    totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                    endTest = true;
                    System.out.print("Elevator passes by caller. ");

                } else { //otherwise add distance to next destination, increment pickupAppendPosition, and continue test

                    totalDistance += Math.abs((int) elevator.getDestinations().get(j)
                            - (int) elevator.getDestinations().get(j + 1));
                    tempPickupAppend++;
                    System.out.print("Elevator does not pass caller. ");
                }
            }

            //runs for the last destination
            //add the distance from the final destination to the caller to totalDistance
            if (!endTest) {
                totalDistance += (Math.abs((int) elevator.getDestinations().get(elevator.getTotalDestinations() - 1) - callerFloor));
            }

            //checks if totalDistance is better than bestDistance, if so, updates bestDist and bestElevator
            if (totalDistance < bestDistance) {
                bestDistance = totalDistance;
                bestElevator = elevator;
                pickupAppendPosition = tempPickupAppend;
            }
            System.out.print("Total Distance: " + totalDistance + ". ");
        }
        System.out.println("Best Elevator: " + bestElevator.getID() + ". Distance: " + bestDistance + ".");

        //finds when the elevator will pass by the dropoff position, or will change directions away from the dropoff
        boolean foundPosition = false;

        //runs for an elevator with 0 destinations
        if (bestElevator.getTotalDestinations() == 0) {
            foundPosition = true;
        }

        for (int i = 0; i < ((int) bestElevator.getTotalDestinations() - 1) && !foundPosition; i++) {

            //if it does NOT pass by the dropoff or start moving away, increase the append position
            if (
                    !(((((int) bestElevator.getDestinations().get(i)) >= destinationFloor)
                            && (destinationFloor >= ((int) bestElevator.getDestinations().get(i + 1))))
                            || ((((int) bestElevator.getDestinations().get(i)) <= destinationFloor)
                            && (destinationFloor <= ((int) bestElevator.getDestinations().get(i + 1))))
                            || (Math.abs((int) bestElevator.getDestinations().get(i)) < Math.abs((int) bestElevator.getDestinations().get(i + 1))))
            ) {
                dropoffAppendPosition++;
            } else { //otherwise, we found our position
                foundPosition = true;
            }
        }

        //runs for the last destination
        if (!foundPosition) {
            dropoffAppendPosition++;
        }

        //adjusts dropoff append position to account for pickup being appended
        if (pickupAppendPosition <= dropoffAppendPosition) {
            dropoffAppendPosition++;
        }

        //appends the pickup and dropoff
        bestElevator.addDestination(pickupAppendPosition, callerFloor);
        bestElevator.addDestination(dropoffAppendPosition,destinationFloor);

    }

    public void directionCheck() {
        if (totalDestinations != 0) {
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
        totalDestinations++;
        directionCheck();
        System.out.println("ID: " + id + ". Loc: " + position + ". Dests: " + destinations);
    }

    //adds a destination to the end of an elevator's route. Updates direction immediately.
    public void addDestination(int floor) {
        destinations.add(floor);
        totalDestinations++;
        directionCheck();
        System.out.println("ID: " + id + ". Loc: " + position + ". Dests: " + destinations);
    }


    //getter methods
    public static int getTotalElevators() {
        return totalElevators;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAcceleration() {
        return acceleration;
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

    public double getCurrentSpeed() {
        return currentSpeed;
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

    public int getTotalDestinations() {
        return totalDestinations;
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
