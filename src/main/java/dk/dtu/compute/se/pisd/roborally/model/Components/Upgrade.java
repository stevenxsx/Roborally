package dk.dtu.compute.se.pisd.roborally.model.Components;

public enum Upgrade {

    BRAKES("Brakes",3),
    FIREWALL("Firewall", 3),
    HOVER_UNIT("Hover Unit", 1);

    final public String displayName;
    final public int cost;


    Upgrade(String displayName, int cost, Upgrade... upgradeOpts) {
        this.displayName = displayName;
        this.cost = cost;
    }
}
