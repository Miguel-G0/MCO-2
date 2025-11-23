public class ForceFloor extends Tiles {
    private String direction;

    public ForceFloor(String tileName, String direction) {
        super(tileName, true,' ');
        this.direction = direction;
		switch(direction){
			case "left":
			sign = '<';
			break;
			case "right":
			sign = '>';
			break;
			case "down":
			sign = 'V';
			break;
			case "up":
			sign = '^';
			break;
			
        }
    }

    @Override
    public boolean isForceFloor() {
        return true;
    }

    @Override
    public String getForceDirection() {
        return direction;
    }
}