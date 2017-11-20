package sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtils {
    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 10000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 10000;
    //    private static HttpClient client=null;
    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static RequestConfig requestConfig = null;
    private static HttpClientBuilder builder = null;
    private static int MAXCONNECTION = 10;

    private static int DEFAULTMAXCONNECTION = 5;

    static {
        SSLContext sslcontext = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            sslcontext = SSLContexts.custom().useTLS()
                    .loadTrustMaterial(trustStore, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, hostnameVerifier);
        Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslsf)
                .build();
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(WAIT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(20000)
                .build();

        connectionManager = new PoolingHttpClientConnectionManager(sfr);
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        builder = HttpClients.custom();
        builder.setConnectionManager(connectionManager);

    }

    public HttpUtils() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    }

    //    获取client
    public static CloseableHttpClient getConnection() {
        CloseableHttpClient client = builder.build();

        return client;
    }

    private static HttpRequest setHeader(HttpRequest method) {

        return null;
    }

    //    获取cookies
    public static CookieStore getCookie() throws IOException {
        CookieStore cookieStore = null;
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = getConnection();
            HttpGet get = new HttpGet(Constans.INIT);
            cookieStore = new BasicCookieStore();
            BasicHttpContext context = new BasicHttpContext();
            context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            response = client.execute(get, context);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        // TODO: 2017/11/16  关闭流
        return cookieStore;
    }

    //    获取验证码,type: 验证码类型
    public static byte[] getLoginCode(String type) {
        CloseableHttpClient client = getConnection();
        HttpGet get = null;
        switch (type) {
            case "login":
                get = new HttpGet(Constans.GETLOGINCODE);
                break;
            case "passenger":
                get = new HttpGet(Constans.ORDERVCODE);
        }
        CloseableHttpResponse response = null;
        HttpEntity entity;
        byte[] bytes = null;
        try {
            response = client.execute(get);
            if (response != null) {
                entity = response.getEntity();
                bytes = EntityUtils.toByteArray(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }


    //    验证码校验
    public static boolean checkCode(String code) {
        CloseableHttpClient client = getConnection();
        HttpGet get = null;
        HttpEntity entity = null;
        CloseableHttpResponse response = null;

        try {
            response = client.execute(get);
            entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            Result res = JSON.parseObject(result, Result.class);
            if ("TRUE".equals(res.getData().getMsg())) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }


    //    登录
    public static boolean login(CloseableHttpClient client, String uName, String pwd, String code) {
        HttpGet firstGet = new HttpGet(Constans.FIRSTLOGIN);
        HttpGet secGet = new HttpGet(Constans.SECLOGIN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginUserDTO.user_name", uName));
        params.add(new BasicNameValuePair("userDTO.password", pwd));
        params.add(new BasicNameValuePair("randCode", code));
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        Result res = null;
        try {
            response = client.execute(firstGet);
            entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            res = JSON.parseObject(result, Result.class);
            if (!"Y".equals(res.getData().getLoginCheck())) {
                return false;
            }
            response.close();
//            开始正式登录
            response = client.execute(secGet);
            entity = response.getEntity();
            res = JSON.parseObject(EntityUtils.toString(entity), Result.class);
            if ("登录成功".equals(res.getResult_message())) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return true;
    }

    //    获取常用联系人
    public static List<Passenger> getPassengers(CloseableHttpClient client) {
        HttpGet get = new HttpGet(Constans.GETPASSENGER);
        CloseableHttpResponse response;
        List<Passenger> lists = new ArrayList<>();
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            Map map = JSON.parseObject(s, Map.class);
            List<JSONObject> list;
            if (map.size() > 0) {
                Map data = (Map) map.get("data");
                if (data.size() > 0) {
                    list = (List<JSONObject>) data.get("normal_passengers");
                    if (list.size() > 0){
                        for (JSONObject passenger : list) {
                            Passenger passenger1 = JSONObject.parseObject(passenger.toString(), Passenger.class);
                            lists.add(passenger1);
                        }
                    }
                }
            }
            for (Passenger passenger : lists) {
                System.out.println("============"+passenger.getPassenger_name()+"===========");
                System.out.println(passenger.toString());
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lists;
    }

//    查询查票
//    public /
    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 30; i++) {
//            List<Cookie> cookies = getCookie().getCookies();
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName() + ":" + cookie.getValue());
//            }
//            System.out.println("============================================");
//        }
        List<Passenger> lists = new ArrayList<>();
        String json = "{'validateMessagesShowId':'_validatorMessage','status':true,'httpstatus':200,'data':{'isExist':true,'exMsg':'','two_isOpenClick':['93','95','97','99'],'other_isOpenClick':['91','93','98','99','95','97'],'normal_passengers':[{'code':'8','passenger_name':'王衡','sex_code':'M','sex_name':'男','born_date':'1992-12-25 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430422199212259710','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'18911424202','phone_no':'','email':'510431599@qq.com','address':'','postalcode':'','first_letter':'','recordCount':'12','total_times':'99','index_id':'0'},{'code':'1','passenger_name':'李琳凤','sex_code':'F','sex_name':'女','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430481199211130042','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'LLF','recordCount':'12','total_times':'99','index_id':'1'},{'code':'5','passenger_name':'罗琼','sex_code':'F','sex_name':'女','born_date':'2015-02-28 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430422198004199640','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'18911424202','phone_no':'','email':'','address':'','postalcode':'','first_letter':'LQ','recordCount':'12','total_times':'99','index_id':'2'},{'code':'3','passenger_name':'廖晓东','sex_code':'M','sex_name':'男','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'612426198307127637','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'LXD','recordCount':'12','total_times':'99','index_id':'3'},{'code':'4','passenger_name':'刘晓霞','sex_code':'M','sex_name':'男','born_date':'2017-08-19 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'130423198411083384','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'LXX','recordCount':'12','total_times':'99','index_id':'4'},{'code':'2','passenger_name':'李佐丽','sex_code':'F','sex_name':'女','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430481198310192463','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'LZL','recordCount':'12','total_times':'99','index_id':'5'},{'code':'7','passenger_name':'王秀英','sex_code':'M','sex_name':'男','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'341126197010166363','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'','phone_no':'','email':'','address':'','postalcode':'','first_letter':'WXY','recordCount':'12','total_times':'99','index_id':'6'},{'code':'6','passenger_name':'王乙平','sex_code':'M','sex_name':'男','born_date':'2016-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430422197502035476','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'','phone_no':'','email':'','address':'','postalcode':'','first_letter':'WYP','recordCount':'12','total_times':'99','index_id':'7'},{'code':'9','passenger_name':'谢春','sex_code':'M','sex_name':'男','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430481199301120892','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'XC','recordCount':'12','total_times':'99','index_id':'8'},{'code':'10','passenger_name':'尹秀丽','sex_code':'F','sex_name':'女','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'421181199110193928','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'YXL','recordCount':'12','total_times':'99','index_id':'9'},{'code':'11','passenger_name':'郑婧','sex_code':'F','sex_name':'女','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430481200509150105','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'Z-','recordCount':'12','total_times':'95','index_id':'10'},{'code':'12','passenger_name':'郑慧','sex_code':'F','sex_name':'女','born_date':'1980-01-01 00:00:00','country_code':'CN','passenger_id_type_code':'1','passenger_id_type_name':'二代身份证','passenger_id_no':'430481200405240261','passenger_type':'1','passenger_flag':'0','passenger_type_name':'成人','mobile_no':'17310220870','phone_no':'','email':'','address':'','postalcode':'','first_letter':'ZH','recordCount':'12','total_times':'95','index_id':'11'}],'dj_passengers':[]},'messages':[],'validateMessages':{}}";
        Map map = JSON.parseObject(json, Map.class);
//        List<JSONObject> list = new ArrayList<>();
//        if (map.size() > 0) {
//            Map data = (Map) map.get("data");
//            if (data.size() > 0) {
//                list = (List<JSONObject>) data.get("normal_passengers");
//                if (list.size() > 0){
//                    for (JSONObject passenger : list) {
//                        Passenger passenger1 = JSONObject.parseObject(passenger.toString(), Passenger.class);
//                        lists.add(passenger1);
//                    }
//                }
//            }
//        }
//        for (Passenger passenger : lists) {
//            System.out.println("============"+passenger.getPassenger_name()+"===========");
//            System.out.println(passenger.toString());
//        }
//        Utils utils = new Utils();
//        utils.jsonToMap(json);
//        for (Object o : map.keySet()) {
//            System.out.println(map.get(o.toString()));
//        }
    }

}
