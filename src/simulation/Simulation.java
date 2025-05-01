package simulation;

import model.Island;
import model.Location;
import statistics.Statistics;
import utils.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {//запуск задач в пуле потоков
    private final Island island;
    private final ScheduledExecutorService mainExecutor;
    private final ExecutorService locationExecutor;
    private volatile boolean running;

    public Simulation() {
        this.island = new Island();// Создаем остров внутри симуляции
        Settings.initializeIsland(island); // Инициализируем животными и растениями
        this.mainExecutor = Executors.newScheduledThreadPool(3);
        this.locationExecutor = Executors.newFixedThreadPool(8);
        this.running = false;
    }
    public void start(){
        if (running) return;
        running = true;

        // Задача для роста растений (каждый день)
        mainExecutor.scheduleAtFixedRate(this::growPlants,0, Settings.DAY_DURATION_MS, TimeUnit.MILLISECONDS);

        //Задача для жизненного цикла животных (каждый день)
        mainExecutor.scheduleAtFixedRate(this::processAnimals,0,Settings.DAY_DURATION_MS,TimeUnit.MILLISECONDS);

        // Задача для вывода статистики (каждые STATISTIC_PRINT_INTERVAL_DAYS дней)
        mainExecutor.scheduleAtFixedRate(this::printStatistics,0,
                Settings.DAY_DURATION_MS * Settings.STATISTIC_PRINT_INTERVAL_DAYS,TimeUnit.MILLISECONDS);

        // Остановка симуляции через заданное время
        mainExecutor.schedule(this::stop,Settings.SIMULATION_DURATION_MINUTES,TimeUnit.MINUTES);
    }
    public void stop(){
        if (!running)return;
        running = false;

        mainExecutor.shutdown();
        locationExecutor.shutdown();
        try {
            if (!mainExecutor.awaitTermination(1,TimeUnit.SECONDS)){
                mainExecutor.shutdown();
            }
            if (!locationExecutor.awaitTermination(1,TimeUnit.SECONDS)){
                locationExecutor.shutdown();
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
//Метод growPlants проходит по каждой локации на острове
// и вызывает метод growPlants для каждой из них в отдельном потоке.
// Это позволяет растению расти параллельно
    private void growPlants() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x,y);
                locationExecutor.submit(location::growPlants);
            }
        }
    }
    private void processAnimals(){
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x,y);
                locationExecutor.submit(() -> {
                    location.processTurn();
                    // Очистка умерших животных и съеденных растений
                    location.cleanup();
                });
            }
        }
    }
    private void printStatistics(){
        // Выводим полную статистику по острову:
        // - Передаем текущий объект острова (island)
        // - И номер текущего такта (получаем через island.getCurrentTurn())
        Statistics.printFullStatistics(island,island.getCurrentTurn());// выводим статистику для ТЕКУЩЕГО такта

        // Увеличиваем счетчик тактов на острове
        // (Вызываем метод incrementTurn() у объекта island)
        island.incrementTurn();
    }

    public boolean isRunning(){//метод для проверки статуса
        return running;
    }
    //ScheduledExecutorService(3) //рост растения, жизненный цикл животного(накормить..), вывод статистики

    //FixedThreadPool(8)//в каждой клетке животное может перемещаться, размножаться, кушать(обработка каждой клетки)
}
