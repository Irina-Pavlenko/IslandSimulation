package model.organisms.predator;

import model.Island;
import model.organisms.Predator;
import model.organisms.herbivores.*;
import utils.Settings;

import java.util.Map;

public class Eagle extends Predator {
    public Eagle(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }

    @Override
    protected void initEatingChances() {
        this.eatingChances = Map.of(
                Cow.class, Settings.EatingChance.EAGLE_COW,
                Deer.class, Settings.EatingChance.EAGLE_DEER,
                Duck.class, Settings.EatingChance.EAGLE_DUCK,
                Goat.class, Settings.EatingChance.EAGLE_GOAT,
                Horse.class, Settings.EatingChance.EAGLE_HORSE,
                Monkey.class, Settings.EatingChance.EAGLE_MONKEY,
                Mouse.class, Settings.EatingChance.EAGLE_MOUSE,
                Pig.class, Settings.EatingChance.EAGLE_PIG,
                Rabbit.class, Settings.EatingChance.EAGLE_RABBIT,
                Sheep.class, Settings.EatingChance.EAGLE_SHEEP);
    }
    @Override
    public void reproduce() {
    }
}
