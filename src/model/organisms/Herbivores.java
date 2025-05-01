package model.organisms;

import model.Island;

import java.util.List;

public abstract class Herbivores extends Animal {//травоядные животные
    public Herbivores(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }
    public Herbivores() {
    }

    @Override
    protected void eat() {
        if (isSatiated() || location == null) return;//если животное наелось и не находится на локации - метод завершается
        List<Plant> plants = location.getPlants();//Возвращает все растения на текущей клетке острова.
        if (plants == null) return;//проверяем, что список растений существует (не null)
        for (Plant plant : plants){//Начинаем перебирать все растения на локации
            if (plant == null || plant.getWeight()<=0)continue;//Проверяем текущее растение:
            //оно существует (plant != null) и не съедено полностью (weight > 0)
            //Если что-то не так - пропускаем это растение (continue)

            double neededFood = foodRequired - satiety;//сколько еды еще нужно для насыщения
            //foodRequired - общее количество пищи, необходимое животному
            //satiety - текущий уровень сытости
            //neededFood - сколько еще нужно съесть до полного насыщения

            double eatenAmount = Math.min(plant.getWeight(),neededFood);//сколько сможем съесть из текущего растения
            //Берем минимальное значение между:
            //весом текущего растения (plant.getWeight())
            //количеством нужной пищи (neededFood)
            //Это гарантирует что мы не съедим больше, чем есть у растения
            //и не больше, чем нужно для насыщения

            satiety += eatenAmount;//Увеличиваем уровень сытости животного

            plant.setWeight(plant.getWeight()-eatenAmount);//Уменьшаем вес растения на съеденное количество

            if (plant.getWeight()<=0){
                location.getPlants().remove(plant);
                //Проверяем, полностью ли съели растение:
                // Если вес растения стал <= 0, удаляем его из локации
            }
            if (isSatiated())break;
            //Проверяем, наелось ли животное:
            // Если текущий уровень сытости (satiety) достиг или превысил
            //необходимый уровень (foodRequired), прерываем цикл (break)
        }
    }

    public abstract void reproduce();
}
