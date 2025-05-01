package statistics;

import model.Island;
import model.Location;
import model.organisms.Animal;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    public static void printFullStatistics(Island island, int turn){
        System.out.println("\n=== ТАКТ " + turn + " ===");

        //Сбор данных
        Map<Class<? extends Animal>,Integer> animalCounts = new HashMap<>();//подсчет количества животных по типам
        int totalPlants = 0;//переменная для хранения общего количества растений
        int totalAnimals = 0;//переменная для хранения общего количества животных

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x,y);
                totalPlants += loc.getPlantsCount();//общее кол-во растений обновляется
                totalAnimals += loc.getTotalAnimalsCount();//общее кол-во животных обновляется

                // Собираем статистику по типам (получение кол-ва жив. по типам)
                loc.getAnimalCountsByType().forEach((type,count)->
                        animalCounts.merge(type,count,Integer::sum));//суммируется кол-во ж. каждого типа
            }
        }

        // Вывод общей статистики
        System.out.printf("Общее количество:\n- Растений: %d\n- Животных: %d\n", totalPlants,totalAnimals);

        // Вывод статистики по типам животных
        System.out.println("\nРаспределение животных:");
        animalCounts.entrySet().stream().sorted(Map.Entry.<Class<? extends Animal>,Integer>comparingByValue().
                reversed()).forEach(entry-> System.out.printf("- %-15s: %3d особей\n",
                entry.getKey().getSimpleName(),entry.getValue()));
    }
}
