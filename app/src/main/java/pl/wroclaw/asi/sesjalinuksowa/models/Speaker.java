package pl.wroclaw.asi.sesjalinuksowa.models;

/**
 * Created by fares on 08.04.15.
 */
public class Speaker {
    private String name;
    private String surname;
    private String description;
    private String imgUrl;
    private int id;


    public Speaker(String name, String surname, String description, String imgUrl, int id) {
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.imgUrl = imgUrl;
        this.id = id;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
