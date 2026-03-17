package enums;


public enum SidebarPage {
    ROOM("Room "),
    ROOMS("Rooms Overview"),
    GUESTS("Guests"),
    CLEANERS("Cleaners"),
    OVERVIEW("Hotel Overview"),
    SETTINGS("Settings"),
    QUIT("Quit Simulation");

    public final String title;

    SidebarPage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
