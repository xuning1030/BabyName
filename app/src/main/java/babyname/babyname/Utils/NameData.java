package babyname.babyname.Utils;

/**
 * Created by Administrator on 2017/7/5.
 */

public class NameData {
    private String name;
    private String Number;
    private String wuge;

    public NameData(String name,String Number){
        this.name=name;
        this.Number=Number;
    }

    public NameData(String wuge,String name, String number) {
        this.name = name;
        this.Number = number;
        this.wuge = wuge;
    }

    public String getWuge() {
        return wuge;
    }

    public void setWuge(String wuge) {
        this.wuge = wuge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
