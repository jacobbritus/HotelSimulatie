package events;

public class HotelEvent {
    private final int time;
    private final HotelEventType eventType;
    private final Integer guestId;
    private final int data;

    public HotelEvent(HotelEventType eventType, int time, Integer guestId, int data) {
        this.eventType = eventType;
        this.time = time;
        this.guestId = guestId;
        this.data = data;
    }

    public HotelEventType getEventType() {
        return this.eventType;
    }

    public Integer getGuestId() {
        return this.guestId;
    }

    public int getData() {
        return this.data;
    }

    public int getTime() {
        return this.time;
    }
}
