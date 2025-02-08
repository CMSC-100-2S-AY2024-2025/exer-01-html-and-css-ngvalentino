package game;

public class Sprite {
    private double x, y;
    private double dx, dy;
    private double width, height;

    public Sprite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public boolean collidesWith(Sprite other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }

    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public void setVelocity(double dx, double dy) { this.dx = dx; this.dy = dy; }
}
