package ma.najeh.ibnouzouhr.model.rest.message;

public class Message {
    private String message;
    private Object content;
    private String status;

    public Message(String message, Object content, String status) {
        this.message = message;
        this.content = content;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public Message setContent(Object content) {
        this.content = content;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Message setStatus(String status) {
        this.status = status;
        return this;
    }


}
