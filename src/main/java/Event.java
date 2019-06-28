import java.util.Date;

public class Event {
    private boolean opening;
    private boolean recurring;
    private Date date;
    private Date endDate;

    Event(boolean opening, boolean recurring, Date date, Date endDate) {
        super();
        this.opening = opening;
        this.recurring = recurring;
        this.date = date;
        this.endDate = endDate;
    }

    public boolean isOpening(){
        return opening;
    }

    public boolean isBusy(){
        return !opening;
    }

    public boolean isRecurring(){
        return recurring;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    static String availabilities(Date fromDate, Date toDate){
        return "";
    }
}
