package net.royalbyte.creative.handler;

public class PlotData {

    private int ID, FROM_X, FROM_Z, TO_X, TO_Z;
    private String owner_uuid, owner_name;

    public PlotData(int ID, int FROM_X, int FROM_Z, int TO_X, int TO_Z, String owner_uuid, String owner_name) {
        this.ID = ID;
        this.FROM_X = FROM_X;
        this.FROM_Z = FROM_Z;
        this.TO_X = TO_X;
        this.TO_Z = TO_Z;
        this.owner_uuid = owner_uuid;
        this.owner_name = owner_name;
    }

    public int getID() {
        return ID;
    }

    public int getFROM_X() {
        return FROM_X;
    }

    public int getFROM_Z() {
        return FROM_Z;
    }

    public int getTO_X() {
        return TO_X;
    }

    public int getTO_Z() {
        return TO_Z;
    }

    public String getOwner_uuid() {
        return owner_uuid;
    }

    public String getOwner_name() {
        return owner_name;
    }
}
