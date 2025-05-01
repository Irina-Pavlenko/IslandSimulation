package model;

import model.organisms.Animal;
import model.organisms.Plant;
import utils.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {//клетка
    int x;//координаты клетки
    int y;
    private final Map<Class<? extends Animal>, List<Animal>> animals = new ConcurrentHashMap<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();
    private final int maxPlants = 200;//максимум растений на одной клетке

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void addPlant(Plant plant){
        if (plants.size()< Settings.MAX_PLANTS_PER_LOCATION){
            plant.setLocation(this);
            plants.add(plant);
        }
    }
    //Возвращает КОПИЮ списка растений на локации
    // new ArrayList<>() делает изменяемую копию,
    //чтобы можно было безопасно удалять растения в методе eat()
    public List<Plant> getPlants(){
        return new ArrayList<>(plants);// Создаем новый изменяемый список
    }
    public void growPlants(){// рост растений
        plants.forEach(Plant::grow);//Рост всех растений
        int plantsToAdd = Math.min(Settings.PLANT_GROWTH_PER_DAY, Settings.MAX_PLANTS_PER_LOCATION - plants.size());
        for (int i = 0; i < plantsToAdd; i++) {
            addPlant(new Plant());
        }
        removeEatenPlants();//Удаляем все растения, вес которых <= 0 (полностью съеденные)
    }
    public synchronized int getPlantsCount() {
        return plants.size();//Возвращает текущее количество растений в локации
    }
    public void removeEatenPlants(){//Метод удаляет из списка plants все растения, которые были съедены (их вес ≤ 0)
        plants.removeIf(plant -> plant.getWeight()<=0);
    }
    public int getMaxPlants(){
        return maxPlants;
    }
    //ЖИВОТНЫЕ
    public synchronized void addAnimal(Animal animal){//синхронизировать добавление животных
        Class<? extends Animal> type = animal.getClass();//Получает класс животного (например, Wolf.class, Rabbit.class)
        animals.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>());//Инициализация списка для типа:
        // Проверяет, есть ли уже список для данного типа животных,
        // Если нет - выполняет функцию k -> new CopyOnWriteArrayList<>() и Помещает новый CopyOnWriteArrayList в мапу по ключу type,
        // Если есть - возвращает существующий список
        if (animals.get(type).size() < Settings.MAX_ANIMALS_PER_LOCATION){
            animal.setLocation(this);
            animals.get(type).add(animal);
        }
    }
    public Map<Class<? extends Animal>, Integer> getAnimalCountsByType() {
        Map<Class<? extends Animal>, Integer> counts = new HashMap<>();
        animals.forEach((type, list) -> counts.put(type, list.size()));
        return counts;
    }

    public int getTotalAnimalsCount(){
        return animals.values().stream().mapToInt(List::size).sum();//соседние клетки
    }
    // === Логика такта ===
    public void processTurn() {//обработка одного такта
        plants.forEach(Plant::grow);//все растения растут
        animals.values().forEach(list -> list.forEach(Animal::live));//все животные живут
        cleanup();
    }
    public void cleanup() {//очистка коллекции от умерших растений и животных
        plants.removeIf(plant -> plant.getWeight() <= 0);
        animals.values().forEach(list -> list.removeIf(animal -> !animal.isAlive()));
    }
    // === Координаты ===
    public int getX() { return x; }
    public int getY() { return y; }

    public void removeAnimal(Animal animal) {
    }

    public Map<Class<?extends Animal>,List<Animal>> getAnimals() {
        return animals;
    }
}
