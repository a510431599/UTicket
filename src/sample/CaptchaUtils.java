package sample;

public class CaptchaUtils {
    private static int _appID = 51705;		//应用ID
    private static String _softKey = "be40e2ee09b257ca913513867a49596f";	//软件KEY
    private static String _uname = "a510431599a";	//用户名
    private static String _upwd = "a12301230";		//用户密码

    public static String decode(byte[] data){
        Dama2Web web = new Dama2Web(_appID,_softKey,_uname,_upwd);
        Dama2Web.DecodeResult result = web.decodeAndGetResult(310, 10000, data);
        return  result.result;
    }
}
