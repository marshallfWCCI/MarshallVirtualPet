package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

abstract public class OrganicPet extends NamedPet {
    private int hunger = 0;
    private int fatigue = 0;

    final int feedIncrement;
    final int sleepIncrement;
    final int hungerIncrement;
    final int fatigueIncrement;

    public OrganicPet(final String name, final String species, final int feedIncrement, final int sleepIncrement, final int hungerIncrement, final int fatigueIncrement) {
        super(name, species);
        this.feedIncrement = feedIncrement;
        this.sleepIncrement = sleepIncrement;
        this.hungerIncrement = hungerIncrement;
        this.fatigueIncrement = fatigueIncrement;
    }

    public void feed(final int amount) {
        this.hunger -= amount;
    }

    public void rest(final int hours) {
        this.fatigue -= hours;
    }

    final public void tick() {
        this.hunger += hungerIncrement;
        this.fatigue += fatigueIncrement;
    }

    @Override
    public Map<String, PetAction> getActions() {
        final Map<String, PetAction> actions = new LinkedHashMap<>();
        actions.put("Feed", new PetAction(() -> feed(this.feedIncrement)));
        actions.put("Rest", new PetAction(() -> rest(this.sleepIncrement)));
        return actions;
    }

    @Override
    public String toString() {
        return "OrganicPet{" +
                "hunger=" + hunger +
                ", fatigue=" + fatigue +
                '}' + super.toString();
    }
}
