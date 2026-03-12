import javax.swing.*;

public class SimulatieController extends JPanel {
    Simulatie simulatie;
    Timer HTEtimer;

    public SimulatieController(Simulatie simulatie) {
        this.simulatie = simulatie;
        HTEtimer = new Timer(Instellingen.millisecondenPerTik, e -> simulatie.update());
    }

    public void start() {
        HTEtimer.start();
    }
}