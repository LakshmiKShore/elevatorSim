public class Runner {
    public static void main(String[] args) {
        Elevator e0 = new Elevator(0,0,0,0,0,1);
        Elevator e1 = new Elevator(0,0,0,0,0,2);
        Elevator e2 = new Elevator(0,0,0,0,0,3);
        Elevator e3 = new Elevator(0,0,0,0,0,4);
        Elevator.callElevator(3,4);
        Elevator.callElevator(4,1);
        Elevator.callElevator(3,2);
    }
}
