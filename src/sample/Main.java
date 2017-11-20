package sample;

import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello1 World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
//        launch(args);
        CloseableHttpClient client = HttpUtils.getConnection();
        // TODO: 2017/11/16 uri提取
        String uri = "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.953517538406317";
//        String uri = "https://kyfw.12306.cn/otn/login/init";
        HttpGet get = new HttpGet(uri);
        CookieStore store = HttpUtils.getCookie();
        CloseableHttpResponse response = client.execute(get);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(entity);
        File file = new File("d://a.jpg");
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(bytes);
        String decode = CaptchaUtils.decode(bytes);
        String[] decodes = decode.split("\\|");
        decode = String.join(",",decodes);
        String codeCheckUri = "https://kyfw.12306.cn/passport/captcha/captcha-check";
        HttpPost httpPosts = new HttpPost(codeCheckUri);
        List<NameValuePair> paramss = new ArrayList<NameValuePair>();
        paramss.add(new BasicNameValuePair("answer",decode));
        paramss.add(new BasicNameValuePair("login_site","E"));
        paramss.add(new BasicNameValuePair("rand","sjrand"));
//        HttpGet httpGet = new HttpGet(codeCheckUri+"?"+new UrlEncodedFormEntity(paramss, Consts.UTF_8));
        httpPosts.setEntity(new UrlEncodedFormEntity(paramss,Consts.UTF_8));
        CloseableHttpResponse resp = client.execute(httpPosts);
        HttpEntity entity1 = resp.getEntity();
        String s = EntityUtils.toString(entity1);
        Map map = JSON.parseObject(s, Map.class);
//        if (!"4".equals(map.get("result_code"))){
//            System.out.println("校验失败");
//        }
        String loginUri = "https://kyfw.12306.cn/passport/web/login";
        HttpPost httpPost = new HttpPost(loginUri);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username","a510431599a"));
        params.add(new BasicNameValuePair("password","a12301230"));
        params.add(new BasicNameValuePair("appid","otn"));
        httpPost.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
        CloseableHttpResponse response1 = client.execute(httpPost);
        System.out.println(EntityUtils.toString(response1.getEntity()));
        List<Cookie> cookies = store.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+":"+cookie.getValue());
        }

//        launch(args);
        HttpClient httpclient = new DefaultHttpClient();
//        // Secure Protocol implementation.
//        SSLContext ctx = SSLContext.getInstance("SSL");
//        // Implementation of a trust manager for X509 certificates
//        X509TrustManager tm = new X509TrustManager() {
//
//            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//
//            }
//
//            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//            }
//
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//        ctx.init(null, new TrustManager[]{tm}, null);
//        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
//
        ClientConnectionManager ccm = httpclient.getConnectionManager();
//        // register https protocol in httpclient's scheme registry
//        SchemeRegistry sr = ccm.getSchemeRegistry();
//        sr.register(new Scheme("https", 443, ssf));
//
//        HttpGet httpget = new HttpGet(
//                "https://kyfw.12306.cn/otn/login/init");
//        HttpParams params = httpclient.getParams();
//
//        System.out.println("Request URL:" + httpget.getURI());
//        ResponseHandler responseHandler = new BasicResponseHandler();
//
//        String responseBody = (String) httpclient.execute(httpget, responseHandler);
//
//
//        System.out.println(responseBody);

        // Create a response handler
    }
}
