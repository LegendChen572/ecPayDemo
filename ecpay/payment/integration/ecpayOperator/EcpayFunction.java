package ecpay.payment.integration.ecpayOperator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import ecpay.payment.integration.domain.AioCheckOutATM;
import ecpay.payment.integration.domain.AioCheckOutCVS;
import ecpay.payment.integration.exception.EcpayException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class EcpayFunction {
   private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
   private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";

   public static final String genCheckMacValue(String key, String iv, Object obj) {
      Class<?> cls = obj.getClass();
      List<String> fieldNames = getSortedFieldNames(cls);
      String data = "";

      try {
         Iterator var6 = fieldNames.iterator();

         while(var6.hasNext()) {
            String name = (String)var6.next();
            if (name != "CheckMacValue" && name != "PaymentToken") {
               Method method = cls.getMethod("get" + name, (Class[])null);
               data = data + "&" + name + "=" + method.invoke(obj).toString();
            }
         }

         String urlEncode = urlEncode("HashKey=" + key + data + "&HashIV=" + iv).toLowerCase();
         urlEncode = netUrlEncode(urlEncode);
         return hash(urlEncode.getBytes(), "SHA-256");
      } catch (Exception var9) {
         throw new EcpayException("產生檢查碼失敗");
      }
   }

   public static final String AESEncode(String HashKey, String HashIV, String plaintext) throws Exception {
      SecretKey key = new SecretKeySpec(HashKey.getBytes("UTF-8"), "AES");
      AlgorithmParameters iv = AlgorithmParameters.getInstance("AES");
      iv.init(new IvParameterSpec(HashIV.getBytes("UTF-8")));
      plaintext = pkcs7Padding(plaintext);
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      cipher.init(1, key, iv);
      byte[] encrypt = cipher.doFinal(plaintext.getBytes("UTF-8"));
      Encoder encoder = Base64.getEncoder();
      String encodedText = encoder.encodeToString(encrypt);
      String urlEncode = urlEncode(encodedText);
      urlEncode = netUrlEncode(urlEncode);
      urlEncode = urlEncode.toLowerCase();
      return urlEncode;
   }

   private static final String pkcs7Padding(String plaintext) {
      int blockSize = 16;
      int pad = 0;
      if (plaintext.length() < blockSize) {
         pad = blockSize - plaintext.length();
      } else if (plaintext.length() >= blockSize) {
         if (plaintext.length() % blockSize == 0) {
            pad = 16;
         } else {
            pad = blockSize - plaintext.length() % blockSize;
         }
      }

      for(int i = 0; i < pad; ++i) {
         plaintext = plaintext + (char)pad;
      }

      return plaintext;
   }

   public static final String genCheckMacValue(String key, String iv, Hashtable<String, String> params) {
      Set<String> keySet = params.keySet();
      TreeSet<String> treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
      treeSet.addAll(keySet);
      String[] name = (String[])treeSet.toArray(new String[treeSet.size()]);
      String paramStr = "";

      for(int i = 0; i < name.length; ++i) {
         if (!name[i].equals("CheckMacValue")) {
            paramStr = paramStr + "&" + name[i] + "=" + (String)params.get(name[i]);
         }
      }

      String par = "Hashkey=" + key + paramStr + "&HashIV=" + iv;
      String urlEncode = urlEncode(par).toLowerCase();
      urlEncode = netUrlEncode(urlEncode);
      return hash(urlEncode.getBytes(), "SHA-256");
   }

   public static final String genHttpValue(Object obj, String CheckMacValue) {
      Class<?> cls = obj.getClass();
      List<String> fieldNames = getSortedFieldNames(cls);
      String result = "";

      for(int i = 0; i < fieldNames.size(); ++i) {
         try {
            Method method = cls.getMethod("get" + (String)fieldNames.get(i), (Class[])null);
            String var10002 = (String)fieldNames.get(i);
            fieldNames.set(i, var10002 + "=" + invokeMethod(method, obj));
         } catch (Exception var8) {
            throw new EcpayException("物件缺少屬性");
         }

         result = result + (String)fieldNames.get(i) + "&";
      }

      return result + "CheckMacValue=" + CheckMacValue;
   }

   public static final Hashtable<String, String> objToHashtable(Object obj) {
      Class<?> cls = obj.getClass();
      Hashtable<String, String> resultDict = new Hashtable();
      List<String> fieldNames = getSortedFieldNames(cls);

      for(int i = 0; i < fieldNames.size(); ++i) {
         try {
            Method method = cls.getMethod("get" + (String)fieldNames.get(i), (Class[])null);
            resultDict.put((String)fieldNames.get(i), invokeMethod(method, obj));
         } catch (Exception var7) {
            throw new EcpayException("物件缺少屬性");
         }
      }

      return resultDict;
   }

   private static final String invokeMethod(Method method, Object obj) {
      try {
         return method.invoke(obj, (Object[])null).toString();
      } catch (Exception var3) {
         throw new EcpayException("物件缺少屬性");
      }
   }

   public static final String httpPost(String url, String urlParameters, String encoding) {
      try {
         URL obj = new URL(url);
         HttpURLConnection connection = null;
         if (obj.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            connection = (HttpsURLConnection)obj.openConnection();
         } else {
            connection = (HttpURLConnection)obj.openConnection();
         }

         ((HttpURLConnection)connection).setRequestMethod("POST");
         ((HttpURLConnection)connection).setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.2171.71 Safari/537.36 EcPay JAVA API Version 2.0.0");
         ((HttpURLConnection)connection).setRequestProperty("Accept-Language", encoding);
         ((HttpURLConnection)connection).setDoOutput(true);
         DataOutputStream wr = new DataOutputStream(((HttpURLConnection)connection).getOutputStream());
         wr.write(urlParameters.getBytes(encoding));
         wr.flush();
         wr.close();
         BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection)connection).getInputStream(), encoding));
         StringBuffer response = new StringBuffer();

         String inputLine;
         while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
         }

         in.close();
         return response.toString();
      } catch (Exception var9) {
         throw new EcpayException(var9.getMessage());
      }
   }

   public static void htmlunit(String url, String para, String encoding) throws Exception {
      URL obj = new URL(url);
      HttpURLConnection connection = null;
      if (obj.getProtocol().toLowerCase().equals("https")) {
         trustAllHosts();
         connection = (HttpsURLConnection)obj.openConnection();
      } else {
         connection = (HttpURLConnection)obj.openConnection();
      }

      ((HttpURLConnection)connection).setRequestMethod("POST");
      ((HttpURLConnection)connection).setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.2171.71 Safari/537.36 EcPay JAVA API Version 2.0.0");
      ((HttpURLConnection)connection).setRequestProperty("Accept-Language", encoding);
      ((HttpURLConnection)connection).setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(((HttpURLConnection)connection).getOutputStream());
      wr.write(para.getBytes(encoding));
      wr.flush();
      wr.close();
      BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection)connection).getInputStream(), encoding));
      StringBuffer response = new StringBuffer();

      String inputLine;
      while((inputLine = in.readLine()) != null) {
         response.append(inputLine);
      }

      boolean redirect = false;
      int status = ((HttpURLConnection)connection).getResponseCode();
      if (status != 200 && (status == 302 || status == 301 || status == 303)) {
         redirect = true;
      }

      String newUrl = "https://payment.ecpay.com.tw/PaymentRule/CVSPaymentInfo";
      String cookies = ((HttpURLConnection)connection).getHeaderField("Set-Cookie");
      HttpURLConnection connection = (HttpURLConnection)(new URL(newUrl)).openConnection();
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.2171.71 Safari/537.36 EcPay JAVA API Version 2.0.0");
      connection.setRequestProperty("Accept-Language", encoding);
      connection.setDoOutput(true);
      new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
      new StringBuffer();
      in.close();
   }

   public static final String genUnixTimeStamp() {
      Date date = new Date();
      Integer timeStamp = (int)(date.getTime() / 1000L);
      return timeStamp.toString();
   }

   public static final Document xmlParser(String uri) {
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(false);
         factory.setIgnoringElementContentWhitespace(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         return builder.parse(uri);
      } catch (Exception var3) {
         throw new Error(var3);
      }
   }

   private static void trustAllHosts() {
      X509TrustManager easyTrustManager = new X509TrustManager() {
         public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }

         public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }

         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }
      };
      TrustManager[] trustAllCerts = new TrustManager[]{easyTrustManager};

      try {
         SSLContext sc = SSLContext.getInstance("TLS");
         sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private static List<String> getSortedFieldNames(Class<?> cls) {
      Field[] fields = cls.getDeclaredFields();
      List<String> fieldNames = new ArrayList();
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         fieldNames.add(field.getName());
      }

      Collections.sort(fieldNames, String.CASE_INSENSITIVE_ORDER);
      return fieldNames;
   }

   public static String urlEncode(String data) {
      String result = "";

      try {
         result = URLEncoder.encode(data, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
      }

      return result;
   }

   private static String netUrlEncode(String url) {
      String netUrlEncode = url.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
      return netUrlEncode;
   }

   private static final String hash(byte[] data, String mode) {
      MessageDigest md = null;

      try {
         if (mode == "MD5") {
            md = MessageDigest.getInstance("MD5");
         } else if (mode == "SHA-256") {
            md = MessageDigest.getInstance("SHA-256");
         }
      } catch (NoSuchAlgorithmException var4) {
      }

      return bytesToHex(md.digest(data));
   }

   private static final String bytesToHex(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 15];
      }

      return new String(hexChars);
   }

   public static EcpayFunction.PaymentInfo htmlunit(String url, ArrayList<NameValuePair> para, String encoding, Object aio) throws Exception {
      WebClient webClient = new WebClient(BrowserVersion.CHROME);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setCssEnabled(false);
      webClient.getOptions().setRedirectEnabled(true);
      URL urlset = new URL(url);
      WebRequest requestSettings = new WebRequest(urlset, HttpMethod.POST);
      requestSettings.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.2171.71 Safari/537.36 EcPay JAVA API Version 2.0.0");
      requestSettings.setAdditionalHeader("Accept-Language", encoding);
      requestSettings.setRequestParameters(para);
      HtmlPage page = null;

      try {
         page = (HtmlPage)webClient.getPage(requestSettings);
      } catch (Exception var20) {
         System.out.println("Get page error" + var20);
      }

      DomNodeList<DomElement> elementsByTagName = page.getElementsByTagName("dd");
      String order_number = "";
      String merchant_name = "";
      String item_name = "";
      String item_price = "";
      String SubPayment = "";
      String payment_expiredate = "";
      String payment_No = "";
      String bank_code = "";

      for(int i = 0; i < elementsByTagName.getLength(); ++i) {
         DomElement domElement = (DomElement)elementsByTagName.get(i);
         String s = "";
         System.out.println(i + " : " + domElement.asText().replaceAll("\r|\n", ""));
         if (aio instanceof AioCheckOutATM) {
            s = domElement.asText().replaceAll("\r|\n", "");
            switch(i) {
            case 0:
               order_number = s;
               break;
            case 1:
               merchant_name = s;
               break;
            case 2:
               SubPayment = s;
            case 3:
            case 4:
            case 6:
            case 7:
            default:
               break;
            case 5:
               item_name = s;
               break;
            case 8:
               item_price = s;
               break;
            case 9:
               bank_code = s.substring(0, 8);
               payment_No = s.substring(8, 30);
               break;
            case 10:
               payment_expiredate = s.substring(0, 19);
            }
         } else if (aio instanceof AioCheckOutCVS) {
            s = domElement.asText().replaceAll("\r|\n", "");
            switch(i) {
            case 0:
               order_number = s;
               break;
            case 1:
               merchant_name = s;
               break;
            case 2:
               SubPayment = s;
            case 3:
            case 4:
            case 6:
            case 7:
            default:
               break;
            case 5:
               item_name = s;
               break;
            case 8:
               item_price = s;
               break;
            case 9:
               payment_No = s;
               break;
            case 10:
               payment_expiredate = s.substring(0, 19);
            }
         }
      }

      webClient.close();
      EcpayFunction.PaymentInfo payment = new EcpayFunction.PaymentInfo(order_number, merchant_name, item_name, item_price, SubPayment, payment_expiredate, payment_No, bank_code, "尚未繳款");
      return payment;
   }

   public static String handlePaymentCVS(int type, String text) {
      String ret = "";
      switch(type) {
      case 0:
         ret = text.replace("訂單編號", "").trim();
         break;
      case 1:
         ret = text.replace("商店名稱", "").trim();
         break;
      case 2:
         ret = text.replace("商品明細", "").trim();
         break;
      case 3:
         ret = text.replace("實際繳費金額", "").trim();
         break;
      case 4:
         ret = text.replace("付款方式", "").trim();
         break;
      case 5:
         ret = text.replace("繳費截止日期", "").trim();
         break;
      case 6:
         ret = text.replace("超商繳費代碼", "").trim();
      }

      return ret;
   }

   public static String handlePaymentATM(int type, String text) {
      String ret = "";
      switch(type) {
      case 0:
         ret = text.replace("訂單編號", "").trim();
         break;
      case 1:
         ret = text.replace("商店名稱", "").trim();
         break;
      case 2:
         ret = text.replace("商品明細", "").trim();
         break;
      case 3:
         ret = text.replace("訂單金額", "").trim();
         break;
      case 4:
         ret = text.replace("付款方式", "").trim();
         break;
      case 5:
         ret = text.replace("銀行代碼", "").trim();
         break;
      case 6:
         ret = text.replace("ATM繳費帳號", "").trim();
         break;
      case 7:
         ret = text.replace("繳費截止日期", "").trim();
      }

      return ret;
   }

   public static class PaymentInfo {
      private final String order_number;
      private final String merchant_name;
      private final String item_name;
      private final String item_price;
      private final String SubPayment;
      private final String payment_expiredate;
      private final String payment_No;
      private final String bank_code;
      private final String payment_status;

      public PaymentInfo(String order_number, String merchant_name, String item_name, String item_price, String SubPayment, String payment_expiredate, String payment_No, String bank_code, String payment_status) {
         this.order_number = order_number;
         this.merchant_name = merchant_name;
         this.item_name = item_name;
         this.item_price = item_price;
         this.SubPayment = SubPayment;
         this.payment_expiredate = payment_expiredate;
         this.payment_No = payment_No;
         this.bank_code = bank_code;
         this.payment_status = payment_status;
      }

      public String getOrderNumber() {
         return this.order_number;
      }

      public String getMerchantName() {
         return this.merchant_name;
      }

      public String getItemName() {
         return this.item_name;
      }

      public String getItemPrice() {
         return this.item_price;
      }

      public String getSubPayment() {
         return this.SubPayment;
      }

      public String getPaymentExpiryDate() {
         return this.payment_expiredate;
      }

      public String getPaymentNo() {
         return this.payment_No;
      }

      public String getPaymentStatus() {
         return this.payment_status;
      }

      public String getBankCode() {
         return this.bank_code;
      }
   }
}
