package lt.viko.eif.gsusekas.first_string.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;




@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

    private int id;
    private String title;
    private String author;
    private int pages;
    private float price;
    private boolean available;

    public Book() {
    }

    public Book(int id, String title, String author, int pages, float price, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public float getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
