public class Building {
    private int floors;
    private int groundFloor;

    private void tick(){
        Traveller.tick();
        Elevator.tick();
    }
}
