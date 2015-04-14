package pl.wroclaw.asi.sesjalinuksowa.models;

/**
 * Created by fares on 08.04.15.
 */
public class Organizer {
    private String name;
    private String surname;
    private String mail;
    private String function;
    private String imgUrl;

    public Organizer(String name, String surname, String mail, String imgUrl, String function) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.function = function;
        this.imgUrl = imgUrl;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFunction() {
        return function;
    }
    public void setFunction(String function) {
        this.function = function;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
