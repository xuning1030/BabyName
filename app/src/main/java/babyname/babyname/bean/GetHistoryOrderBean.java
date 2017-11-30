package babyname.babyname.bean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class GetHistoryOrderBean {

    private String surname;
    private String man;
    private String lunar;
    private String solar;
    private String orderno;

    public GetHistoryOrderBean(String surname,String man,String lunar,String solar,String orderno) {
        this.surname=surname;
        this.man=man;
        this.lunar=lunar;
        this.solar=solar;
        this.orderno=orderno;


    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getSolar() {
        return solar;
    }

    public void setSolar(String solar) {
        this.solar = solar;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
