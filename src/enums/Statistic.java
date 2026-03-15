package enums;

public enum Statistic {
    Guests("General", "Guests"),
    Cleaners("General", "Cleaners"),
    RoomsOccupied("Rooms", "Rooms Occupied"),
    DirtyRooms("Rooms", "Dirty Rooms"),
    TotalRoomsCleaned("Rooms", "Rooms Cleaned");


    private final String section;

    private final String str;

    Statistic(String section, String str) {
        this.section = section;
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
    public String getSection() {
        return this.section;
    }
}
