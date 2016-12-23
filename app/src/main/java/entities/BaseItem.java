package entities;

/**
 * Created by HL_TH on 12/17/2016.
 */

public class BaseItem {
    private int icon;
    private String name;

    public BaseItem(String address, int icon) {
        this.name = address;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
