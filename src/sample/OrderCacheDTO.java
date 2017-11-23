package sample;

import java.util.Arrays;
import java.util.List;

public class OrderCacheDTO {

    private String requestId;//请求序列id
    private String requestTime;//请求时间
    private String queueOffset;//队列偏移量
    private String queueName;//队列名字
    private String stationTrainCode;//车次
    private String fromStationName;//出发站
    private String toStationName;//目的站
    private List<Ticket> tickets;//车票信息
    private String waitTime;//排队时间
    private String waitCount;//排队人数
    private String ticketCount;//车票张数
    private String  startTimeString;//开车时间
    private String[] array_passser_name_page;//乘车人姓名，数组

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getQueueOffset() {
        return queueOffset;
    }

    public void setQueueOffset(String queueOffset) {
        this.queueOffset = queueOffset;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(String waitCount) {
        this.waitCount = waitCount;
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String[] getArray_passser_name_page() {
        return array_passser_name_page;
    }

    public void setArray_passser_name_page(String[] array_passser_name_page) {
        this.array_passser_name_page = array_passser_name_page;
    }

    @Override
    public String toString() {
        return "OrderCacheDTO{" +
                "requestId='" + requestId + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", queueOffset='" + queueOffset + '\'' +
                ", queueName='" + queueName + '\'' +
                ", stationTrainCode='" + stationTrainCode + '\'' +
                ", fromStationName='" + fromStationName + '\'' +
                ", toStationName='" + toStationName + '\'' +
                ", tickets=" + tickets +
                ", waitTime='" + waitTime + '\'' +
                ", waitCount='" + waitCount + '\'' +
                ", ticketCount='" + ticketCount + '\'' +
                ", startTimeString='" + startTimeString + '\'' +
                ", array_passser_name_page=" + Arrays.toString(array_passser_name_page) +
                '}';
    }
}
