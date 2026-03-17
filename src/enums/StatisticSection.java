package enums;

public enum StatisticSection {
    Hotel("Hotel"),
    CLEANING("Cleaning"),
    Activity("Activity");

    private final String str;

    StatisticSection(String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
}
