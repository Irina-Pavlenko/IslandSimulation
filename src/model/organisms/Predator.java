package model.organisms;

import model.Island;
import utils.Randomizer;

import java.util.List;
import java.util.Map;

public abstract class Predator extends Animal { //хищные животные
    protected Map<Class<? extends Animal>,Integer> eatingChances;
    public Predator(Island island, double weight, int maxPerLocation, int speed, double foodNeeded, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodNeeded, reproductionChance);
        initEatingChances();
    }
    protected abstract void initEatingChances();

    @Override
    protected void eat() {
        if (isSatiated() || location == null)return;//Проверка сытости
        //Поиск добычи на локации
        for (Map.Entry<Class<? extends Animal>, Integer> entry : eatingChances.entrySet()) {//Перебор возможной добычи

            //Animal prey: Переменная, представляющая добычу, которую животное может попытаться съесть.
            //getOrDefault(entry.getKey(), List.of()): пытается получить список животных,
            // соответствующих классу, указанному в entry.getKey().
            // Если таких животных нет, возвращается пустой список (List.of()), чтобы избежать NullPointerException.
            //location.getAnimals():возвращает коллекцию животных, находящихся в данной локации.
            for (Animal prey : location.getAnimals().getOrDefault(entry.getKey(), List.of())) {
                
                //пытается съесть указанную добычу (prey).
                // Второй аргумент — это шансы на поедание, полученные из entry.getValue().
                // Метод возвращает true, если поедание прошло успешно, и false в противном случае.
                if (tryEat(prey, entry.getValue())) {//проверяет, удалось ли животному съесть добычу
                    return; // Прекращаем после успешного питания
                }
            }
        }
    }

    protected boolean tryEat(Animal prey, int chance){//Механизм питания
        if (prey == null || !prey.isAlive() || prey.getWeight() <= 0) return false;

        try {
            if (Randomizer.getProbability(chance)) {
                double eatenAmount = Math.min(prey.getWeight(), foodRequired - satiety);
                satiety += eatenAmount;
                prey.setWeight(prey.getWeight() - eatenAmount);

                if (prey.getWeight() <= 0) prey.die();
                return true;
            }
        } catch (IllegalAccessException e) {
            System.err.println("Ошибка вероятности: " + e.getMessage());
        }
        return false;
    }
    public abstract void reproduce();
}
