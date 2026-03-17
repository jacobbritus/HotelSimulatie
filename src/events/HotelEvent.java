package events;

public class HotelEvent {
    private int time;
    private HotelEventType eventType;
    private int guestId;
    private int data;

    public HotelEvent(HotelEventType eventType, int time) {
        this.eventType = eventType;
        this.time = time;
    }

    public HotelEventType getEventType() {
        return eventType;
    }

    public int getTime() {
        return this.time;
    }
}
