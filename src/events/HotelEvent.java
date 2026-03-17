package events;

public class HotelEvent {
    private final int time;
    private final HotelEventType eventType;
    private final Integer humanId;
    private final int data;

    public HotelEvent(HotelEventType eventType, int time, Integer humanId, int data) {
        this.eventType = eventType;
        this.time = time;
        this.humanId = humanId;
        this.data = data;
    }

    public HotelEventType getEventType() {
        return this.eventType;
    }

    public Integer getHumanId() {
        return this.humanId;
    }

    public int getData() {
        return this.data;
    }

    public int getTime() {
        return this.time;
    }
}
