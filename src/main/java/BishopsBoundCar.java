import java.util.Random; // to arrvie cars on random intervals

public class BishopsBoundCar extends Car implements Runnable {
    private Bridge bridge;
    private Random random;

    public BishopsBoundCar(String name, Bridge bridge) {
        super(name);//call the constructor of parent class (Car)
        this.bridge = bridge;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {//since assignment says infinte loop, so...
            try {
                Thread.sleep(random.nextInt(5000) + 1000); //1-6 sleeps secs before approaching the bridge
                System.out.println(getName() + " is approaching the bridge...");
                if (!bridge.isEmpty() && bridge.hasLionsBoundCars())//cars in brdige and at least 1 car opposite direction coming
                    System.out.println(getName() + " waiting for Lion's bound cars to clear");
                bridge.getBishopsBoundSemaphore().acquire();//ask for permission
                if (bridge.hasLionsBoundCars()) {
                    bridge.getBishopsBoundSemaphore().release();//give back the permission
                    continue;//jump back to top of while loop
                }
                //start crossing
                bridge.addCar(this);
                //time to cross the bridge (4 sec)
                Thread.sleep(4000);//takes four sec to cross
                bridge.removeCar(this); // exit the bridge
                //release the resource
                if (bridge.isEmpty()) {
                    bridge.getLionBoundSemaphore().release();//if i was the last car i signal lions car to go
                }
                bridge.getLionBoundSemaphore().release();//relaesing for next bishops cars
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//preserving interrputed status of thread
                System.out.println(getName() + " was interrupted !!!");
                break; // this breaks the whileloop
            }
        }
    }

}
