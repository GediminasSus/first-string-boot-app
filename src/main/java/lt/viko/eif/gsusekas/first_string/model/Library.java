package lt.viko.eif.gsusekas.first_string.model;

import jakarta.xml.bind.annotation.*;


import java.util.List;

@XmlRootElement(name = "library")
@XmlAccessorType(XmlAccessType.FIELD)
public class Library {

    private int id;
    private String name;
    private String city;
    private boolean open;
    
    private float yearlyBudget;

    @XmlElementWrapper(name = "books")
    @XmlElement(name = "book")
    private List<Book> books;

    public Library() {
    }

    public Library(int id, String name, String city, boolean open, float yearlyBudget, List<Book> books) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.open = open;
        this.yearlyBudget = yearlyBudget;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public boolean isOpen() {
        return open;
    }

    public float getYearlyBudget() {
        return yearlyBudget;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setYearlyBudget(float yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
