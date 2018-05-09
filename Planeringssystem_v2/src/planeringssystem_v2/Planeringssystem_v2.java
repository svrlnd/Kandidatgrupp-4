package planeringssystem_v2;

public class Planeringssystem_v2 {

    DataStore ds;
    GUI gui;
    RobotRead rr;
    Thread t1;
    GUIUpdate gu;
    Thread t2;
    OptPlan op;
    HTTPanrop ha;
    ClosestPlats cp;
    UppdragsInfo ui;
    Thread t3;
    GroupRead gr;
    Thread t4;
    Stop stop;

    Planeringssystem_v2() {

        ds = new DataStore();

        ds.setFileName("streets.txt");

        ds.readNet();

        gui = new GUI(ds);
        gui.setVisible(true);

        gu = new GUIUpdate(gui);
        t2 = new Thread(gu);
        t2.start();

        op = new OptPlan(ds);

        //Kalla på HTTp anrop för att ta reda på vilken som är närmaste platsen
        ha = new HTTPanrop();
        
        //Räkna ut närmaste plats
        cp = new ClosestPlats(ds, ha, op);
        cp.getClosestPlats();

        //Körinstruktioner till närmaste plats
        op.createPlan(ds.a, ds.dest_node);
        op.createInstructions();

        rr = new RobotRead(ds, gui, stop);
        t1 = new Thread(rr);
        t1.start();
        
        //Ta reda på vilka uppdrag vi ska ta på den platsen
        ui = new UppdragsInfo(ds, ha, op);
        t3 = new Thread(ui);
        t3.start();


        //Meddela gruppen om vilket uppdrag vi vill ta
        //sedan starta tråd GroupRead

        gr = new GroupRead(ds, gui);
        t4 = new Thread(gr);
        t4.start();


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Planeringssystem_v2 x = new Planeringssystem_v2();
    }

}
