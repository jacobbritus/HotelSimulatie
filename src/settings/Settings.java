package settings;

import java.awt.Color;

public final class Settings {
    private Settings() {} // Geen instanties kunnen gemaakt worden.

    // Scherm Dimensies
    public static int schermBreedte = 1920;
    public static int schermHoogte = 1080;

    // Sidebar width
    public static int sidebarWidth = 400;

    // Theme
    // Default = Color.WHITE
//    public static Color achtergrondKleur = new Color(240, 240, 240, 255);
//    public static Color themeColor = new Color(255, 255, 255,255);
//    public static Color themeColor2 = new Color(220, 220, 220,255);
//    public static Color textColor = Color.BLACK;

    public static Color achtergrondKleur = new Color(25, 25, 25, 255);
    public static Color themeColor = new Color(30, 30, 30,255);
    public static Color themeColor2 = new Color(40, 40, 40,255);
    public static Color themeColor3 = new Color(50, 50, 50,255);
    public static Color textColor = Color.WHITE;

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
    public static int ticks = 1000;

    // human.Guest staytime (3 hours)
    public static int guestBaseStayTime = 60 * 60 * 3; // can be used like: this * random number between 1 and 5

    // Guest spawn time (1 per hour)
    public static int guestSpawnTime = 60 * 60;

    // human.Cleaner cleaningTime (30 minutes)
    public static int cleanerBaseCleaningTime = 60 * 30 ;

    public static String convertTime(int milliseconds) {
        int seconds = milliseconds % 60;
        int minutes = (milliseconds / 60) % 60;
        int hours = milliseconds / 3600;

        return String.format(
                "%02d:%02d:%02d",
                hours, minutes, seconds
        );
    }
}
