package game;

public class Player extends Sprite {
    private int health;
    public Player(String name, double x, double y, double width, double height, int health) {
        super(x, y, width, height);
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }
}
