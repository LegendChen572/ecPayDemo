package ecpay.payment.integration.domain;

public class CreateServerOrderObj {
   private String MerchantID = "";
   private String MerchantTradeNo = "";
   private String MerchantTradeDate = "";
   private String TotalAmount = "";
   private String CurrencyCode = "";
   private String ItemName = "";
   private String PlatformID = "";
   private String TradeDesc = "";
   private String TradeType = "";
   private String PaymentToken = "";

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

   public String getMerchantTradeDate() {
      return this.MerchantTradeDate;
   }

   public void setMerchantTradeDate(String merchantTradeDate) {
      this.MerchantTradeDate = merchantTradeDate;
   }

   public String getTotalAmount() {
      return this.TotalAmount;
   }

   public void setTotalAmount(String totalAmount) {
      this.TotalAmount = totalAmount;
   }

   public String getCurrencyCode() {
      return this.CurrencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.CurrencyCode = currencyCode;
   }

   public String getItemName() {
      return this.ItemName;
   }

   public void setItemName(String itemName) {
      this.ItemName = itemName;
   }

   public String getPlatformID() {
      return this.PlatformID;
   }

   public void setPlatformID(String platformID) {
      this.PlatformID = platformID;
   }

   public String getTradeDesc() {
      return this.TradeDesc;
   }

   public void setTradeDesc(String tradeDesc) {
      this.TradeDesc = tradeDesc;
   }

   public String getTradeType() {
      return this.TradeType;
   }

   public void setTradeType(String tradeType) {
      this.TradeType = tradeType;
   }

   public String getPaymentToken() {
      return this.PaymentToken;
   }

   public void setPaymentToken(String paymentToken) {
      this.PaymentToken = paymentToken;
   }

   public String toString() {
      return "CreateServerOrderObj [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", MerchantTradeDate=" + this.MerchantTradeDate + ", TotalAmount=" + this.TotalAmount + ", CurrencyCode=" + this.CurrencyCode + ", ItemName=" + this.ItemName + ", PlatformID=" + this.PlatformID + ", TradeDesc=" + this.TradeDesc + ", TradeType=" + this.TradeType + ", PaymentToken=" + this.PaymentToken + "]";
   }
}
