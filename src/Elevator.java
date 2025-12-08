import java.util.ArrayList;

public class Elevator {
    private static int totalElevators = 0;
    static ArrayList elevatorList = new ArrayList();
    private boolean isPlaceholder = false;

    private double maxSpeed; //speed is in floors per tick
    private double acceleration; //acceleration is in floors per tick per tick
    private int capacity;
    private int maxFloor;
    private int minFloor;

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
        position = startPosition;

        totalElevators++;
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

        System.out.println("CallElevator initiated.");

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
            Elevator elevator = (Elevator) elevatorList.get(i);
            double totalDistance = 0;
            boolean endTest = false;

            //runs for elevators with no destinations, then ends test
            if (elevator.getTotalDestinations() == 0) {
                totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                endTest = true;
            }

            //runs for elevator destinations 1 through n-1
            for (int j = 0; j < (elevator.getTotalDestinations() - 1) && !endTest; j++) {

                //if elevator will move past caller add the distance to the caller and end test
                if ((((int) elevator.getDestinations().get(j) >= callerFloor) && (elevator.getDirection() == -1) && (callerDirection == -1))
                        || (((int) elevator.getDestinations().get(j) <= callerFloor) && (elevator.getDirection() == 1) && (callerDirection == 1))) {

                    totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                    endTest = true;

                } else { //otherwise add distance to next destination, increment pickupAppendPosition, and continue test

                    totalDistance += Math.abs((Double) elevator.getDestinations().get(j)
                            - (Double) elevator.getDestinations().get(j + 1));
                    pickupAppendPosition++;
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
            }
        }

        //adds the destination to the best elevator
        bestElevator.addDestination(pickupAppendPosition, callerFloor);
    }

    //adds a destination in the middle of an elevator's route
    public void addDestination(int appendPosition, int floor) {
        System.out.println("Old Pos + Dests: " + position + " and " + destinations);
        destinations.add(appendPosition, floor);
        System.out.println("New Pos + Dests: " + position + " and " + destinations);
    }

    //adds a destination to the end of an elevator's route
    public void addDestination(int floor) {
        System.out.println("Old Pos + Dests: " + position + " and " + destinations);
        destinations.add(floor);
        System.out.println("New Pos + Dests: " + position + " and " + destinations);
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

}
