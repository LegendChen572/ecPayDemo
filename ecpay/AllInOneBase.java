package ecpay;

import org.w3c.dom.Document;

public class AllInOneBase {
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

   public AllInOneBase() {
      MerchantID = EcpayPayment.MerchantID;
      HashKey = EcpayPayment.HashKey;
      HashIV = EcpayPayment.HashIV;
      PlatformID = "";
   }
}
