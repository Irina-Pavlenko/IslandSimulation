package utils;

import model.Island;
import model.organisms.Animal;
import model.organisms.herbivores.*;
import model.organisms.predator.*;

import model.Location;

public final class Settings {
    private Settings() {//приватный конструктор для запрета создания экземпляпов класса
    }
    //все поля и методы статические
    //Размер острова
    public static int ISLAND_HEIGHT = 5;
    public static int ISLAND_WIDTH = 5;
    //продолжительность одного дня в милисекундах
    //1000мс = 1 минута = 1 день
    public static final int DAY_DURATION_MS = 1000;
    //интервал в днях между выводами статистики в консоль
    public static final int STATISTIC_PRINT_INTERVAL_DAYS = 5;
    //общая продолжительность симуляции в минутах.
    //через указанное время симуляция автоматически остановится
    public static final int SIMULATION_DURATION_MINUTES = 10;
    //начальный уровень сытости животных
    //задаётся как доля от foodNeeded (0.7 = 70% от максимальной сытости)
    public static final double INITIAL_SATIETY_PERCENT = 0.7;
    //дневная потеря сытости
    public static final double DAILY_SATIETY_LOSS = 0.3;
    //---Лимиты на локацию--
    //максимальное кол-во растений на одной клетке
    public static final int MAX_PLANTS_PER_LOCATION = 200;
    //кол-во растений, которое вырастает ежедневно на одной клетке
    //если места нет рост прекращается
    public static final int PLANT_GROWTH_PER_DAY = 10;
    //Параметры растений
    public static final double PLANT_DEFAULT_WEIGHT = 0.01;
    public static final double PLANT_MAX_WEIGHT = 1.0;
    public static final double PLANT_GROWTH_RATE = 0.15;
    //---МАКСИМАЛЬНОЕ ОБЩЕЕ КОЛИЧЕСТВО ЖИВОТНЫХ В ОДНОЙ КЛЕТКЕ----
    public static final int MAX_ANIMALS_PER_LOCATION = 500;

    //---ENUM- содержит параметры для каждого вида животного
    public enum AnimalConfig {
        //Формат конструктора:
        //Имя вида(вес, максНаКлетке, скорость, потребностьВЕде, шансРазмножения, фабрика)
        //---ХИЩНИКИ---
        BEAR(500, 5, 2, 80, 0.8),
        EAGLE(6, 20, 3, 1, 0.7),
        FOX(8, 30, 2, 2, 0.7),
        SNAKE(15, 30, 1, 3, 0.4),
        WOLF(50, 30, 3, 8, 0.8),
        //---ТРАВОЯДНЫЕ---
        COW(700, 10, 3, 100, 0.6),
        DEER(300, 20, 4, 50, 0.6),
        DUCK(1, 200, 4, 0.15, 0.7),
        GOAT(60, 140, 3, 10, 0.7),
        HORSE(400, 20, 4, 60, 0.8),
        MONKEY(20, 40, 3, 10, 0.4),
        MOUSE(0.05, 500, 1, 0.01, 0.8),
        PIG(400, 50, 2, 50, 0.7),
        RABBIT(2, 150, 2, 0.45, 0.9),
        SHEEP(70, 140, 3, 15, 0.7);
        //---ПАРАМЕТРЫ ЖИВОЬНЫХ---
        // -- вес одной особи данного вида в кг --
        // - используется при расчетах питания -
        public final double weight;

        //--максимальное кол-во особей этого вида на одной клетке
        //-- при достижении лимита новые особи не могут появиться на коетке
        public final int maxPerCell;

        //--максимальная скорость перемещения (клеток за ход)
        public final int maxSpeed;

        //--кол-во пищи, необходимое для полного насыщения (кг) в день
        public final double foodRequired;

        //--вероятность размножения (от 0.0 до 1.0)
        public final double reproductionChance;


        //----КОНСТРУКТОР ДЛЯ СОЗДАНИЯ enum----
        AnimalConfig(double weight, int maxPerCell, int maxSpeed, double foodRequired, double reproductionChance) {
            this.weight = weight;
            this.maxPerCell = maxPerCell;
            this.maxSpeed = maxSpeed;
            this.foodRequired = foodRequired;
            this.reproductionChance = reproductionChance;
        }

        // Фабричный метод для создания животных
        public Animal createAnimal(Island island){
            switch (this){
                case BEAR: return new Bear(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case EAGLE: return new Eagle(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case FOX: return new Fox(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case SNAKE: return new Snake(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case WOLF: return new Wolf(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case COW: return new Cow(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case DEER: return new Deer(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case DUCK: return new Duck(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case GOAT: return new Goat(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case HORSE: return new Horse(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case MONKEY: return new Monkey(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case MOUSE: return new Mouse(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case PIG: return new Pig(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case RABBIT: return new Rabbit(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                case SHEEP: return new Sheep(island,weight,maxPerCell,maxSpeed,foodRequired,reproductionChance);
                default:throw new IllegalArgumentException("Неизвестный тип животного");
            }
        }
        // Получение начального количества животных для вида
        public int getInitialCount() {
            return switch (this) {
                case BEAR -> InitialCount.BEAR;
                case EAGLE -> InitialCount.EAGLE;
                case FOX -> InitialCount.FOX;
                case SNAKE -> InitialCount.SNAKE;
                case WOLF -> InitialCount.WOLF;
                case COW -> InitialCount.COW;
                case DEER -> InitialCount.DEER;
                case DUCK -> InitialCount.DUCK;
                case GOAT -> InitialCount.GOAT;
                case HORSE -> InitialCount.HORSE;
                case MONKEY -> InitialCount.MONKEY;
                case MOUSE -> InitialCount.MOUSE;
                case PIG -> InitialCount.PIG;
                case RABBIT -> InitialCount.RABBIT;
                case SHEEP -> InitialCount.SHEEP;
            };
        }
    }
    //Класс, содержащий вероятности успешной охоты/поедания (%)
    public static final class EatingChance{
        //-----МЕДВЕДЬ-----
        public static final int BEAR_COW = 20;
        public static final int BEAR_DEER = 80;
        public static final int BEAR_DUCK = 10;
        public static final int BEAR_GOAT = 70;
        public static final int BEAR_HORSE = 60;
        public static final int BEAR_MONKEY = 20;
        public static final int BEAR_MOUSE = 90;
        public static final int BEAR_PIG = 50;
        public static final int BEAR_RABBIT = 80;
        public static final int BEAR_SHEEP = 70;
        //----ОРЕЛ-----
        public static final int EAGLE_COW = 0;
        public static final int EAGLE_DEER = 0;
        public static final int EAGLE_DUCK = 80;
        public static final int EAGLE_GOAT = 0;
        public static final int EAGLE_HORSE = 0;
        public static final int EAGLE_MONKEY = 20;
        public static final int EAGLE_MOUSE = 90;
        public static final int EAGLE_PIG = 0;
        public static final int EAGLE_RABBIT = 90;
        public static final int EAGLE_SHEEP = 0;
        //----ЛИСА------
        public static final int FOX_COW = 0;
        public static final int FOX_DEER = 0;
        public static final int FOX_DUCK = 60;
        public static final int FOX_GOAT = 0;
        public static final int FOX_HORSE = 0;
        public static final int FOX_MONKEY = 10;
        public static final int FOX_MOUSE = 90;
        public static final int FOX_PIG = 0;
        public static final int FOX_RABBIT = 70;
        public static final int FOX_SHEEP = 0;
        //----ЗМЕЯ----
        public static final int SNAKE_COW = 0;
        public static final int SNAKE_DEER = 0;
        public static final int SNAKE_DUCK = 60;
        public static final int SNAKE_GOAT = 0;
        public static final int SNAKE_HORSE = 0;
        public static final int SNAKE_MONKEY = 20;
        public static final int SNAKE_MOUSE = 90;
        public static final int SNAKE_PIG = 0;
        public static final int SNAKE_RABBIT = 70;
        public static final int SNAKE_SHEEP = 0;
        //-----ВОЛК-----
        public static final int WOLF_COW = 10;
        public static final int WOLF_DEER = 40;
        public static final int WOLF_DUCK = 40;
        public static final int WOLF_GOAT = 60;
        public static final int WOLF_HORSE = 30;
        public static final int WOLF_MONKEY = 10;
        public static final int WOLF_MOUSE = 80;
        public static final int WOLF_PIG = 15;
        public static final int WOLF_RABBIT = 60;
        public static final int WOLF_SHEEP = 70;
    }
    //---НАЧАЛЬНОЕ КОЛ-ВО ЖИВОТНЫХ НА ОСТРОВЕ-----
    public static final class InitialCount{
        // --- ХИЩНИКИ---
        public static final int BEAR = 5;
        public static final int EAGLE = 20;
        public static final int FOX = 30;
        public static final int SNAKE = 30;
        public static final int WOLF = 30;
        //---ТРАВОЯДНЫЕ---
        public static final int COW = 10;
        public static final int DEER = 20;
        public static final int DUCK = 200;
        public static final int GOAT = 140;
        public static final int HORSE = 20;
        public static final int MONKEY = 40;
        public static final int MOUSE = 500;
        public static final int PIG = 50;
        public static final int RABBIT = 150;
        public static final int SHEEP = 140;
    }
    // первоначальное заселение острова животными всех видов в соответствии с заданными настройками
    public static void initializeIsland(Island island){
        for (AnimalConfig config:AnimalConfig.values()){
            for (int i = 0; i < config.getInitialCount(); i++) {
                Location randomLocation = getRandomLocation(island);
                randomLocation.addAnimal(config.createAnimal(island));
            }
        }
    }
    private static Location getRandomLocation(Island island){
        int x = Randomizer.getRandomInt(ISLAND_WIDTH);
        int y = Randomizer.getRandomInt(ISLAND_HEIGHT);
        return island.getLocation(x,y);
    }
}
