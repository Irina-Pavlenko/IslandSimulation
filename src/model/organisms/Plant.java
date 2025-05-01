package model.organisms;

import model.Location;
import model.validation.PlantValidator;
import utils.Settings;

public  class Plant {
    private double weight;//масса растения
    private final double maxWeight;//макс.масса
    private final double growthRate;//скорость роста за 1 такт
    private Location location; //ссылка на локацию, где находится растение
   //параметры по умолчанию
    //private static final double DEFAULT_INITIAL_WEIGHT = 0.01;
    //private static final double DEFAULT_MAX_WEIGHT = 1.0;
   // private static final double DEFAULT_GROWTH_RATE = 0.15;

    public Plant(){
        this(Settings.PLANT_DEFAULT_WEIGHT,
                Settings.PLANT_MAX_WEIGHT,
                Settings.PLANT_GROWTH_RATE);
    }
    public Plant(double initialWeight, double maxWeight, double growthRate) {
        PlantValidator.validateGrowthParameters(initialWeight, maxWeight, growthRate);
        this.weight = initialWeight;
        this.maxWeight = maxWeight;
        this.growthRate = growthRate;
    }
    //Основная логика
    public void grow(){
        if (weight < maxWeight){//проверка условий роста(если растение не достигло макс.роста выполняется блок кода)
          //логика роста
          weight = Math.min(weight + growthRate,maxWeight);//увеличивает вес на значение скорости роста, но не превышает максимальный вес
        }else {
            tryReproduce();
        }
    }
    private void tryReproduce(){
        if (location != null && PlantValidator.canReproduce(this,location)){//проверяет наличие растения в локации,
            // и валидация условий размножения
            location.addPlant(createOffspring());//вызывает метод для создания нового растения
        }
    }
    protected Plant createOffspring(){
        return new Plant(this.weight/2, this.maxWeight, this.growthRate);//Создает новый объект растения
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    /*public Location getLocation() throws IllegalStateException{//метод не используется. можно удалить
        return location;
    }*/
    public double getWeight(){
        return weight;
    }
    /*public double getMaxWeight() {//метод не используется можно удалить
        return this.maxWeight;
    }*/

    public void setWeight(double weight) {
        if (weight < 0){
            throw new IllegalArgumentException ("Вес растения не может быть отрицательным");
        }
        this.weight = weight;
    }
}
