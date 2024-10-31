package game;

import java.util.Arrays;

public class Inventory {

    private int meat;
    private int fruit;
    private int veggies;
    private int tools;
    private int clothes;
    private double cash;

    public Inventory(int meat, int fruit, int veggies, int tools, int clothes,double cash) {
        this.meat = meat;
        this.fruit = fruit;
        this.veggies = veggies;
        this.tools = tools;
        this.clothes = clothes;
        this.cash=cash;
    }

    public int getMeat() {

        return meat;
    }

    public void setMeat(int meat) {

        this.meat = meat;
    }

    public int getFruit() {
        return fruit;
    }

    public void setFruit(int fruit) {
        this.fruit = fruit;
    }

    public int getVeggies() {
        return veggies;
    }

    public void setVeggies(int veggies) {
        this.veggies = veggies;
    }

    public int getTools() {
        return tools;
    }

    public void setTools(int tools) {
        this.tools = tools;
    }

    public int getClothes() {
        return clothes;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    public double getCash(){
        return cash;
    }

    public void setCash(double cash){
        this.cash=cash;
    }

    @Override
    public String toString() {
        return "Inventory: " +
                "Pounds of meat=" + meat +
                ", Pounds of fruit=" + fruit +
                ", Pounds of vegetables=" + veggies +
                ", Sets of tools=" + tools +
                ", Sets of clothes=" + clothes +
                ", Available cash= $" + cash ;
    }
}
