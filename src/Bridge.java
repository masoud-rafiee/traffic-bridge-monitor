import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Bridge {
    private List<Car> carsOnBridge; // holding cars
    private Semaphore bishopsBoundSemaphore; // semaphore for bishops-bound cars
    private Semaphore lionBoundSemaphore;

    public Bridge(){ //constructor
        carsOnBridge=new ArrayList<>();
        bishopsBoundSemaphore = new Semaphore(1); //one direction of traffic at a time (one thread only)
        lionBoundSemaphore = new Semaphore(1); // same
    }
    //adding cars
    public synchronized void addCar(Car car){
        carsOnBridge.add(car);//car is now on the bridge
        System.out.println(car.getName()+" entered the bridge");
        System.out.println("Cars on bridge: "+ printCars());// all cars on bridge
    }
    //removing cars (after crossing the bridge)
    public synchronized void removeCar(Car car){
        carsOnBridge.remove(car);
        System.out.println(car.getName()+" exited the bridge.");
        System.out.println("Cars on bridge: "+ printCars());// to show the current status
    }
    //to print all cars
    public synchronized String printCars(){
        StringBuilder sb = new StringBuilder();
        for (Car car: carsOnBridge)
            sb.append(car.getName()).append(", ");
        return !sb.isEmpty() ?sb.substring(0,sb.length()-2):"None!";//if there are cars on bridge remove the last "," else return NONE
    }
    //accessor for semaphore for Bishops-Bound (since the sem is private for encaps. so I made a getter)
    public Semaphore getBishopsBoundSemaphore(){return bishopsBoundSemaphore;}
    public Semaphore getLionBoundSemaphore(){return lionBoundSemaphore;}

    //checking if there is any opposite cars on bridge (MUTUAL EXCLUSION)
    public synchronized boolean hasBishopsBoundCars(){
        for (Car car : carsOnBridge) if (car instanceof BishopsBoundCar)return true;
        return false;
    }
    //same for lion-direction cars
    public synchronized boolean hasLionsBoundCars(){
        for (Car car : carsOnBridge) if (car instanceof LionsBoundCar)return true;
        return false;
    }
    //bridge emptiness or not
    public synchronized boolean isEmpty(){return carsOnBridge.isEmpty();}
}
