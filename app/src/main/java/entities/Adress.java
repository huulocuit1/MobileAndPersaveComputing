package entities;

import java.util.ArrayList;

/**
 * Created by MinhNhan on 28/05/2016.
 */
public class Adress {
    private String Name ="";
    private ArrayList<String> Dist = new ArrayList<>();

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<String> getDist() {
        return Dist;
    }

    public void setDist(String dist) {
        Dist.add(dist);
    }
}
