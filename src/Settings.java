import java.awt.Color;

public final class Settings {
    private Settings() {} // Geen instanties kunnen gemaakt worden.

    // Scherm Dimensies
    public static int schermBreedte = 1080;
    public static int schermHoogte = 720;

    // Achtergrond Kleur
    // Default = Color.WHITE
    public static Color achtergrondKleur = Color.BLACK;

    // Bepaald hoe hoog en breed elk Oppervlakte is
    // Default = 100
    public static int oppervlakGrootte = 100;

    // Bepaald hoeveel vakjes in een Oppervlakte staan
    // Default = 16
    public static int facilityTilesSize = 8;


    // Vakjes View
    public static boolean setSquaresAlternatingColors = true;

    // Oppervlakte Kleuren

    // Milliseconden per tik
    public static int ticks = 30;
}
