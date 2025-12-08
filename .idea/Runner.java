public class Runner {
    public static void main(String[] args) {
        Elevator e1 = new Elevator(0,0,0,0,0,4);
        Elevator e2 = new Elevator(0,0,0,0,0,4);
        Elevator e3 = new Elevator(0,0,0,0,0,4);
        Elevator e4 = new Elevator(0,0,0,0,0,4);
        Elevator.callElevator(1,3);
        Elevator.callElevator(2,3);
        Elevator.callElevator(3,3);
        Elevator.callElevator(4,3);
    }
}
