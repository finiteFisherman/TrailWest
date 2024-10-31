package game;

public class Player {

    private int health;
    private String name;

    public Player(int health, String name) {
        this.health = health;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "health=" + health +
                ", name='" + name + '\'' +
                '}';
    }
}
