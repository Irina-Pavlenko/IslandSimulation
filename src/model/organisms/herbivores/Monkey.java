package model.organisms.herbivores;

import model.Island;
import model.organisms.Herbivores;

public class Monkey extends Herbivores {
    public Monkey(Island island, double weight, int maxPerLocation, int speed, double foodRequired, double reproductionChance) {
        super(island, weight, maxPerLocation, speed, foodRequired, reproductionChance);
    }
    @Override
    public void reproduce() {

    }
}
