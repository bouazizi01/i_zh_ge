package ma.najeh.ibnouzouhr.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends StdSerializer<Date> {
  
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
 
    public CustomDateSerializer() {
        this(null);
    }
 
    public CustomDateSerializer(Class t) {
        super(t);
    }
     
    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider sp)
      throws IOException {
        gen.writeString(formatter.format(value));
    }

}