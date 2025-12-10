public class Runner {
    public static void main(String[] args) {
        Elevator e0 = new Elevator(0.6,0,0,0,2);
        Elevator e1 = new Elevator(0.6,0,0,0,3);
        Traveller t1 = new Traveller(0,15,0);
        Traveller t2 = new Traveller(0,15,0);
        Traveller t3 = new Traveller(0,15,0);
        for (int i = 0; i < 100; i++) {
            System.out.println("tick " + i);
            Traveller.tick();
            Elevator.tick();
        }
    }
}
