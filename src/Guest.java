import java.awt.*;

public class Guest extends Human{
    private boolean isCheckedIn;
    public Guest(Tile tile) {
        super(tile);
        this.isCheckedIn = false;
        this.getTile().setBackground(Color.RED);
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.isCheckedIn) color = Color.GREEN;
        else color = Color.RED;
        super.setTile(newTile, color);
    }

    @Override
    public void update(Layout layout) {
//        super.update(layout);

        if (!this.isAtDestination()) {
            this.move();
        } else {
            this.setDestinationPath(false);
            Tile destination = null;

            if (this.isCheckedIn) {
                destination = layout.getRandomTile(null, false, null); // this.room (Kamer)
                // add room to where randomly returns to
            } else {
                destination = layout.getRandomTile(null, true, Lobby.class);
                if (!(this.getTile().getFacility() instanceof Lobby)) {
                } else {
                    this.setWalkingCooldown(1000);
                    this.isCheckedIn = true;
                }
            }

            if (destination != null) this.bfs(destination);
        }
    }


}
