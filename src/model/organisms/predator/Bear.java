package model.organisms.predator;

import model.Island;
import model.organisms.Predator;
import model.organisms.herbivores.*;
import utils.Settings;

import java.util.Map;

public class Bear extends Predator {
    public Bear(Island island, double weight, int maxPerLocation, int speed, double foodNeeded, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodNeeded, reproductionChance);
    }

    @Override
    protected void initEatingChances() {
        this.eatingChances = Map.of(
                Cow.class, Settings.EatingChance.BEAR_COW,
                Deer.class, Settings.EatingChance.BEAR_DEER,
                Duck.class, Settings.EatingChance.BEAR_DUCK,
                Goat.class, Settings.EatingChance.BEAR_GOAT,
                Horse.class, Settings.EatingChance.BEAR_HORSE,
                Monkey.class, Settings.EatingChance.BEAR_MONKEY,
                Mouse.class, Settings.EatingChance.BEAR_MOUSE,
                Pig.class, Settings.EatingChance.BEAR_PIG,
                Rabbit.class, Settings.EatingChance.BEAR_RABBIT,
                Sheep.class, Settings.EatingChance.BEAR_SHEEP);
    }
    @Override
    public void reproduce() {
    }
}
