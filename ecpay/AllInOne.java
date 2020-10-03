package ecpay;

import com.gargoylesoftware.htmlunit.util.NameValuePair;
import constants.GameSetConstants;
import ecpay.payment.integration.domain.ATMRequestObj;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.AioCheckOutATM;
import ecpay.payment.integration.domain.AioCheckOutBARCODE;
import ecpay.payment.integration.domain.AioCheckOutCVS;
import ecpay.payment.integration.domain.AioCheckOutDevide;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.domain.AioCheckOutPeriod;
import ecpay.payment.integration.domain.AioCheckOutWebATM;
import ecpay.payment.integration.domain.CVSOrBARCODERequestObj;
import ecpay.payment.integration.domain.CaptureObj;
import ecpay.payment.integration.domain.CreateServerOrderObj;
import ecpay.payment.integration.domain.DoActionObj;
import ecpay.payment.integration.domain.FundingReconDetailObj;
import ecpay.payment.integration.domain.InvoiceObj;
import ecpay.payment.integration.domain.QueryCreditCardPeriodInfoObj;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import ecpay.payment.integration.domain.QueryTradeObj;
import ecpay.payment.integration.domain.TradeNoAioObj;
import ecpay.payment.integration.ecpayOperator.EcpayFunction;
import ecpay.payment.integration.exception.EcpayException;
import ecpay.payment.integration.verification.VerifyCapture;
import ecpay.payment.integration.verification.VerifyCreateServerOrder;
import ecpay.payment.integration.verification.VerifyDoAction;
import ecpay.payment.integration.verification.VerifyFundingReconDetail;
import ecpay.payment.integration.verification.VerifyQueryCreditTrade;
import ecpay.payment.integration.verification.VerifyQueryTrade;
import ecpay.payment.integration.verification.VerifyQueryTradeInfo;
import ecpay.payment.integration.verification.VerifyTradeNoAio;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.w3c.dom.Document;

public class AllInOne {
   protected static String operatingMode;
   protected static String mercProfile;
   protected static String isProjectContractor;
   protected static String HashKey;
   protected static String HashIV;
   protected static String MerchantID;
   protected static String PlatformID;
   protected static String aioCheckOutUrl;
   protected static String doActionUrl;
   protected static String queryCreditTradeUrl;
   protected static String queryTradeInfoUrl;
   protected static String captureUrl;
   protected static String queryTradeUrl;
   protected static String tradeNoAioUrl;
   protected static String fundingReconDetailUrl;
   protected static String createServerOrderUrl;
   protected static Document verifyDoc;
   protected static String[] ignorePayment;
   private static final Logger log = Logger.getLogger(AllInOne.class.getName());

   public static boolean compareCheckMacValue(Hashtable<String, String> params) {
      String checkMacValue = "";
      if (!params.containsKey("CheckMacValue")) {
         throw new EcpayException("此Hashtable並沒有CheckMacValue可比較");
      } else {
         try {
            checkMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, params);
         } catch (EcpayException var3) {
            throw new EcpayException("產生檢查碼失敗");
         }

         return checkMacValue.equals(params.get("CheckMacValue"));
      }
   }

   public String createServerOrder(CreateServerOrderObj obj) {
      obj.setPlatformID(PlatformID);
      if (!PlatformID.isEmpty() && obj.getMerchantID().isEmpty()) {
         obj.setMerchantID(MerchantID);
      } else if (PlatformID.isEmpty() || obj.getMerchantID().isEmpty()) {
         obj.setMerchantID(MerchantID);
      }

      log.info("createServerOrder params: " + obj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         obj.setPaymentToken(EcpayFunction.AESEncode(HashKey, HashIV, obj.getPaymentToken()));
         VerifyCreateServerOrder verify = new VerifyCreateServerOrder();
         createServerOrderUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(obj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)obj);
         log.info("createServerOrder generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(obj, CheckMacValue);
         log.info("createServerOrder post String: " + httpValue);
         result = EcpayFunction.httpPost(createServerOrderUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      } catch (Exception var7) {
         log.warning(var7.getMessage());
         throw new EcpayException(var7.getMessage());
      }
   }

   public String capture(CaptureObj captureObj) {
      captureObj.setPlatformID(PlatformID);
      if (!PlatformID.isEmpty() && captureObj.getMerchantID().isEmpty()) {
         captureObj.setMerchantID(MerchantID);
      } else if (PlatformID.isEmpty() || captureObj.getMerchantID().isEmpty()) {
         captureObj.setMerchantID(MerchantID);
      }

      log.info("capture params: " + captureObj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyCapture verify = new VerifyCapture();
         captureUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(captureObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)captureObj);
         log.info("capture generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(captureObj, CheckMacValue);
         log.info("capture post String: " + httpValue);
         result = EcpayFunction.httpPost(captureUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      }
   }

   public String fundingReconDetail(FundingReconDetailObj fundingReconDetailObj) {
      fundingReconDetailObj.setMerchantID(MerchantID);
      log.info("fundingReconDetail params: " + fundingReconDetailObj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyFundingReconDetail verify = new VerifyFundingReconDetail();
         fundingReconDetailUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(fundingReconDetailObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)fundingReconDetailObj);
         log.info("fundingReconDetail generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(fundingReconDetailObj, CheckMacValue);
         log.info("fundingReconDetail post String: " + httpValue);
         result = EcpayFunction.httpPost(fundingReconDetailUrl, httpValue, "BIG5");
         List<String> subRE = new ArrayList();
         Pattern pattern = Pattern.compile("\\d{8}\\,\\d{6}\\,\\d{5}");
         Matcher matcher = pattern.matcher(result);

         while(matcher.find()) {
            subRE.add(matcher.group());
         }

         pattern = Pattern.compile("\\,+\\u6bcf\\u65e5\\u5c0f\\u8a08");
         matcher = pattern.matcher(result);
         if (matcher.find()) {
            subRE.add(matcher.group());
         }

         pattern = Pattern.compile("\\,+\\u5408\\u8a08");
         matcher = pattern.matcher(result);
         if (matcher.find()) {
            subRE.add(matcher.group());
         }

         pattern = Pattern.compile("\\u6388\\u6b0a\\u55ae\\u865f");
         matcher = pattern.matcher(result);
         if (matcher.find()) {
            subRE.add(matcher.group());
         }

         String tmp;
         for(Iterator var9 = subRE.iterator(); var9.hasNext(); result = result.replace(tmp, "\r\n" + tmp)) {
            tmp = (String)var9.next();
         }

         result = result.substring(2);
         return result;
      } catch (EcpayException var11) {
         var11.ShowExceptionMessage();
         log.warning(var11.getNewExceptionMessage());
         throw new EcpayException(var11.getNewExceptionMessage());
      }
   }

   public String queryTrade(QueryTradeObj queryTradeObj) {
      queryTradeObj.setMerchantID(MerchantID);
      log.info("queryTrade params: " + queryTradeObj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyQueryTrade verify = new VerifyQueryTrade();
         queryTradeUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(queryTradeObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)queryTradeObj);
         log.info("queryTrade generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(queryTradeObj, CheckMacValue);
         log.info("queryTrade post String: " + httpValue);
         result = EcpayFunction.httpPost(queryTradeUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      }
   }

   public String tradeNoAio(TradeNoAioObj tradeNoAioObj) {
      tradeNoAioObj.setMerchantID(MerchantID);
      log.info("tradeNoAio params: " + tradeNoAioObj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyTradeNoAio verify = new VerifyTradeNoAio();
         tradeNoAioUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(tradeNoAioObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)tradeNoAioObj);
         log.info("tradeNoAio generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(tradeNoAioObj, CheckMacValue);
         log.info("tradeNoAio post String: " + httpValue);
         result = EcpayFunction.httpPost(tradeNoAioUrl, httpValue, "BIG5");
         List<String> subRE = new ArrayList();
         Pattern pattern;
         Matcher matcher;
         Iterator var9;
         String tmp;
         if (tradeNoAioObj.getMediaFormated().equals("0")) {
            pattern = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{16}");
            matcher = pattern.matcher(result);

            while(matcher.find()) {
               subRE.add(matcher.group());
            }

            for(var9 = subRE.iterator(); var9.hasNext(); result = result.replace(tmp, "\r\n" + tmp)) {
               tmp = (String)var9.next();
            }
         } else if (tradeNoAioObj.getMediaFormated().equals("1")) {
            result = result.replace("=", "");
            pattern = Pattern.compile("\"\\d{4}\\/\\d{2}\\/\\d{2} \\d{2}:\\d{2}:\\d{2}\"");
            matcher = pattern.matcher(result);

            while(matcher.find()) {
               subRE.add(matcher.group());
            }

            for(var9 = subRE.iterator(); var9.hasNext(); result = result.replace(tmp, "\r\n" + tmp)) {
               tmp = (String)var9.next();
            }
         }

         return result;
      } catch (EcpayException var11) {
         var11.ShowExceptionMessage();
         log.warning(var11.getNewExceptionMessage());
         throw new EcpayException(var11.getNewExceptionMessage());
      }
   }

   public String doAction(DoActionObj doActionObj) {
      doActionObj.setPlatformID(PlatformID);
      if (!PlatformID.isEmpty() && doActionObj.getMerchantID().isEmpty()) {
         doActionObj.setMerchantID(MerchantID);
      } else if (PlatformID.isEmpty() || doActionObj.getMerchantID().isEmpty()) {
         doActionObj.setMerchantID(MerchantID);
      }

      String result = "";
      String CheckMacValue = "";

      try {
         VerifyDoAction verify = new VerifyDoAction();
         doActionUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(doActionObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)doActionObj);
         log.info("doAction generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(doActionObj, CheckMacValue);
         log.info("doAction post String: " + httpValue);
         result = EcpayFunction.httpPost(doActionUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      }
   }

   public String queryTradeInfo(QueryTradeInfoObj queryTradeInfoObj) {
      queryTradeInfoObj.setPlatformID(PlatformID);
      if (!PlatformID.isEmpty() && queryTradeInfoObj.getMerchantID().isEmpty()) {
         queryTradeInfoObj.setMerchantID(MerchantID);
      } else if (PlatformID.isEmpty() || queryTradeInfoObj.getMerchantID().isEmpty()) {
         queryTradeInfoObj.setMerchantID(MerchantID);
      }

      queryTradeInfoObj.setTimeStamp(EcpayFunction.genUnixTimeStamp());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyQueryTradeInfo verify = new VerifyQueryTradeInfo();
         queryTradeInfoUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(queryTradeInfoObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)queryTradeInfoObj);
         log.info("queryTradeInfo generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(queryTradeInfoObj, CheckMacValue);
         log.info("queryTradeInfo post String: " + httpValue);
         result = EcpayFunction.httpPost(queryTradeInfoUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      }
   }

   public String queryCreditCardPeriodInfo(QueryCreditCardPeriodInfoObj queryCreditCardPeriodInfoObj) {
      queryCreditCardPeriodInfoObj.setMerchantID(MerchantID);
      queryCreditCardPeriodInfoObj.setTimeStamp(EcpayFunction.genUnixTimeStamp());
      log.info("queryCreditCardPeriodeInfo params: " + queryCreditCardPeriodInfoObj.toString());
      String result = "";
      String CheckMacValue = "";

      try {
         VerifyQueryCreditTrade verify = new VerifyQueryCreditTrade();
         queryCreditTradeUrl = verify.getAPIUrl(operatingMode);
         verify.verifyParams(queryCreditCardPeriodInfoObj);
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)queryCreditCardPeriodInfoObj);
         log.info("queryCreditCardPeriodInfo generate CheckMacValue: " + CheckMacValue);
         String httpValue = EcpayFunction.genHttpValue(queryCreditCardPeriodInfoObj, CheckMacValue);
         log.info("queryCreditCardPeriodInfo post String: " + httpValue);
         result = EcpayFunction.httpPost(queryCreditTradeUrl, httpValue, "UTF-8");
         return result;
      } catch (EcpayException var6) {
         var6.ShowExceptionMessage();
         log.warning(var6.getNewExceptionMessage());
         throw new EcpayException(var6.getNewExceptionMessage());
      }
   }

   public static EcpayFunction.PaymentInfo aioCheckOut(Object obj, InvoiceObj invoice, boolean hack) {
      new StringBuilder();
      String ignoreParam = "";
      EcpayFunction.PaymentInfo pay = null;
      String MERCHANTID = hack ? GameSetConstants.HACK_MERCHANT_ID : MerchantID;
      if (obj instanceof AioCheckOutALL) {
         ((AioCheckOutALL)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutALL)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutALL)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutALL)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutALL)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutALL)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         if (ignorePayment.length > 0) {
            ignoreParam = Arrays.toString(ignorePayment);
            ignoreParam = ignoreParam.replaceAll(", ", "#");
            ignoreParam = ignoreParam.substring(1, ignoreParam.length() - 1);
            ((AioCheckOutALL)obj).setIgnorePayment(ignoreParam);
         }

         log.info("aioCheckOutALL params: " + ((AioCheckOutALL)obj).toString());
      } else if (obj instanceof AioCheckOutATM) {
         ((AioCheckOutATM)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutATM)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutATM)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutATM)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutATM params: " + ((AioCheckOutATM)obj).toString());
      } else if (obj instanceof AioCheckOutBARCODE) {
         ((AioCheckOutBARCODE)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutBARCODE)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutBARCODE)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutBARCODE)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutBARCODE)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutBARCODE)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutBARCODE params: " + ((AioCheckOutBARCODE)obj).toString());
      } else if (obj instanceof AioCheckOutCVS) {
         ((AioCheckOutCVS)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutCVS)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutCVS)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutCVS)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutCVS)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutCVS)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutCVS params: " + ((AioCheckOutCVS)obj).toString());
      } else if (obj instanceof AioCheckOutDevide) {
         ((AioCheckOutDevide)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutDevide)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutDevide)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutDevide)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutDevide)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutDevide)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutDevide params: " + ((AioCheckOutDevide)obj).toString());
      } else if (obj instanceof AioCheckOutOneTime) {
         ((AioCheckOutOneTime)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutOneTime)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutOneTime)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutOneTime)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutOneTime)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutOneTime)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutOneTime params: " + ((AioCheckOutOneTime)obj).toString());
      } else if (obj instanceof AioCheckOutPeriod) {
         ((AioCheckOutPeriod)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutPeriod)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutPeriod)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutPeriod)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutPeriod)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutPeriod)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutPeriod params: " + ((AioCheckOutPeriod)obj).toString());
      } else {
         if (!(obj instanceof AioCheckOutWebATM)) {
            throw new EcpayException("傳入非定義的物件導致錯誤!");
         }

         ((AioCheckOutWebATM)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutWebATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutWebATM)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutWebATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutWebATM)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutWebATM)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutWebATM params: " + ((AioCheckOutWebATM)obj).toString());
      }

      try {
         if (invoice != null) {
            log.info("aioCheckOut invoice params: " + invoice.toString());
            invoice.setCustomerName(EcpayFunction.urlEncode(invoice.getCustomerName()));
            invoice.setCustomerAddr(EcpayFunction.urlEncode(invoice.getCustomerAddr()));
            invoice.setCustomerEmail(EcpayFunction.urlEncode(invoice.getCustomerEmail()));
            invoice.setInvoiceItemName(EcpayFunction.urlEncode(invoice.getInvoiceItemName()));
            invoice.setInvoiceItemWord(EcpayFunction.urlEncode(invoice.getInvoiceItemWord()));
            invoice.setInvoiceRemark(EcpayFunction.urlEncode(invoice.getInvoiceRemark()));
         }

         pay = genCheckOutHtmlParameter(obj, invoice, hack);
         return pay;
      } catch (EcpayException var8) {
         var8.ShowExceptionMessage();
         log.warning(var8.getNewExceptionMessage());
         throw new EcpayException(var8.getNewExceptionMessage());
      }
   }

   public static String aioCheckOutHtml(Object obj, InvoiceObj invoice, boolean hack) {
      new StringBuilder();
      String ignoreParam = "";
      EcpayFunction.PaymentInfo pay = null;
      String MERCHANTID = hack ? GameSetConstants.HACK_MERCHANT_ID : MerchantID;
      if (obj instanceof AioCheckOutALL) {
         ((AioCheckOutALL)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutALL)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutALL)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutALL)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutALL)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutALL)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         if (ignorePayment.length > 0) {
            ignoreParam = Arrays.toString(ignorePayment);
            ignoreParam = ignoreParam.replaceAll(", ", "#");
            ignoreParam = ignoreParam.substring(1, ignoreParam.length() - 1);
            ((AioCheckOutALL)obj).setIgnorePayment(ignoreParam);
         }

         log.info("aioCheckOutALL params: " + ((AioCheckOutALL)obj).toString());
      } else if (obj instanceof AioCheckOutATM) {
         ((AioCheckOutATM)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutATM)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutATM)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutATM)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutATM params: " + ((AioCheckOutATM)obj).toString());
      } else if (obj instanceof AioCheckOutBARCODE) {
         ((AioCheckOutBARCODE)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutBARCODE)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutBARCODE)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutBARCODE)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutBARCODE)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutBARCODE)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutBARCODE params: " + ((AioCheckOutBARCODE)obj).toString());
      } else if (obj instanceof AioCheckOutCVS) {
         ((AioCheckOutCVS)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutCVS)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutCVS)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutCVS)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutCVS)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutCVS)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutCVS params: " + ((AioCheckOutCVS)obj).toString());
      } else if (obj instanceof AioCheckOutDevide) {
         ((AioCheckOutDevide)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutDevide)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutDevide)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutDevide)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutDevide)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutDevide)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutDevide params: " + ((AioCheckOutDevide)obj).toString());
      } else if (obj instanceof AioCheckOutOneTime) {
         ((AioCheckOutOneTime)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutOneTime)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutOneTime)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutOneTime)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutOneTime)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutOneTime)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutOneTime params: " + ((AioCheckOutOneTime)obj).toString());
      } else if (obj instanceof AioCheckOutPeriod) {
         ((AioCheckOutPeriod)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutPeriod)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutPeriod)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutPeriod)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutPeriod)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutPeriod)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutPeriod params: " + ((AioCheckOutPeriod)obj).toString());
      } else {
         if (!(obj instanceof AioCheckOutWebATM)) {
            throw new EcpayException("傳入非定義的物件導致錯誤!");
         }

         ((AioCheckOutWebATM)obj).setPlatformID(PlatformID);
         if (!PlatformID.isEmpty() && ((AioCheckOutWebATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutWebATM)obj).setMerchantID(MERCHANTID);
         } else if (PlatformID.isEmpty() || ((AioCheckOutWebATM)obj).getMerchantID().isEmpty()) {
            ((AioCheckOutWebATM)obj).setMerchantID(MERCHANTID);
         }

         ((AioCheckOutWebATM)obj).setInvoiceMark(invoice == null ? "N" : "Y");
         log.info("aioCheckOutWebATM params: " + ((AioCheckOutWebATM)obj).toString());
      }

      try {
         if (invoice != null) {
            log.info("aioCheckOut invoice params: " + invoice.toString());
            invoice.setCustomerName(EcpayFunction.urlEncode(invoice.getCustomerName()));
            invoice.setCustomerAddr(EcpayFunction.urlEncode(invoice.getCustomerAddr()));
            invoice.setCustomerEmail(EcpayFunction.urlEncode(invoice.getCustomerEmail()));
            invoice.setInvoiceItemName(EcpayFunction.urlEncode(invoice.getInvoiceItemName()));
            invoice.setInvoiceItemWord(EcpayFunction.urlEncode(invoice.getInvoiceItemWord()));
            invoice.setInvoiceRemark(EcpayFunction.urlEncode(invoice.getInvoiceRemark()));
         }
      } catch (EcpayException var8) {
         var8.ShowExceptionMessage();
         log.warning(var8.getNewExceptionMessage());
         throw new EcpayException(var8.getNewExceptionMessage());
      }

      return genCheckOutHtmlCode(obj, invoice);
   }

   public Object aioCheckOutFeedback(HttpServletRequest req) {
      List<String> parameterNames = new ArrayList(req.getParameterMap().keySet());
      Iterator var4;
      String name;
      Method method;
      String checkMacValue;
      if (parameterNames.contains("BankCode")) {
         ATMRequestObj obj = new ATMRequestObj();
         var4 = parameterNames.iterator();

         while(var4.hasNext()) {
            name = (String)var4.next();

            try {
               method = obj.getClass().getMethod("set" + name, (Class[])null);
               method.invoke(obj, req.getParameter(name));
            } catch (Exception var8) {
               throw new EcpayException("物件缺少屬性");
            }
         }

         log.info("ATMRequest params: " + obj.toString());
         checkMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)obj);
         log.info("ATMRequest self generate CheckMacValue: " + checkMacValue + ", received CheckMacValue: " + obj.getCheckMacValue());
         if (!checkMacValue.equals(obj.getCheckMacValue())) {
            log.warning("檢查碼驗證錯誤!");
            throw new EcpayException("檢查碼驗證錯誤!");
         } else {
            return obj;
         }
      } else {
         CVSOrBARCODERequestObj obj = new CVSOrBARCODERequestObj();
         var4 = parameterNames.iterator();

         while(var4.hasNext()) {
            name = (String)var4.next();

            try {
               method = obj.getClass().getMethod("set" + name, (Class[])null);
               method.invoke(obj, req.getParameter(name));
            } catch (Exception var9) {
               throw new EcpayException("物件缺少屬性");
            }
         }

         log.info("CVSRequest params: " + obj.toString());
         checkMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, (Object)obj);
         log.info("CVSRequest self generate CheckMacValue: " + checkMacValue + ", received CheckMacValue: " + obj.getCheckMacValue());
         if (!checkMacValue.equals(obj.getCheckMacValue())) {
            log.warning("檢查碼驗證錯誤!");
            throw new EcpayException("檢查碼驗證錯誤!");
         } else {
            return obj;
         }
      }
   }

   private static String genCheckOutHtmlCode(Object aio, InvoiceObj invoice) {
      StringBuilder builder = new StringBuilder();
      Hashtable<String, String> fieldValue = EcpayFunction.objToHashtable(aio);
      new Hashtable();
      if (invoice != null) {
         Hashtable<String, String> invoiceField = EcpayFunction.objToHashtable(invoice);
         fieldValue.putAll(invoiceField);
      }

      String CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, fieldValue);
      fieldValue.put("CheckMacValue", CheckMacValue);
      Set<String> key = fieldValue.keySet();
      String[] name = (String[])key.toArray(new String[key.size()]);
      builder.append("<form id=\"allPayAPIForm\" action=\"https://payment.ecpay.com.tw/Cashier/AioCheckOut/V5\" method=\"post\">");

      for(int i = 0; i < name.length; ++i) {
         builder.append("<input type=\"hidden\" name=\"" + name[i] + "\" value=\"" + (String)fieldValue.get(name[i]) + "\">");
      }

      builder.append("<script language=\"JavaScript\">");
      builder.append("allPayAPIForm.submit()");
      builder.append("</script>");
      builder.append("</form>");
      String s = builder.toString();
      return s;
   }

   private static EcpayFunction.PaymentInfo genCheckOutHtmlParameter(Object aio, InvoiceObj invoice, boolean hack) {
      Hashtable<String, String> fieldValue = EcpayFunction.objToHashtable(aio);
      new Hashtable();
      if (invoice != null) {
         Hashtable<String, String> invoiceField = EcpayFunction.objToHashtable(invoice);
         fieldValue.putAll(invoiceField);
      }

      String CheckMacValue;
      if (!hack) {
         CheckMacValue = EcpayFunction.genCheckMacValue(HashKey, HashIV, fieldValue);
      } else {
         CheckMacValue = EcpayFunction.genCheckMacValue(GameSetConstants.HACK_HASHKEY, GameSetConstants.HACK_HASHIV, fieldValue);
      }

      fieldValue.put("CheckMacValue", CheckMacValue);
      Set<String> key = fieldValue.keySet();
      String[] name = (String[])key.toArray(new String[key.size()]);
      ArrayList<NameValuePair> val = new ArrayList();

      for(int i = 0; i < name.length; ++i) {
         val.add(new NameValuePair(name[i], (String)fieldValue.get(name[i])));
      }

      EcpayFunction.PaymentInfo payment = null;

      try {
         payment = EcpayFunction.htmlunit("https://payment.ecpay.com.tw/Cashier/AioCheckOut/V5", val, "UTF-8", aio);
      } catch (Exception var11) {
         Logger.getLogger(AllInOne.class.getName()).log(Level.SEVERE, (String)null, var11);
      }

      return payment;
   }

   static {
      MerchantID = EcpayPayment.MerchantID;
      HashKey = EcpayPayment.HashKey;
      HashIV = EcpayPayment.HashIV;
      PlatformID = "";
   }
}
