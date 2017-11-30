package babyname.babyname.bean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class NameListBean {

    public NameListBean(String id,String man,String surname,String name,String mark) {
        this.id=id;
        this.man=man;
        this.surname=surname;
        this.name=name;
        this.mark=mark;


    }


    private String id;
    private String man;
    private String surname;
    private String name;
    private String mark;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
