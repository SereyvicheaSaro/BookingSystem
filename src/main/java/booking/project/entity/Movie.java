package booking.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private String duration;

    @Column(name = "releaseDate")
    private String releaseDate;

    @Column(name = "hall")
    private String hall;

    @Column(name = "genre")
    private String genre;

    @Column(name = "extension")
    private String extension;

    @Column(name = "showingDate")
    private String showingDate;

    @Column(name = "showingTime")
    private String showingTime;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(String showingDate) {
        this.showingDate = showingDate;
    }

    public String getShowingTime() {
        return showingTime;
    }

    public void setShowingTime(String showingTime) {
        this.showingTime = showingTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie(Long id, String title, String duration, String releaseDate, String hall, String genre,
            String extension, String showingDate, String showingTime, String type, String description) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.hall = hall;
        this.genre = genre;
        this.extension = extension;
        this.showingDate = showingDate;
        this.showingTime = showingTime;
        this.type = type;
        this.description = description;
    }

    public Movie() {

    }
}
