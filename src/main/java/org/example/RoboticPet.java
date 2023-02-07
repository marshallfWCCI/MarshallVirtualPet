package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

abstract public class RoboticPet extends NamedPet {
    private int oil = 0;
    private int rust = 0;

    final int oilIncrement;
    final int polishIncrement;
    final int rustIncrement;
    final int dullnessIncrement;

    public RoboticPet(final String name, final String species, final int oilIncrement, final int polishIncrement, int rustIncrement, int dullnessIncrement) {
        super(name, species);
        this.oilIncrement = oilIncrement;
        this.polishIncrement = polishIncrement;
        this.rustIncrement = rustIncrement;
        this.dullnessIncrement = dullnessIncrement;
    }

    public void oil(final int amount) {
        this.oil += amount;
    }

    public void polish(final int amount) {
        this.rust -= amount;
    }

    public void tick() {
        this.oil -= rustIncrement;
        this.rust += dullnessIncrement;
    }

    @Override
    public String toString() {
        return "RoboticPet{" +
                "oil=" + oil +
                ", rust=" + rust +
                '}' + super.toString();
    }

    @Override
    public Map<String, PetAction> getActions() {
        final Map<String, PetAction> actions = new LinkedHashMap<>();
        actions.put("Oil", new PetAction(() -> oil(this.oilIncrement)));
        actions.put("Polish", new PetAction(() -> polish(this.polishIncrement)));
        return actions;
    }
}
