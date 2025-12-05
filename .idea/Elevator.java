import java.sql.Array;
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

    //finds the elevator that can get to the caller fastest and adds the caller's current floor to its destination list.
    public static void callElevator(int callerFloor) {
        Elevator bestElevator = new Elevator();

        for (int i = 0; i < totalElevators; i++) {
            Elevator elevator = (Elevator) elevatorList.get(i);
            double bestDistance = 99999;

            //if the elevator isnt moving, or the elevator is moving towards the caller's floor, check distance
            if (elevator.isMoving = false || (elevator.getPosition() > callerFloor && elevator.getDirection() == -1)
                    || (elevator.getPosition() < callerFloor && elevator.getDirection() == 1)) {

                //if this elevator is closer than the others we tested, set this elevator as best elevator
                if (bestDistance > Math.abs(elevator.getPosition())) {
                    bestDistance = Math.abs(elevator.getPosition());
                    bestElevator = elevator;
                }
            }
        }

        //if no best elevator has been found (none are valid), check moving elevators
        if (bestElevator.getIsPlaceholder()) {
            for (int i = 0; i < totalElevators; i++) {
                Elevator elevator = (Elevator) elevatorList.get(i);
                double bestDistance = 99999;
                double totalDistance = 0;
                boolean endTest = false;

                //if the elevator isnt moving, or the elevator is moving towards the caller's floor, add to dist and end check
                if (elevator.isMoving = false || (elevator.getPosition() > callerFloor && elevator.getDirection() == -1)
                        || (elevator.getPosition() < callerFloor && elevator.getDirection() == 1)) {

                    totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                    endTest = true;

                }

                for (int j = 0; j < (elevator.getTotalDestinations() - 2) && !endTest; j++) {
                    //if elevator will move past caller add dist to caller and end test
                    if ((elevator.getPosition() > callerFloor && elevator.getDirection() == -1) ||
                            (elevator.getPosition() < callerFloor && elevator.getDirection() == 1)) {

                        totalDistance += Math.abs(elevator.getPosition() - callerFloor);
                        endTest = true;

                    } else { //otherwise add distance to next destination and continue test

                        totalDistance += Math.abs((Double) elevator.getDestinations().get(j)
                                - (Double) elevator.getDestinations().get(j));

                    }
                }

            }
        }

    }


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
