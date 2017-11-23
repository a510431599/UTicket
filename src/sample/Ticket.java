package sample;

public class Ticket {

    private String seatTypeName;
    private String seatTypeCode;
    private String ticketTypeName;
    private String passengerName;
    private String passengerIdTypeName;

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    public String getSeatTypeCode() {
        return seatTypeCode;
    }

    public void setSeatTypeCode(String seatTypeCode) {
        this.seatTypeCode = seatTypeCode;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerIdTypeName() {
        return passengerIdTypeName;
    }

    public void setPassengerIdTypeName(String passengerIdTypeName) {
        this.passengerIdTypeName = passengerIdTypeName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "seatTypeName='" + seatTypeName + '\'' +
                ", seatTypeCode='" + seatTypeCode + '\'' +
                ", ticketTypeName='" + ticketTypeName + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengerIdTypeName='" + passengerIdTypeName + '\'' +
                '}';
    }
}
