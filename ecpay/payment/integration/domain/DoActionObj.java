package ecpay.payment.integration.domain;

public class DoActionObj {
   private String MerchantID = "";
   private String MerchantTradeNo = "";
   private String TradeNo = "";
   private String Action = "";
   private String TotalAmount = "";
   private String PlatformID = "";

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

   public String getTradeNo() {
      return this.TradeNo;
   }

   public void setTradeNo(String tradeNo) {
      this.TradeNo = tradeNo;
   }

   public String getAction() {
      return this.Action;
   }

   public void setAction(String action) {
      this.Action = action;
   }

   public String getTotalAmount() {
      return this.TotalAmount;
   }

   public void setTotalAmount(String totalAmount) {
      this.TotalAmount = totalAmount;
   }

   public String getPlatformID() {
      return this.PlatformID;
   }

   public void setPlatformID(String platformID) {
      this.PlatformID = platformID;
   }

   public String toString() {
      return "DoActionObj [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", TradeNo=" + this.TradeNo + ", Action=" + this.Action + ", TotalAmount=" + this.TotalAmount + ", PlatformID=" + this.PlatformID + "]";
   }
}
