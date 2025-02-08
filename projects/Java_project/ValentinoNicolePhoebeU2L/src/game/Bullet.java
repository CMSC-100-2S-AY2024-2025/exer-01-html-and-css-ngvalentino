package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
    private ImageView bulletImageView;
    private double speed = 10.0;
    private double direction;

    public Bullet(double x, double y, double speed, String imagePath, double direction) {
        this.bulletImageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        this.bulletImageView.setX(x);
        this.bulletImageView.setY(y);
        this.speed = speed;
        this.direction = direction;
        bulletImageView.setFitWidth(40);
        bulletImageView.setFitHeight(50);
    }

    public void move() {
    	// Move the bullet horizontally based on direction of the player (left or right)
        this.bulletImageView.setX(this.bulletImageView.getX() + (speed * direction));
    }

    public ImageView getBulletImageView() {
        return bulletImageView;
    }
}
