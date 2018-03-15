package ma.najeh.ibnouzouhr.model;



import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by youssef on 12/19/17.
 */
@Embeddable
public class Hour implements Serializable {
    private Integer h;
    private Integer m;

    public Hour(){

    }
    public Hour(String hour){
        String[] time = hour.split(":");
        this.h = new Integer(time[0]);
        this.m = new Integer(time[1]);
    }
    public Hour(Integer h, Integer m) {
        this.h = h;
        this.m = m;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "Hour{" +
                "h=" + h +
                ", m=" + m +
                '}';
    }
}
