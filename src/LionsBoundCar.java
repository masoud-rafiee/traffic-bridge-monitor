import java.util.Random;

public class LionsBoundCar extends Car implements Runnable {

    private Bridge bridge;
    private Random random;

    public LionsBoundCar(String name, Bridge bridge) {
        super(name);
        this.bridge=bridge;
        this.random=new Random();
    }
    @Override//this automatically executes when the car' thread starts
    public void run() {
        while(true){
            try{
                //lion cars 100x faster than bishops --> probably bc people want to have fun more than knowledge (specially in BU)
                //sleep for rand time 10-60 milisecs
                Thread.sleep((random.nextInt(50)+10));
                System.out.println(getName()+ " is approaching the bridge...");
                //check if it's safe to enter
                if (!bridge.isEmpty()&& bridge.hasBishopsBoundCars()){
                    System.out.println(getName()+" waiting for Bishop's bound cars to clear");
                }
                bridge.getLionBoundSemaphore().acquire();//waiting for permisiion to acquire
                if (bridge.hasBishopsBoundCars()){//if there are cars coming from bishops, wait
                    bridge.getLionBoundSemaphore().release();//give back the permission
                    continue;
                }
                //start crossing now
                bridge.addCar(this);
                //faster : takes 1 sec to pass
                Thread.sleep(1000);
                bridge.removeCar(this);//done passing
                //relaseing the resources
                if (bridge.isEmpty()){ //if it was the last car
                    bridge.getBishopsBoundSemaphore().release();//signal bishops one to pass
                } bridge.getLionBoundSemaphore().release();//release for the lion too for the future cars
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();//if it got messy when in sleep
                System.out.println(getName()+ " was interrupted!!!");
                break;
            }
        }
    }
}
