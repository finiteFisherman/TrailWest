package game;

public class Wagon {

    private int health;
    private double weight;
    private int speed;

    public Wagon(int health, double weight, int speed) {
        this.health = health;
        this.weight = weight;
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wagon{" +
                "health=" + health +
                ", weight=" + weight +
                ", speed=" + speed +
                '}';
    }
}
