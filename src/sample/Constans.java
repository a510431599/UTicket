package sample;

public class Constans {

    //    初始化，获取cookie
    public static final String INIT="https://kyfw.12306.cn/otn/login/init";
    //    获取验证码
    public static final String GETLOGINCODE = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random();
    //    验证码校验
    public static final String CHECKCODE = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
    //    第一次登录
    public static final String FIRSTLOGIN = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";
    //    真正登录
    public static final String SECLOGIN = "https://kyfw.12306.cn/otn/login/userLogin";
    //    获取联系人
    public static final String GETPASSENGER = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
    //    查票日志
    public static final String QUERYLOG = "https://kyfw.12306.cn/otn/leftTicket/log";
    //    查票
    public static final String QUERY = "https://kyfw.12306.cn/otn/leftTicket/queryT";
    //    提交订单
    public static final String SUBMITORDER = "https://kyfw.12306.cn/otn/confirmPassenger/autoSubmitOrderRequest";
    //    查询余票数量
    public static final String GETQUEENCOUNT = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCountAsync";
    //    订单验证码
    public static final String ORDERVCODE = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&"+Math.random();
    //    确认提交订单
    public static final String ORDER = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueueAsys";
    //    查询订单结果
    public static final String QUERYORDER = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random="+Math.random()+"&tourFlag=dc&_json_att=";

    public static final String COMFIRMORDER = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";

}
