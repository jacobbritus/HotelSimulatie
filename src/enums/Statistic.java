package enums;

public enum Statistic {
    Guests("Agents", "Guests", "Numerical"),
    Cleaners("Agents", "Cleaners", "Numerical"),
    RoomsOccupied("Rooms", "Room Occupation", "Percentage"),
    DirtyRooms("Rooms", "Dirty Rooms", "Numerical"),
    TotalRoomsCleaned("Rooms", "Rooms Cleaned", "Numerical");
//    TotalRoomsBooked("Rooms", "Rooms Booked", "Numerical");


    private final String section;

    private final String str;
    private final String unit;

    Statistic(String section, String str, String unit) {
        this.section = section;
        this.str = str;
        this.unit = unit;
    }

    public String getString() {
        return this.str;
    }
    public String getUnit() {
        return this.unit;
    }
    public String getSection() {
        return this.section;
    }
}
