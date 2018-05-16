package planeringssystem_v2;

public class ClosestPlats {

    private DataStore ds;
    private HTTPanrop ha;
    private OptPlan op;
    int tempis;
    boolean skippis;
    private String[] listaplatserList;

    public ClosestPlats(DataStore ds, HTTPanrop ha, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
        tempis = 0;
        skippis = false;
    }

    public void getClosestPlats() {

        try {

            listaplatserList = ha.messagetype();

            if (ds.counterFirstInstructions == 0) {
                ds.a = 44;
                ds.counterFirstInstructions = ds.counterFirstInstructions + 1;
            }

            int len = Integer.parseInt(listaplatserList[0]);
            ds.platsLista = new String[len];
            ds.startSlutNoder = new String[len];
            ds.platser = new String[len];
            ds.noder = new String[len];
            ds.startnod = new int[len];
            ds.slutnod = new int[len];

            for (int i = 0; i < len; i++) {
                ds.platsLista = listaplatserList[i + 1].split(";");
                ds.platser[i] = ds.platsLista[0];
                ds.noder[i] = ds.platsLista[1];
            }

            for (int j = 0; j < len; j++) {
                ds.startSlutNoder = ds.noder[j].split(",");
                ds.startnod[j] = Integer.parseInt(ds.startSlutNoder[0]) - 1;
                ds.slutnod[j] = Integer.parseInt(ds.startSlutNoder[1]) - 1;
            }

            int min = Integer.MAX_VALUE; //Detta är avståndet till den närmsta upphämtningsplatsen
            String cP = ""; //Detta är närmsta upphämtningsplatsen
            int dN = 0; //Detta är första noden i sista länken, Dijkstra ska räkna hit
            int lN = 0; //Detta är sista noden i sista länken

            for (int i = 0; i < ds.platser.length; i++) {
                Thread.sleep(1000);
                skippis = false;
                for (int j = 0; j < ds.tomPlats.size(); j++) {
                    if (ds.tomPlats.get(j) == ds.platser[i]) {
                        skippis = true;
                        System.out.println("NU SKA DET VARA EN PLATS SOM INTE HADE NÅGRA UPPDRAG KVAR, DET BORDE VARA PLATS" + ds.tomPlats);
                    }
                }
                String nuvarande = ha.messagetype(ds.platser[i])[0];
                System.out.println("NUVARANDE " + nuvarande);
                if (!skippis) {

                    if (!nuvarande.equals("0")) {

                        tempis = op.getCost(ds.a, ds.startnod[i]);
                        if (tempis < min) {
                            min = tempis;
                            cP = ds.platser[i];
                            dN = ds.startnod[i];
                            lN = ds.slutnod[i];
                        }
                    } else {
                        System.out.println("Tom plats" + ds.platser[i]);
                    }
                }
            }
            ds.distanceCP = min + ds.distanceDO;
            ds.dest_node = dN;
            ds.last_node = lN;
            ds.valdPlats = cP;

        } catch (InterruptedException e) {
        }

    }
}
