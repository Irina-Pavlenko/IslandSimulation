import simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК СИМУЛЯЦИИ ОСТРОВА ===");

        Simulation simulation = new Simulation();//Создаем и настраиваем симуляцию
        try {
            simulation.start();
            awaitSimulationEnd(simulation);//Ожидаем завершения по таймауту
        }catch (Exception e){
            System.err.println("Ошибка: " + e.getMessage());
            simulation.stop();
        }
    }
    public static void awaitSimulationEnd(Simulation sim){
        while (sim.isRunning()){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                sim.stop();
            }
        }
        System.out.println("\n=== Симуляция завершена ===");
    }
}
