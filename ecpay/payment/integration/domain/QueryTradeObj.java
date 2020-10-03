package ecpay.payment.integration.domain;

public class QueryTradeObj {
   private String MerchantID = "";
   private String CreditRefundId = "";
   private String CreditAmount = "";
   private String CreditCheckCode = "";

   public String getMerchantID() {
      return this.MerchantID;
   }

   public void setMerchantID(String merchantID) {
      this.MerchantID = merchantID;
   }

   public String getCreditRefundId() {
      return this.CreditRefundId;
   }

   public void setCreditRefundId(String creditRefundId) {
      this.CreditRefundId = creditRefundId;
   }

   public String getCreditAmount() {
      return this.CreditAmount;
   }

   public void setCreditAmount(String creditAmount) {
      this.CreditAmount = creditAmount;
   }

   public String getCreditCheckCode() {
      return this.CreditCheckCode;
   }

   public void setCreditCheckCode(String creditCheckCode) {
      this.CreditCheckCode = creditCheckCode;
   }

   public String toString() {
      return "QureyTradeObj [MerchantID=" + this.MerchantID + ", CreditRefundId=" + this.CreditRefundId + ", CreditAmount=" + this.CreditAmount + ", CreditCheckCode=" + this.CreditCheckCode + "]";
   }
}
