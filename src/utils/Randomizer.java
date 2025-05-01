package utils;

import java.util.Random;

public final class Randomizer {
    private static final Random random = new Random();

    private Randomizer() {
    }
    public static boolean getProbability(int percent) throws IllegalAccessException {//вероятность
        validPercent(percent);
        return random.nextInt(100) < percent;

    }
    public static void validPercent(int percent) throws IllegalAccessException {
        if (percent < 0 || percent > 100){//проверка вероятности
            throw new IllegalAccessException("Вероятность должна быть от 0 до 100");
        }
    }
    public static int getRandomInt(int bound){
        return random.nextInt(bound);
    }
    public static void validRandomInt(int bound){//число для передвижения ж. по клеткам
        if (bound < 0){
            throw new IllegalArgumentException("Число должно быть положительным");
        }
    }
    public static double getRandomDouble(double volume){
        return random.nextDouble(volume);//для веса животного
    }
    public static void validRandonDouble(double volume){
        if (volume < 0){
            throw new IllegalArgumentException("Число должно быть положительным");
        }
    }
}
