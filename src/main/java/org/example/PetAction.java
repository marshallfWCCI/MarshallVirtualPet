package org.example;

public class PetAction {
    final Runnable behavior;

    public PetAction(final Runnable behavior) {
        this.behavior = behavior;
    }

    public void run() {
        behavior.run();
    }
}
