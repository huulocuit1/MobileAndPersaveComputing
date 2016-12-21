package entities;

/**
 * Created by HL_TH on 12/17/2016.
 */

public class BaseItem {
    private int icon;
    private String address;

    public BaseItem(String address, int icon) {
        this.address = address;
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
