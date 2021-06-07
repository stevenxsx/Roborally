package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Command;

import java.util.Arrays;
import java.util.Collections;

public enum Upgrade {




    BRAKES("Brakes"), //Upgrade ID 1
    VIRUS_MODULE("Virus Module"), //ID 2 etc.
    TROJAN_NEEDLER("Trojan Needler"),
    REAR_LASER("Rear Laser");

    final public String displayName;

    Upgrade(String displayName, Command... options) {
        this.displayName = displayName;
    }
}
