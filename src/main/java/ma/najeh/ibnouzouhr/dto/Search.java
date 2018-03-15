package ma.najeh.ibnouzouhr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Search implements Serializable {
   String firstName;
   String lastName;
   String cin;
   String cne;
   String codeApogee;
}
