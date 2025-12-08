public class Runner {
    public static void main(String[] args) {
        Elevator e1 = new Elevator(0,0,0,0,0,1);
        Elevator e2 = new Elevator(0,0,0,0,0,2);
        Elevator e3 = new Elevator(0,0,0,0,0,3);
        Elevator e4 = new Elevator(0,0,0,0,0,4);
        Elevator.callElevator(3,4);
    }
}
