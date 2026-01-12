import java.util.ArrayList;

public class Dataset {

    public static int nextID = 0;

    private int id;
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

    private static ArrayList<Dataset> datasets = new ArrayList<Dataset>();

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

        this.id = nextID;
        nextID++;

        totalFloors = bMaxFloor - bMinFloor;
        numElevators = eTravelDist.length;
        numTravellers = tTimePresent.length;

        datasets.add(this); //adds itself to the list of datasets
    }

    //prints every dataset's statistics.
    public static void reportAll() {
        for (Dataset d : datasets) {
            d.printStatistics();
        }
    }

    //prints the idle tick percent for elevators and travellers, and the average elevator travel distance per call.
    public void printStatistics() {
        System.out.println(this);
        System.out.println("Elevators were idle for an average of " + (eIdleTickPercent() * 100) + "% of the simulation.");
        System.out.println("Elevators travelled an average of " + eTravelDistPerCall() + " floors per call.");
        System.out.println("Travellers were idle for an average of " + tTimeIdlePerCall() + " ticks after each call.");
    }


    //Below are instance methods which calculate various statistics about data.

    //calculates the average percentage of time that elevators spend waiting. Returns a double between 0 and 1 (inclusive).
    public double eIdleTickPercent() {
        return average(eTimeWaiting)/simTicks;
    }

    //calculates the average distance (floors) that an elevator travels, per time it was called. Returns a double.
    public double eTravelDistPerCall() {
        return average(arrayDiv(eTravelDist,eNumCalls)); //Should update to use the distance that a call requested the elevator to travel
    }                                                    //to better represent the efficiency of overlapping calls.

    //calculates the average number of ticks per call that a traveller spends waiting for an elevator to arrive.
    public double tTimeIdlePerCall() {
        return average(arrayDiv(tTimeWaiting,tNumCalls));
    }

    public String toString() {
        return ("Dataset Number " + id);
    }

    //Below are static arrayformula methods. They run basic calculations on integer and double arrays.
    //For arrayformulas involving two or more arrays, all arrays must be the same length.

    //counts the number of times that a value appears in an integer array.
    public static int count(int[] arr, int value) {
        int count = 0;
        for (int a : arr) {
            if (a == value) {
                count++;
            }
        }
        return count;
    }

    //counts the number of times that a value appears in an double array.
    public static int count(double[] arr, double value) {
        int count = 0;
        for (double a : arr) {
            if (a == value) {
                count++;
            }
        }
        return count;
    }

    //returns the average value of an integer array.
    public static double average(int[] arr) {
        return (double) sum(arr)/arr.length;
    }

    //returns the average value of a double array.
    public static double average(double[] arr) {
        return sum(arr)/arr.length;
    }

    //returns the sum of an integer array.
    public static int sum(int[] arr) {
        int sum = 0;
        for (int a : arr) {
            sum += a;
        }
        return sum;
    }

    //returns the sum of a double array.
    public static double sum(double[] arr) {
        double sum = 0;
        for (double a : arr) {
            sum += a;
        }
        return sum;
    }

    //divides the elements of two integer arrays. returns a double array. returns {-1.0} when fed two arrays with different lengths.
    //If a pair of values has a divisor of 0, that pair will be removed and the resulting array will be shorter than the input arrays.
    public static double[] arrayDiv(int[] divided, int[] divisor) {
        int resultLength = divisor.length - count(divisor, 0);
        int skipCount = 0;

        if (divided.length != divisor.length) {
            return new double[-1];
        } else {
            double[] result = new double[resultLength];
            for (int i = 0; i < divided.length; i++) {
                if (divisor[i] != 0) {
                    result[i-skipCount] = (double) divided[i]/divisor[i];
                } else {
                    skipCount++;
                }
            }
            return result;
        }
    }

    //divides the elements a double array by an int array. returns a double array. returns {-1.0} when fed two arrays with different lengths.
    //If a pair of values has a divisor of 0, that pair will be removed and the resulting array will be shorter than the input arrays.
    public static double[] arrayDiv(double[] divided, int[] divisor) {
        int resultLength = divisor.length - count(divisor, 0);
        int skipCount = 0;

        if (divided.length != divisor.length) {
            return new double[-1];
        } else {
            double[] result = new double[resultLength];
            for (int i = 0; i < divided.length; i++) {
                if (divisor[i] != 0) {
                    result[i-skipCount] = divided[i]/divisor[i];
                } else {
                    skipCount++;
                }
            }
            return result;
        }
    }

    //divides the elements an int array by a double array. returns a double array. returns {-1.0} when fed two arrays with different lengths.
    public static double[] arrayDiv(int[] divided, double[] divisor) {
        int resultLength = divisor.length - count(divisor, 0);
        int skipCount = 0;

        if (divided.length != divisor.length) {
            return new double[-1];
        } else {
            double[] result = new double[resultLength];
            for (int i = 0; i < divided.length; i++) {
                if (divisor[i] != 0) {
                    result[i-skipCount] = divided[i]/divisor[i];
                } else {
                    skipCount++;
                }
            }
            return result;
        }
    }

    //divides the elements of two double arrays. returns a double array. returns {-1.0} when fed two arrays with different lengths.
    public static double[] arrayDiv(double[] divided, double[] divisor) {
        int resultLength = divisor.length - count(divisor, 0);
        int skipCount = 0;

        if (divided.length != divisor.length) {
            return new double[-1];
        } else {
            double[] result = new double[resultLength];
            for (int i = 0; i < divided.length; i++) {
                if (divisor[i] != 0) {
                    result[i-skipCount] = divided[i]/divisor[i];
                } else {
                    skipCount++;
                }
            }
            return result;
        }
    }
}
