public class IceTile extends Tiles {
    private String lastDirection;

    public IceTile(String tileName) {
        super(tileName, true, 'I');
        this.sign = 'I';
        this.lastDirection = null;
    }

    @Override
    public boolean isIceTile() {
        return true;
    }

    @Override
    public String getLastDirection() {
        return lastDirection;
    }


    public void handlePlayerEntry(String moveDirection) {
        this.lastDirection = moveDirection;
    }

}