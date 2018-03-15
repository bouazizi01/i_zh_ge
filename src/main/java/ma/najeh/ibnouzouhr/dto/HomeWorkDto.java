package ma.najeh.ibnouzouhr.dto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
public class HomeWorkDto implements Serializable {
    private String title;
    private String description;
    private Long planingId;


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



    public Long getPlaningId() {
        return planingId;
    }

    public void setPlaningId(Long planingId) {
        this.planingId = planingId;
    }

    @Override
    public String toString() {
        return "HomeWorkDto{" +

                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", planingId=" + planingId +
                '}';
    }

}
