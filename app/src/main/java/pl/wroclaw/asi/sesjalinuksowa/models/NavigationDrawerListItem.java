package pl.wroclaw.asi.sesjalinuksowa.models;

/**
 * Created by fares on 08.04.15.
 */
public class NavigationDrawerListItem {

    public static final int TYPE_HEADER = 4;
    public static final int TYPE_ITEM = 5;
    public static final int TYPE_DIVIDER = 6;

    private String title;
    private String subtitle;
    private int imageID;
    private int itemType;
    private int background;

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    // ---------------- GETTERS / SETTERS ----------------
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getItemType() {
        return itemType;
    }
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
    // ---------------- -------------------- ----------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationDrawerListItem)) return false;

        NavigationDrawerListItem that = (NavigationDrawerListItem) o;

        if (background != that.background) return false;
        if (imageID != that.imageID) return false;
        if (itemType != that.itemType) return false;
        if (subtitle != null ? !subtitle.equals(that.subtitle) : that.subtitle != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + imageID;
        result = 31 * result + itemType;
        result = 31 * result + background;
        return result;
    }
}