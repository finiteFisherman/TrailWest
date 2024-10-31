package game;

import java.util.Arrays;

public class Store {

    private String meat[];
    private String fruit[];
    private String veggies[];
    private String tools[];
    private String clothes[];
    private String furniture[];
    private String misc[];

    public Store(String[] meat, String[] fruit, String[] veggies, String[] tools, String[] clothes, String[] furniture, String[] misc) {
        this.meat = meat;
        this.fruit = fruit;
        this.veggies = veggies;
        this.tools = tools;
        this.clothes = clothes;
        this.furniture = furniture;
        this.misc = misc;
    }

    public String[] getMeat() {
        return meat;
    }

    public void setMeat(String[] meat) {
        this.meat = meat;
    }

    public String[] getFruit() {
        return fruit;
    }

    public void setFruit(String[] fruit) {
        this.fruit = fruit;
    }

    public String[] getVeggies() {
        return veggies;
    }

    public void setVeggies(String[] veggies) {
        this.veggies = veggies;
    }

    public String[] getTools() {
        return tools;
    }

    public void setTools(String[] tools) {
        this.tools = tools;
    }

    public String[] getClothes() {
        return clothes;
    }

    public void setClothes(String[] clothes) {
        this.clothes = clothes;
    }

    public String[] getFurniture() {
        return furniture;
    }

    public void setFurniture(String[] furniture) {
        this.furniture = furniture;
    }

    public String[] getMisc() {
        return misc;
    }

    public void setMisc(String[] misc) {
        this.misc = misc;
    }

    @Override
    public String toString() {
        return "Store{" +
                "meat=" + Arrays.toString(meat) +
                ", fruit=" + Arrays.toString(fruit) +
                ", veggies=" + Arrays.toString(veggies) +
                ", tools=" + Arrays.toString(tools) +
                ", clothes=" + Arrays.toString(clothes) +
                ", furniture=" + Arrays.toString(furniture) +
                ", misc=" + Arrays.toString(misc) +
                '}';
    }
}
