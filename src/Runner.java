public class Runner {
    public static void main(String[] args) {
        Elevator e0 = new Elevator(0.6,0,0,0,2);
        Elevator e1 = new Elevator(0.6,0,0,0,3);
        Elevator.callElevator(1,7);
        Elevator.callElevator(3,5);
        Elevator.callElevator(4,2);
        for (int i = 0; i < 11; i++) {
            Elevator.tick();
        }
    }
}
//