package sample;

public class Data {

    private String result;//返回核心数据
    private String msg;
    private String otherMsg;
    private String loginCheck;
    private String flag;
    private String map;//站点缩写映射
    private String submitStatus;//提交状态
    private String count;
    private String ticket;
    private OrderCacheDTO orderCacheDTO;


    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public OrderCacheDTO getOrderCacheDTO() {
        return orderCacheDTO;
    }

    public void setOrderCacheDTO(OrderCacheDTO orderCacheDTO) {
        this.orderCacheDTO = orderCacheDTO;
    }

    public String getCountT() {
        return countT;
    }

    public void setCountT(String countT) {
        this.countT = countT;
    }

    private String countT;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg;
    }

    public String getLoginCheck() {
        return loginCheck;
    }

    public void setLoginCheck(String loginCheck) {
        this.loginCheck = loginCheck;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Data{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", otherMsg='" + otherMsg + '\'' +
                ", loginCheck='" + loginCheck + '\'' +
                ", flag='" + flag + '\'' +
                ", map='" + map + '\'' +
                ", submitStatus='" + submitStatus + '\'' +
                ", count='" + count + '\'' +
                ", ticket='" + ticket + '\'' +
                ", orderCacheDTO=" + orderCacheDTO +
                ", countT='" + countT + '\'' +
                '}';
    }
}
