package ecpay.payment.integration.domain;

public class FundingReconDetailObj {
   private String MerchantID = "";
   private String PayDateType = "";
   private String StartDate = "";
   private String EndDate = "";

   public String getMerchantID() {
      return this.MerchantID;
   }

   public void setMerchantID(String merchantID) {
      this.MerchantID = merchantID;
   }

   public String getPayDateType() {
      return this.PayDateType;
   }

   public void setPayDateType(String payDateType) {
      this.PayDateType = payDateType;
   }

   public String getStartDate() {
      return this.StartDate;
   }

   public void setStartDate(String startDate) {
      this.StartDate = startDate;
   }

   public String getEndDate() {
      return this.EndDate;
   }

   public void setEndDate(String endDate) {
      this.EndDate = endDate;
   }

   public String toString() {
      return "FundingReconDetailObj [MerchantID=" + this.MerchantID + ", PayDateType=" + this.PayDateType + ", StartDate=" + this.StartDate + ", EndDate=" + this.EndDate + "]";
   }
}
