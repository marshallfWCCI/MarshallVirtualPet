package org.example;

import java.util.Map;

public interface Pet {
    Map<String, PetAction> getActions();
    void tick();
}
