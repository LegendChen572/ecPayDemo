package ecpay.payment.integration.domain;

public class TradeNoAioObj {
   private String MerchantID = "";
   private String DateType = "";
   private String BeginDate = "";
   private String EndDate = "";
   private String PaymentType = "";
   private String PlatformStatus = "";
   private String PaymentStatus = "";
   private String AllocateStatus = "";
   private String MediaFormated = "";

   public String getMerchantID() {
      return this.MerchantID;
   }

   public void setMerchantID(String merchantID) {
      this.MerchantID = merchantID;
   }

   public String getDateType() {
      return this.DateType;
   }

   public void setDateType(String dateType) {
      this.DateType = dateType;
   }

   public String getBeginDate() {
      return this.BeginDate;
   }

   public void setBeginDate(String beginDate) {
      this.BeginDate = beginDate;
   }

   public String getEndDate() {
      return this.EndDate;
   }

   public void setEndDate(String endDate) {
      this.EndDate = endDate;
   }

   public String getPaymentType() {
      return this.PaymentType;
   }

   public void setPaymentType(String paymentType) {
      this.PaymentType = paymentType;
   }

   public String getPlatformStatus() {
      return this.PlatformStatus;
   }

   public void setPlatformStatus(String platformStatus) {
      this.PlatformStatus = platformStatus;
   }

   public String getPaymentStatus() {
      return this.PaymentStatus;
   }

   public void setPaymentStatus(String paymentStatus) {
      this.PaymentStatus = paymentStatus;
   }

   public String getAllocateStatus() {
      return this.AllocateStatus;
   }

   public void setAllocateStatus(String allocateStatus) {
      this.AllocateStatus = allocateStatus;
   }

   public String getMediaFormated() {
      return this.MediaFormated;
   }

   public void setMediaFormated(String mediaFormated) {
      this.MediaFormated = mediaFormated;
   }

   public String toString() {
      return "TradeNoAioObj [MerchantID=" + this.MerchantID + ", DateType=" + this.DateType + ", BeginDate=" + this.BeginDate + ", EndDate=" + this.EndDate + ", PaymentType=" + this.PaymentType + ", PlatformStatus=" + this.PlatformStatus + ", PaymentStatus=" + this.PaymentStatus + ", AllocateStatus=" + this.AllocateStatus + ", MediaFormated=" + this.MediaFormated + "]";
   }
}
