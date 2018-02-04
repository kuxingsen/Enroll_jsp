package cn.yiban.YBAPI;

import java.io.File;

import java.util.List;
import java.security.KeyStore;

import java.net.URL;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.Header;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;


/**
 * 简单HTTP请求
 *
 * 简单对HttpClient库做封装，方便在易班API接口中使用。
 * 只有POST与GET的简单请求，没有其它header设置等一些复杂功能
 */
public class HTTPSimple
{

    /**
     * HTTP的GET请求
     *
     * @return	String 返回内容
     */
    public static String GET(String url)
    {
        String responseContext = "";
        try
        {
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    return cn.yiban.util.HTTPSimple.GET(h[0].toString().substring(10));
                }
            }
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return responseContext;
    }


    /**
     * HTTP的POST请求
     *
     * @return	String 返回内容
     */
    public static String POST(String url, List<NameValuePair> param)
    {
        String responseContext = "";
        int found = url.indexOf('?');
        if (found > 0)
        {
            url = url.substring(0, found);
        }
        try
        {
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setEntity(new UrlEncodedFormEntity(param));//原方法默认只能发送ISO_8859_1编码，需指定utf-8才能发送中文
            httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    //System.out.println("?????????????"+h[0].toString().substring(10));
                    return HTTPSimple.POST(h[0].toString().substring(10), param);
                }
            }
            //System.out.println(status);
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            System.out.println(responseContext);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return responseContext;
    }

    /**
     * 通过URL地址验证是否HTTPS安全链接
     *
     * @return	boolean
     */
    private static boolean isSecurity(String url) throws Exception
    {
        URL u = new URL(url);
        return u.getProtocol().contentEquals("https");
    }

    /**
     * 初始化HttpClient对象
     *
     * 若url为https链接，则使用SSLConnection来初始化
     * 若为普通的HTTP链接，只使用默认的HttpClient参数初始化
     *
     * @return	CloseableHttpClient
     */
    private static CloseableHttpClient getClientInstance(String url) throws Exception
    {
        CloseableHttpClient httpclient = null;
        if (isSecurity(url))
        {
            KeyStore myTrustKeyStore = KeyStore.getInstance(
                    KeyStore.getDefaultType()
            );
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(myTrustKeyStore, new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier()
            );
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        else
        {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }
}