package model.organisms.predator;

import model.Island;
import model.organisms.Predator;
import model.organisms.herbivores.*;
import utils.Settings;

import java.util.Map;

public class Fox extends Predator {
    public Fox(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }

    @Override
    protected void initEatingChances() {
        this.eatingChances = Map.of(
                Cow.class, Settings.EatingChance.FOX_COW,
                Deer.class, Settings.EatingChance.FOX_DEER,
                Duck.class, Settings.EatingChance.FOX_DUCK,
                Goat.class, Settings.EatingChance.FOX_GOAT,
                Horse.class, Settings.EatingChance.FOX_HORSE,
                Monkey.class, Settings.EatingChance.FOX_MONKEY,
                Mouse.class, Settings.EatingChance.FOX_MOUSE,
                Pig.class, Settings.EatingChance.FOX_PIG,
                Rabbit.class, Settings.EatingChance.FOX_RABBIT,
                Sheep.class, Settings.EatingChance.FOX_SHEEP);
    }
    @Override
    public void reproduce() {
    }
}
