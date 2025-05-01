package model;

import statistics.Statistics;
import utils.Settings;

public class Island {
    private final Location[][] locations;
    private int currentTurn = 0;

    public Island() {
        this.locations = new Location[Settings.ISLAND_HEIGHT][Settings.ISLAND_WIDTH];
        initializeLocations();
        Settings.initializeIsland(this);
    }
    public void initializeLocations(){
        for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
            for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
                locations[y][x] = new Location(x,y);
            }
        }
    }
    public Location getLocation(int x, int y){
        if (x < 0 || x >= Settings.ISLAND_WIDTH||
        y < 0 || y >= Settings.ISLAND_HEIGHT){
            throw new IllegalArgumentException("Недопустимые координаты: [" + x + "][" + y +"]");
        }
        return locations[y][x];
    }
    /*
    public void processTurn(){
        currentTurn++;
        //обработка всех локаций.
        for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
            for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
                locations[y][x].processTurn();
            }
        }
        // Вывод статистики
        Statistics.printFullStatistics(this,currentTurn);
    }
     */
    public int getHeight(){
        return Settings.ISLAND_HEIGHT;
    }
    public int getWidth(){
        return Settings.ISLAND_WIDTH;
    }
    public int getCurrentTurn(){
        return currentTurn;
    }
    public void incrementTurn(){
        currentTurn++;
        //Увеличивает счетчик текущего такта симуляции
        //Вызывается автоматически после обработки каждого дня
    }
}
