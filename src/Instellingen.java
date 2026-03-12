import java.awt.Color;

public final class Instellingen {
    private Instellingen() {} // Geen instanties kunnen gemaakt worden.

    // Scherm Dimensies
    public static int schermBreedte = 1920;
    public static int schermHoogte = 1080;

    // Achtergrond Kleur
    // Default = Color.WHITE
    public static Color achtergrondKleur = Color.BLACK;

    // Bepaald hoe hoog en breed elk Oppervlakte is
    // Default = 100
    public static int oppervlakGrootte = 100;

    // Bepaald hoeveel vakjes in een Oppervlakte staan
    // Default = 16
    public static int oppervlakVakjes = 16;


    // Vakjes View
    public static boolean vakjesAlternerendeKleuren = false;

    // Oppervlakte Kleuren

    // Milliseconden per tik
    public static int millisecondenPerTik = 1000;
}
