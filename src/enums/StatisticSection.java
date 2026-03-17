package enums;

public enum StatisticSection {
    AGENTS("Agents"),
    ROOMS("Rooms");

    private final String str;

    StatisticSection(String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
}
