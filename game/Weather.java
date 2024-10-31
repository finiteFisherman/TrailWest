package game;

public class Weather {

    private int tempF;

    public Weather(int tempF) {
        this.tempF = tempF;
    }

    public void setTempF(int tempF) {
        this.tempF = tempF;
    }

    public int getTempF() {
        return tempF;
    }

    @Override
    public String toString() {
        return "game.Weather: "+tempF;
    }
}
