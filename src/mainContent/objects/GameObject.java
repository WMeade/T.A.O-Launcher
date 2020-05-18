package mainContent.objects;

public class GameObject {
    String name;
    String executablePath;
    String belongsTo;
    Boolean isFavourite;
    Boolean onToolbar;

    public GameObject(){

    }

    public GameObject(String name, String executablePath, String belongsTo, Boolean isFavourite){
        this.name = name;
        this.executablePath = executablePath;
        this.isFavourite = isFavourite;
        this.belongsTo = belongsTo;
        this.onToolbar = false;
    }

    public String getName() {
        return name;
    }

    public Boolean getOnToolbar() {
        return onToolbar;
    }

    public void setOnToolbar(Boolean onToolbar) {
        this.onToolbar = onToolbar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutablePath() {
        return executablePath;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setExecutablePath(String executablePath) {
        this.executablePath = executablePath;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }
}
