package Mercury.Android.Mercury_Model.Entitys;

import java.util.Date;

public class Entity_03_Message {
    private String message;
    private Date dateTimeMessage;
    private Boolean wasVisualized;


    public Boolean getWasVisualized() {
        return wasVisualized;
    }

    public void setWasVisualized(Boolean wasVisualized) {
        this.wasVisualized = wasVisualized;
    }

    public Date getDateTimeMessage() {
        return dateTimeMessage;
    }

    public void setDateTimeMessage(Date dateTimeMessage) {
        this.dateTimeMessage = dateTimeMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
