package enums;

public enum Statistic {
    GUEST_COUNT(StatisticSection.AGENTS, "Guests", UnitType.NUMERICAL),
    CLEANER_COUNT(StatisticSection.AGENTS, "Cleaners", UnitType.NUMERICAL),
    ROOMS_OCCUPIED(StatisticSection.ROOMS, "Room Occupation", UnitType.PERCENTAGE),
    DIRTY_ROOM_COUNT(StatisticSection.ROOMS, "Dirty Rooms", UnitType.NUMERICAL);
//    TotalRoomsCleaned(StatisticSection.Rooms, "Rooms Cleaned", UnitType.NUMERICAL);
//    TotalRoomsBooked(StatisticSection.Rooms, "Rooms Booked", UnitType.NUMERICAL);


    private final StatisticSection section;

    private final String title;
    private final UnitType unit;

    Statistic(StatisticSection section, String title, UnitType unit) {
        this.section = section;
        this.title = title;
        this.unit = unit;
    }

    public String getTitle() {
        return this.title;
    }
    public UnitType getUnit() {
        return this.unit;
    }
    public StatisticSection getSection() {
        return this.section;
    }
}
