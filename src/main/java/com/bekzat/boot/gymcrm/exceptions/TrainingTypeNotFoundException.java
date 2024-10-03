package com.bekzat.boot.gymcrm.exceptions;

public class TrainingTypeNotFoundException extends NotFoundException {

    public TrainingTypeNotFoundException(String name) {
        super("TrainingType with name " + name + " doesn't exist");
    }
}
