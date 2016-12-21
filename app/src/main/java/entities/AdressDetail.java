package entities;

import java.util.ArrayList;

/**
 * Created by MinhNhan on 28/05/2016.
 */
public class AdressDetail {
    private ArrayList<String> cityList = new ArrayList<>();

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    private String currentCity;
    private Adress Hcm;
    private Adress DongNai;
    private Adress BinhDuong;

//    public AdressDetail() {
//        getCityList().add("TP. Hồ Chí Minh");
//        getCityList().add("Đồng Nai");
//        getCityList().add("Bình Dương");
//        setBinhDuong();
//        setDongNai();
//        setHcm();
//    }
    public AdressDetail(String name)
    {
        this.currentCity=new String("TP. Hồ Chí Minh");
        if(name.equals("Hcm"))
        {
            getCityList().add("TP. Hồ Chí Minh");
            setHcm();
        }

    }

    public Adress getHcm() {
        return Hcm;
    }

    public void setHcm() {
        Hcm = new Adress();
        Hcm.setName("TP. Hồ Chí Minh");
        Hcm.setDist("Quận 1");
        Hcm.setDist("Quận 2");
        Hcm.setDist("Quận 3");
        Hcm.setDist("Quận 4");
        Hcm.setDist("Quận 5");
        Hcm.setDist("Quận 6");
        Hcm.setDist("Quận 7");
        Hcm.setDist("Quận 8");
        Hcm.setDist("Quận 9");
        Hcm.setDist("Quận 10");
        Hcm.setDist("Quận 11");
        Hcm.setDist("Quận 12");
        Hcm.setDist("Quận Thủ Đức");
        Hcm.setDist("Quận Gò Vấp");
        Hcm.setDist("Quận Bình Thạnh");
        Hcm.setDist("Quận Tân Bình");
        Hcm.setDist("Quận Tân Phú");
        Hcm.setDist("Quận Phú Nhuận");
        Hcm.setDist("Quận Bình Tân");
        Hcm.setDist("Huyện Củ Chi");
        Hcm.setDist("Huyện Hóc Môn");
        Hcm.setDist("Huyện Bình Chánh");
        Hcm.setDist("Huyện Nhà Bè");
        Hcm.setDist("Huyện Cần Giờ");
    }

    public Adress getDongNai() {
        return DongNai;
    }

    public void setDongNai() {
        DongNai = new Adress();
        DongNai.setName("Đồng Nai");
        DongNai.setDist("Biên Hòa");
    }

    public Adress getBinhDuong() {
        return BinhDuong;
    }

    public void setBinhDuong() {
        BinhDuong = new Adress();
        BinhDuong.setName("Bình Dương");
        BinhDuong.setDist("Dĩ An");
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<String> cityList) {
        this.cityList = cityList;
    }

    public ArrayList<String> getDisFromCity(String city) {
        if (Hcm.getName().equals(city))
            return Hcm.getDist();
        if (BinhDuong.getName().equals(city))
            return BinhDuong.getDist();
        if (DongNai.getName().equals(city))
            return DongNai.getDist();

        return null;
    }
}
