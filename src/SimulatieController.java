import javax.swing.*;

public class SimulatieController {

    private final Simulatie simulatie;
    private final Timer HTEtimer;

    public SimulatieController(Simulatie simulatie) {
        this.simulatie = simulatie;
        this.HTEtimer = new Timer(Instellingen.millisecondenPerTik, e -> simulatie.update());
    }

    public void start() {
        HTEtimer.start();
    }

    public void setTickSpeed(int ms) {
        HTEtimer.setDelay(ms);
    }
}