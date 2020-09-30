package ro.mihai.fitness_App.database;

public class Article {
    private int id;
    private String title;
    private String description;
    private String content;
    private String photoFile;
    private int category_id;
    private int chosen_option;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getChosen_option() {
        return chosen_option;
    }

    public void setChosen_option(int chosen_option) {
        this.chosen_option = chosen_option;
    }
}
