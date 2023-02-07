package org.example;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class Shelter {
    private final Scanner input;
    private final PrintStream output;
    private final Map<String, Function<String, NamedPet>> species = new HashMap<>();
    private final Map<String, NamedPet> guests = new HashMap<>();
    private final Map<String, BooleanSupplier> mainLoopActions = new HashMap<>();

    public Shelter(final Scanner in, final PrintStream out) {
        this.input = in;
        this.output = out;
    }

    public static void main(String[] args) {
        final Shelter shelter = new Shelter(new Scanner(System.in), System.out);
        shelter.registerTheUsualSpecies();
        shelter.registerTheUsualActions();

        shelter.runLoop();
    }

    private void runLoop() {
        boolean continueLooping = true;
        do {
            output.println("Please choose a verb");
            for (final String verb : mainLoopActions.keySet()) {
                output.println("'" + verb + "'");
            }
            final String choice = input.next().trim();
            if (mainLoopActions.containsKey(choice)) {
                continueLooping = mainLoopActions.get(choice).getAsBoolean();
            } else {
                output.println("Don't know how to '" + choice + "' ... please try again");
            }
        } while (continueLooping);
    }

    private void registerTheUsualActions() {
        mainLoopActions.put("createAPet",
                () -> {this.createAPet(); this.tickAllGuests(); return true;});
        mainLoopActions.put("playWithPet",
                () -> {this.playWithPet(); this.tickAllGuests(); return true;});
        mainLoopActions.put("tick",
                () -> {this.tickAllGuests(); return true;});
        mainLoopActions.put("quit",
                () -> {return false;});
    }

    private void playWithPet() {
        for (final String petName : this.guests.keySet()) {
            this.output.println("Guest: '" + petName + "'");
        }

        output.println("Please enter the name of your pet");
        final String choice = input.next().trim();
        if (!guests.containsKey(choice)) {
            output.println("Unable to find '" + choice + "'. Please try again later");
            return;
        }

        guests.get(choice).doSomething(this.input, this.output);
    }

    public void createAPet() {
        output.println("What type of pet would you like.");
        for (final String petType : species.keySet()) {
            output.println("'" + petType + "'");
        }
        output.println("Please type in the type");
        final String choice = input.next().trim();
        if (!species.containsKey(choice)) {
            output.println("'" + choice + "' not found ... we'll try again later.");
            return;
        }

        output.println("Please type in the name");
        final String name = input.next().trim();
        if (guests.containsKey(name)) {
            output.println("'" + name + "' is already a guest ... we'll try again later.");
            return;
        }

        final NamedPet newPet = species.get(choice).apply(name);
        guests.put(name, newPet);
        output.println("New pet created: " + newPet.toString());
    }

    private void registerTheUsualSpecies() {
        registerSpecies("Cat", (name) -> new Cat(name));
        registerSpecies("Dog", (name) -> new Dog(name));
        registerSpecies("RoboDog", (name) -> new RoboDog(name));
        registerSpecies("RoboCat", (name) -> new RoboCat(name));
    }

    public void registerSpecies(final String speciesName, final Function<String, NamedPet> maker) {
        species.put(speciesName, maker);
    }

    public void accept(final NamedPet pet) {
        guests.put(pet.getName(), pet);
    }

    public Optional<NamedPet> findGuest(final String name) {
        final NamedPet pet = guests.get(name);
        if (pet != null) {
            return Optional.of(pet);
        } else {
            return Optional.empty();
        }
    }

    public void doSomethingWithPet(final Scanner input, final PrintStream output) {
        output.println("Please enter a pet name");
        final String name = input.next();
        final Optional<NamedPet> perhapsPet = findGuest(name);

        if (perhapsPet.isEmpty()) {
            output.println("Our apologies, but we don't have a " + name + "here :-(");
        } else {
            perhapsPet.get().doSomething(input, output);
        }
    }

    public void tickAllGuests() {
        for (final Pet pet : guests.values()) {
            pet.tick();
        }
        for (final Pet pet : guests.values()) {
            output.println(pet.toString());
        }
    }
}
