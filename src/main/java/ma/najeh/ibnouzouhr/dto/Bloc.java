package ma.najeh.ibnouzouhr.dto;


import java.io.Serializable;

/**
 * Created by youssef on 12/10/17.
 */
public class Bloc implements Serializable {

    private Long id;
    private String name;
    private int numberSalle;
    private int capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberSalle() {
        return numberSalle;
    }

    public void setNumberSalle(int numberSalle) {
        this.numberSalle = numberSalle;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public String toString() {
        return "Bloc{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberSalle=" + numberSalle +
                ", capacity=" + capacity +
                '}';
    }
}
