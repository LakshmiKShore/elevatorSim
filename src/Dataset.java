public class Dataset {

    private int simTicks;
    private int bMinFloor;
    private int bMaxFloor;
    private int totalFloors;
    private int numElevators;
    private int numTravellers;
    private double[] eTravelDist;
    private int[] eTimeWaiting;
    private int[] eNumCalls;
    private int[] tTimePresent;
    private int[] tTimeWaiting;
    private int[] tNumCalls;

    //Full constructor. takes a lot of inputs. Building.getData handles it.
    public Dataset(int simTicks, int bMinFloor, int bMaxFloor, double[] eTravelDist, int[] eTimeWaiting,
                   int[] eNumCalls, int[] tTimePresent, int[] tTimeWaiting, int[] tNumCalls) {
        this.simTicks = simTicks;
        this.bMinFloor = bMinFloor;
        this.bMaxFloor = bMaxFloor;
        this.eTravelDist = eTravelDist;
        this.eTimeWaiting = eTimeWaiting;
        this.eNumCalls = eNumCalls;
        this.tTimePresent = tTimePresent;
        this.tTimeWaiting = tTimeWaiting;
        this.tNumCalls = tNumCalls;

        totalFloors = bMaxFloor - bMinFloor;
        numElevators = eTravelDist.length;
        numTravellers = tTimePresent.length;
    }

    //calculates
    public double eIdleTickPercent() {

    }
}
