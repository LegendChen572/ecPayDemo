package ecpay.payment.integration.verification;

import ecpay.payment.integration.domain.FundingReconDetailObj;
import ecpay.payment.integration.ecpayOperator.PaymentVerifyBase;
import ecpay.payment.integration.exception.EcpayException;
import java.lang.reflect.Method;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VerifyFundingReconDetail extends PaymentVerifyBase {
   public String getAPIUrl(String mode) {
      Element ele = (Element)this.doc.getElementsByTagName("FundingReconDetail").item(0);
      String url = "";
      NodeList nodeList = ele.getElementsByTagName("url");

      for(int i = 0; i < nodeList.getLength(); ++i) {
         ele = (Element)nodeList.item(i);
         if (ele.getAttribute("type").equalsIgnoreCase(mode)) {
            url = ele.getTextContent();
            break;
         }
      }

      if (url == "") {
         throw new EcpayException("payment_conf設定擋OperatingMode設定錯誤");
      } else {
         return url;
      }
   }

   public void verifyParams(FundingReconDetailObj obj) {
      Class<?> cls = obj.getClass();
      Element ele = (Element)this.doc.getElementsByTagName("FundingReconDetail").item(0);
      NodeList nodeList = ele.getElementsByTagName("param");

      for(int i = 0; i < nodeList.getLength(); ++i) {
         Element tmpEle = (Element)nodeList.item(i);

         String objValue;
         try {
            Method method = cls.getMethod("get" + tmpEle.getAttribute("name"), (Class[])null);
            objValue = method.invoke(obj, (Object[])null).toString();
         } catch (Exception var10) {
            throw new EcpayException("物件缺少屬性");
         }

         this.requireCheck(tmpEle.getAttribute("name"), objValue, tmpEle.getAttribute("require").toString());
         this.valueCheck(tmpEle.getAttribute("type"), objValue, tmpEle);
      }

   }
}
