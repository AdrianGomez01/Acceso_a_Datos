package Tema2.Act1p2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Libro {
    @XmlAttribute(name = "id")
    private String id;
    @XmlElement(name = "Author")
    private String author;
    @XmlElement(name = "Title")
    private String title;
    @XmlElement(name = "Genre")
    private String genre;
    @XmlElement(name = "Price")
    private Double price;
    @XmlElement(name = "PublishDate")
    private Date publishDate;
    @XmlElement(name = "Description")
    private String description;

    public Libro() {

    }

    public Libro(String id, String Author, String Title, String Genre, Double Price, Date PublishDate, String Description) {
        this.id = id;
        this.author = Author;
        this.title = Title;
        this.genre = Genre;
        this.price = Price;
        this.publishDate = PublishDate;
        this.description = Description;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }


    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Double getPrice() {
        return price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                ", description='" + description + '\'' +
                '}';
    }
}



