package enums;

public enum FontWeight {
    BOLD("Bold"),
    ITALIC("Italic"),
    MEDIUM("Medium"),
    REGULAR("Regular"),
    SEMIBOLD("SemiBold");

    private final String str;

    FontWeight(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
