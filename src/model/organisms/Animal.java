package model.organisms;

import model.Island;
import model.Location;
import utils.Randomizer;
import utils.Settings;

import java.util.List;

public abstract class Animal {
    protected double weight;//вес животного
    protected int maxPerLocation; //максимальное количество животных на одной клетке
    protected int speed;//скорость перемещения животного по клеткам
    protected double foodRequired;//количество пищи для насыщения
    protected double satiety;
    boolean isAlive;//флаг - живо ли животное
    protected Location location;
    protected Island island;
    protected double reproductionChance; // вероятность размножения

    public Animal(Island island,double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        this.island = island;
        this.weight = weight;
        this.maxPerLocation = maxPerLocation;
        this.speed = speed;
        this.foodRequired = foodRequired;
        this.reproductionChance = reproductionChance;
        this.satiety = foodRequired* Settings.INITIAL_SATIETY_PERCENT;
        this.isAlive = true;
    }

    public Animal() {

    }
    protected abstract void eat();

    protected void move(){
        if (!isAlive || speed==0) return;
        int newX = location.getX()+ Randomizer.getRandomInt(speed*2+1)-speed;
        int newY = location.getY()+Randomizer.getRandomInt(speed*2+1)-speed;
        try {
            Location newLocation = island.getLocation(newX, newY);
            if (newLocation != location){
                location.removeAnimal(this);
                newLocation.addAnimal(this);
                this.location = newLocation;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }// Остаемся на текущей локации, если новые координаты недопустимы
    }
    protected void reproduce() {
        if (!isAlive || Randomizer.getRandomDouble(1.0)>getReproductionChance()){
            return;//если шанс репрод.<1 размнож.не происходит
        }
        List<Animal> sameTypeAnimals = location.getAnimals().get(this.getClass());//лист жив.одного типа
        if (sameTypeAnimals !=null && sameTypeAnimals.size() < maxPerLocation){
            Animal offspring = createOffspring();//создаётся жив.родит-го типа
            location.addAnimal(offspring);//животное добавляется в локацию
        }
    }
    protected double getReproductionChance(){
        return Settings.AnimalConfig.valueOf(getClass().getSimpleName().toUpperCase()).reproductionChance;//шанс на размножение у опр.жив.
    }
    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location; 
    }

    public void live() {
        if (!isAlive){
            return;
        }
        eat();
        move();
        reproduce();
        loseSatiety();
        if (satiety<=0){
            die();
        }
    }
    //Метод реализует механизм размножения, создавая новое животное того же вида, что и родитель
    public Animal createOffspring(){
        return Settings.AnimalConfig.valueOf(getClass().getSimpleName().toUpperCase()).createAnimal(this.island);
    }

    protected void loseSatiety(){// отвечает за уменьшение уровня сытости животного с течением времени
        satiety-=foodRequired*Settings.DAILY_SATIETY_LOSS;
        if (satiety<0){
            satiety=0;
        }
    }
    public void die(){
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
    protected boolean isSatiated(){
        return  satiety >= foodRequired;//сытость>=необходимой пищи
    }

    public double getWeight() {
        return this.weight;
    }
    public void setWeight(double weight){
        this.weight = Math.max(0,weight);
    }
    public void setIsland(Island island){
        this.island = island;
    }
}
