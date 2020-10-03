package ecpay.payment.integration.domain;

public class CaptureObj {
   private String MerchantID = "";
   private String MerchantTradeNo = "";
   private String CaptureAMT = "";
   private String UserRefundAMT = "0";
   private String PlatformID = "";
   private String Remark = "";

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

   public String getCaptureAMT() {
      return this.CaptureAMT;
   }

   public void setCaptureAMT(String captureAMT) {
      this.CaptureAMT = captureAMT;
   }

   public String getUserRefundAMT() {
      return this.UserRefundAMT;
   }

   public String getPlatformID() {
      return this.PlatformID;
   }

   public void setPlatformID(String platformID) {
      this.PlatformID = platformID;
   }

   public String getRemark() {
      return this.Remark;
   }

   public void setRemark(String remark) {
      this.Remark = remark;
   }

   public String toString() {
      return "CaptureObj [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", CaptureAMT=" + this.CaptureAMT + ", UserRefundAMT=" + this.UserRefundAMT + ", PlatformID=" + this.PlatformID + ", Remark=" + this.Remark + "]";
   }
}
