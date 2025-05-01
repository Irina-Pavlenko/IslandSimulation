package model.organisms.predator;

import model.Island;
import model.organisms.Predator;
import model.organisms.herbivores.*;
import utils.Settings;
import java.util.Map;

public class Wolf extends Predator {
    public Wolf(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }

    @Override
    protected void initEatingChances() {//Заполняет мапу животными, которые могут быть добычей
        this.eatingChances = Map.of(
                Cow.class, Settings.EatingChance.WOLF_COW,//значение - вероятность в % что волк съест корову при встрече
                Deer.class, Settings.EatingChance.WOLF_DEER,//ключ - Тип животного-жертвы
                Duck.class, Settings.EatingChance.WOLF_DUCK,//Задаёт вероятность успешной атаки для каждого типа добычи
                Goat.class,Settings.EatingChance.WOLF_GOAT,
                Horse.class, Settings.EatingChance.WOLF_HORSE,
                Monkey.class, Settings.EatingChance.WOLF_MONKEY,
                Mouse.class, Settings.EatingChance.WOLF_MOUSE,
                Pig.class, Settings.EatingChance.WOLF_PIG,
                Rabbit.class, Settings.EatingChance.WOLF_RABBIT,
                Sheep.class, Settings.EatingChance.WOLF_SHEEP);
    }

    @Override
    public void reproduce() {

    }
}
