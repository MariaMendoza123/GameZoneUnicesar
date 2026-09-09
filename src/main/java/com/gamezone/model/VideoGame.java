package com.gamezone.model;

/**
 * Represents a video game product in the game zone.
 */
public class VideoGame extends Product{

    private String platform;
    private String genre;
    private String classification;
    /**
     * Constructs a new VideoGame instance.
     *
     * @param id            Unique identifier for the product.
     * @param title         Title or name of the video game.
     * @param price         Base price of the video game.
     * @param stockQuantity Available quantity in stock.
     * @param platform      Platform on which the video game can be played (e.g., PC, PlayStation, Xbox).
     * @param genre         Genre of the video game (e.g., Action, Adventure, RPG).
     * @param classification Classification or rating of the video game (e.g., E for Everyone, M for Mature).
     */
    public VideoGame(String id, String title, double price, int stockQuantity, String platform, String genre, String classification) {
        super(id, title, price, stockQuantity);
        this.platform = platform;
        this.genre = genre;
        this.classification = classification;
    }
    /**
     * Returns the platform of the video game.
     *
     * @return the platform of the video game
     */
    public String getPlatform() {
        return platform;
    }
    /**
     * Sets the platform of the video game.
     *
     * @param platform the platform of the video game
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    /**
     * Returns the genre of the video game.
     *
     * @return the genre of the video game
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Sets the genre of the video game.
     *
     * @param genre the genre of the video game
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Returns the classification of the video game.
     *
     * @return the classification of the video game
     */
    public String getClassification() {
        return classification;
    }
    /**
     * Sets the classification of the video game.
     *
     * @param classification the classification of the video game
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }
    /**
     * Returns a string representation of the video game.
     *
     * @return a string representation of the video game
     */
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
   /**
    * Returns a string representation of the video game.
    *
    * @return a string representation of the video game
    */
   @Override
   public String toString() {
       return getDescription();
   }
}
