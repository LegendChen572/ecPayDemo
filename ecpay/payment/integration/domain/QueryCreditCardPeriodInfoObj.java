package ecpay.payment.integration.domain;

public class QueryCreditCardPeriodInfoObj {
   private String MerchantID = "";
   private String MerchantTradeNo = "";
   private String TimeStamp = "";

   public String getMerchantID() {
      return this.MerchantID;
   }

   public void setMerchantID(String merchantID) {
      this.MerchantID = merchantID;
   }

   public String getMerchantTradeNo() {
      return this.MerchantTradeNo;
   }

   public void setMerchantTradeNo(String merchantTradeNo) {
      this.MerchantTradeNo = merchantTradeNo;
   }

   public String getTimeStamp() {
      return this.TimeStamp;
   }

   public void setTimeStamp(String timeStamp) {
      this.TimeStamp = timeStamp;
   }

   public String toString() {
      return "QueryCreditCardPeriodInfoObj [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", TimeStamp=" + this.TimeStamp + "]";
   }
}
