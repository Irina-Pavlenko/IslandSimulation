package model.validation;

import model.Location;
import model.organisms.Plant;
import utils.Settings;

public class PlantValidator {
    private static final double MIN_WEIGHT = 0.001;//минимальный вес растения 1 грамм
    //Проверка параметров при создании растения
    public static void validateGrowthParameters(double initialWeight, double maxWeight, double growthRate){
        if (initialWeight < MIN_WEIGHT){
            throw new IllegalArgumentException("Начальный вес растения не может быть меньше " + MIN_WEIGHT + " кг.");
        }
        if (maxWeight <= initialWeight){
            throw new IllegalArgumentException("Максимальный вес должен превышать начальный");
        }
        if (growthRate <= 0){
            throw new IllegalArgumentException("Скорость роста должна быть положительной");
        }
    }
    //Проверка возможности размножения
    public static boolean canReproduce(Plant plant, Location location){
        return plant.getWeight() >= Settings.PLANT_MAX_WEIGHT *0.9 &&
        location.getPlants().size()<Settings.MAX_PLANTS_PER_LOCATION;//Проверка зрелости растения, Проверка свободного места
    }
    public static void validateEdible(Plant plant, double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Количество съедаемой биомассы должно быть положительным");
        }
        if (amount > plant.getWeight()){
            throw new IllegalArgumentException("Нельзя съесть больше, чем весит растение");
        }
    }
}
