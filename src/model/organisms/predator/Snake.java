package model.organisms.predator;

import model.Island;
import model.organisms.Predator;
import model.organisms.herbivores.*;
import utils.Settings;

import java.util.Map;

public class Snake extends Predator {
    public Snake(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }

    @Override
    protected void initEatingChances() {
        this.eatingChances = Map.of(
                Cow.class, Settings.EatingChance.SNAKE_COW,
                Deer.class, Settings.EatingChance.SNAKE_DEER,
                Duck.class, Settings.EatingChance.SNAKE_DUCK,
                Goat.class, Settings.EatingChance.SNAKE_GOAT,
                Horse.class, Settings.EatingChance.SNAKE_HORSE,
                Monkey.class, Settings.EatingChance.SNAKE_MONKEY,
                Mouse.class, Settings.EatingChance.SNAKE_MOUSE,
                Pig.class, Settings.EatingChance.SNAKE_PIG,
                Rabbit.class, Settings.EatingChance.SNAKE_RABBIT,
                Sheep.class, Settings.EatingChance.SNAKE_SHEEP);
    }
    @Override
    public void reproduce() {
    }
}
