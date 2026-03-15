package enums;

public enum StatisticSection {
    General("General"),
    Rooms("Rooms");

    private final String str;

    StatisticSection(String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }
}
