package com.gamezone.model;

public class VideoGame extends Product{

    private String platform;
    private String genre;
    private String classification;

    public VideoGame(String id, String title, double price, int stockQuantity, String platform, String genre, String classification) {
        super(id, title, price, stockQuantity);
        this.platform = platform;
        this.genre = genre;
        this.classification = classification;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String getDescription() {
        return "VideoGame{" +
                "id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", stockQuantity=" + getStockQuantity() +
                ", platform='" + platform + '\'' +
                ", genre='" + genre + '\'' +
                ", classification='" + classification + '\'' +
                '}';
    }


}
