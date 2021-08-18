package me.MuchDan.GlobalBoosters.ObjectFiles;

public class BoosterObject {
    private String SectionName;
    private String DisplayName;
    private String Type;
    private String Permission;
    private double Multiplier;
    private int Time;

    public BoosterObject(){
        SectionName = " ";
        DisplayName = " ";
        Type = " ";
        Permission = " ";
        Multiplier = 1;
        Time = 0;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPermission() {
        return Permission;
    }

    public void setPermission(String permission) {
        Permission = permission;
    }

    public double getMultiplier() {
        return Multiplier;
    }

    public void setMultiplier(double multiplier) {
        Multiplier = multiplier;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }
}
