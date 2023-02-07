package org.example;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

abstract public class NamedPet implements Pet {
    final private String name;
    final private String species;

    public NamedPet(final String name, final String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    private String getSpecies() {
        return species;
    }

    final public void doSomething(final Scanner input, final PrintStream output) {
        output.println("Hi! You're about to do something with " + this.getName());
        final Map<String, PetAction> actions = getActions();
        for (final String verb : actions.keySet()) {
            output.println("'" + verb + "'");
        }
        String verb;
        do {
            output.println("Please choose from the above options");
            verb = input.next().trim();
        } while (!actions.containsKey(verb));

        actions.get(verb).run();

        output.println("After " + verb + ", " + getName() + " has status " + toString());
    }

    @Override
    public String toString() {
        return "NamedPet{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                '}';
    }
}
