import java.awt.Color;

public final class Settings {
    private Settings() {} // Geen instanties kunnen gemaakt worden.

    // Scherm Dimensies
    public static int schermBreedte = 1080;
    public static int schermHoogte = 720;

    // Achtergrond Kleur
    // Default = Color.WHITE
    public static Color achtergrondKleur = new Color(230, 230, 230, 255);
    public static Color themeColor = new Color(240, 240, 240,255);
    public static Color themeColor2 = new Color(200, 200, 200,255);

//    public static Color achtergrondKleur = new Color(20, 20, 20, 255);
//    public static Color themeColor = new Color(30, 30, 30,255);
//    public static Color themeColor2 = new Color(56, 56, 56,255);

    // Bepaald hoe hoog en breed elk Oppervlakte is
    // Default = 100
    public static int oppervlakGrootte = 100;

    // Bepaald hoeveel vakjes in een Oppervlakte staan
    // Default = 16
    public static int facilityTilesSize = 16;


    // Vakjes View
    public static boolean setSquaresAlternatingColors = false;

    // Oppervlakte Kleuren

    // Milliseconden per tik
    public static int ticks = 20;
}
