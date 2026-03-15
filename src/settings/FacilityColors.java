package settings;

import enums.FacilityState;
import facility.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;




public final  class FacilityColors {
    private FacilityColors() {}

    public static Map<Class<? extends Facility>, HashMap<FacilityState, Color>> map = new HashMap<>();

    // get Kamer -> map -> Kamer

    public static HashMap<FacilityState, Color> room = new HashMap<>();
    public static HashMap<FacilityState, Color> hall = new HashMap<>();
    public static HashMap<FacilityState, Color> lobby = new HashMap<>();
    public static HashMap<FacilityState, Color> lift = new HashMap<>();
    public static HashMap<FacilityState, Color> stairs = new HashMap<>();

    public static void setup() {
        // --- Kamer ---
        room.put(FacilityState.AVAILABLE1, new Color(224, 246, 218));
        room.put(FacilityState.AVAILABLE2, new Color(173, 222, 161));

        room.put(FacilityState.DEFAULT1, new Color(224, 246, 218));
        room.put(FacilityState.DEFAULT2, new Color(173, 222, 161));

        room.put(FacilityState.UNAVAILABLE1, new Color(246, 210, 210,255));
        room.put(FacilityState.UNAVAILABLE2, new Color(255, 138, 138,255));

        room.put(FacilityState.DIRTY1, new Color(249, 252, 224,255));
        room.put(FacilityState.DIRTY2, new Color(247, 253, 136,255));

        // --- Hall ---
        hall.put(FacilityState.DEFAULT1, new Color(241, 220, 193));
        hall.put(FacilityState.DEFAULT2, new Color(238, 200, 157));

        // -- Lobby --
        lobby.put(FacilityState.DEFAULT1, new Color(251, 225, 254));
        lobby.put(FacilityState.DEFAULT2, new Color(247, 179, 252));

        // --- Lift ---
        lift.put(FacilityState.DEFAULT1, new Color(225, 245, 254));
        lift.put(FacilityState.DEFAULT2, new Color(179, 229, 252));


        // --- Trap ---
        stairs.put(FacilityState.DEFAULT1, new Color(255, 248, 225));
        stairs.put(FacilityState.DEFAULT2, new Color(240, 230, 190));

        map.put(Room.class, room);
        map.put(Hall.class, hall);
        map.put(Lobby.class, lobby);
        map.put(Lift.class, lift);
        map.put(Stairs.class, stairs);
    }

}
