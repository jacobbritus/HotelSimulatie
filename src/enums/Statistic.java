package enums;

public enum Statistic {
    GUEST_COUNT(StatisticSection.Hotel, "Guests", UnitType.NUMERICAL),
    ROOMS_OCCUPIED(StatisticSection.Hotel, "Occupied Rooms", UnitType.PERCENTAGE),
    AVAILABLE_ROOMS(StatisticSection.Hotel, "Available Rooms", UnitType.PERCENTAGE),

    CLEANER_COUNT(StatisticSection.CLEANING, "Cleaners", UnitType.NUMERICAL),
    DIRTY_ROOM_COUNT(StatisticSection.CLEANING, "Dirty Rooms", UnitType.NUMERICAL),
    CLEAN_ROOM_COUNT(StatisticSection.CLEANING, "Clean Rooms", UnitType.NUMERICAL),

    CHECK_INS(StatisticSection.Activity, "Check-ins", UnitType.NUMERICAL),
    CHECK_OUTS(StatisticSection.Activity, "Check-outs", UnitType.NUMERICAL),
    PENDING_EVENTS(StatisticSection.Activity, "Pending Events", UnitType.NUMERICAL);



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

//
//Guest Satisfaction
//Average Cleaning Time
//Queue Length
//Agent Idle Time
//Room Turnover Rate