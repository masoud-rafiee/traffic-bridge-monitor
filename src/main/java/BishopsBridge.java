public class BishopsBridge {
    public static void main(String[] args){
        Bridge bridge = new Bridge();
        System.out.println("Three Bishops Cars Are: \n1-BUGATTI\n2-MASERATI\n3-PORSCHE");
        BishopsBoundCar b1 = new BishopsBoundCar("BUGATTI", bridge);
        BishopsBoundCar b2 = new BishopsBoundCar("MASERATI", bridge);
        BishopsBoundCar b3 = new BishopsBoundCar("PORSCHE", bridge);

        System.out.println("\nTwo Lions Cars Are: \n1-FIAT\n2-MAZDA\n");
        LionsBoundCar l1=new LionsBoundCar("FIAT", bridge);
        LionsBoundCar l2=new LionsBoundCar("MAZDA", bridge);

        new Thread(b1).start();//starting the Bugatti ;)
        new Thread(b2).start();//starting the Maserati
        new Thread(b3).start();//starting the Porsche
        new Thread(l1).start();//starting the Fiat
        new Thread(l2).start();//starting the Mazda

        try {
            Thread.sleep(60000);//run for 1 min
            System.exit(0);//terminate all threads once the time expires afte 1 min
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
