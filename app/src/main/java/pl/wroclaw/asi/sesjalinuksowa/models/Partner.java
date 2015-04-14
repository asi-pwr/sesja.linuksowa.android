package pl.wroclaw.asi.sesjalinuksowa.models;

/**
 * Created by fares on 08.04.15.
 */
public class Partner {

    public static final int SPONSOR_GOLD = 0;
    public static final int SPONSOR_SILVER = 1;
    public static final int MEDIA = 3;

    private String name;
    private String webUrl;
    private String description;
    private int type;
    private String imgUrl;

    public Partner(String name, String description, String webUrl, String imgUrl, int type) {
        this.name = name;
        this.description = description;
        this.webUrl = webUrl;
        this.imgUrl = imgUrl;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
