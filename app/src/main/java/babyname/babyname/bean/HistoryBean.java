package babyname.babyname.bean;

/**
 * Created by Administrator on 2017/7/7.
 */

public class HistoryBean {


    public HistoryBean(String orderno,String status,String paych,String name) {
        this.orderno=orderno;
        this.status=status;
        this.paych=paych;
        this.name=name;


    }


    private String orderno;
    private String status;
    private String date;

    private String data;
    private String paych;
    private String databack;
    private String pay_fee;
    private String created;

    private String name;
    private String gender;
    private String birthday;
    private String birthtime;
    private String birthmin;
    private String pay_order_no;
    private String device;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPaych() {
        return paych;
    }

    public void setPaych(String paych) {
        this.paych = paych;
    }

    public String getDataback() {
        return databack;
    }

    public void setDataback(String databack) {
        this.databack = databack;
    }

    public String getPay_fee() {
        return pay_fee;
    }

    public void setPay_fee(String pay_fee) {
        this.pay_fee = pay_fee;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getBirthmin() {
        return birthmin;
    }

    public void setBirthmin(String birthmin) {
        this.birthmin = birthmin;
    }

    public String getPay_order_no() {
        return pay_order_no;
    }

    public void setPay_order_no(String pay_order_no) {
        this.pay_order_no = pay_order_no;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
