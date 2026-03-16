package enums;

public enum StatisticSection {
    Agents("Agents"),
    Rooms("Rooms");

    private final String str;

    StatisticSection(String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
}
