package ecpay.payment.integration.domain;

public class AioCheckOutGooglePay {
   private String MerchantID = "";
   private String MerchantTradeNo = "";
   private String MerchantTradeDate = "";
   private String PaymentType = "aio";
   private String TotalAmount = "";
   private String TradeDesc = "";
   private String ItemName = "";
   private String ReturnURL = "";
   private String ChoosePayment = "GooglePay";
   private String ClientBackURL = "";
   private String ItemURL = "";
   private String Remark = "";
   private String ChooseSubPayment = "";
   private String OrderResultURL = "";
   private String NeedExtraPaidInfo = "";
   private String DeviceSource = "M";
   private String IgnorePayment = "";
   private String PlatformID = "";
   private String InvoiceMark = "";
   private String HoldTradeAMT = "";
   private String EncryptType = "1";
   private String Redeem = "";
   private String UnionPay = "";
   private String Language = "";
   private String StoreID = "";
   private String CustomField1 = "";
   private String CustomField2 = "";
   private String CustomField3 = "";
   private String CustomField4 = "";
   private String BidingCard = "";
   private String MerchantMemberID = "";

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

   public String getPaymentType() {
      return this.PaymentType;
   }

   public String getTotalAmount() {
      return this.TotalAmount;
   }

   public void setTotalAmount(String totalAmount) {
      this.TotalAmount = totalAmount;
   }

   public String getTradeDesc() {
      return this.TradeDesc;
   }

   public void setTradeDesc(String tradeDesc) {
      this.TradeDesc = tradeDesc;
   }

   public String getItemName() {
      return this.ItemName;
   }

   public void setItemName(String itemName) {
      this.ItemName = itemName;
   }

   public String getReturnURL() {
      return this.ReturnURL;
   }

   public void setReturnURL(String returnURL) {
      this.ReturnURL = returnURL;
   }

   public String getChoosePayment() {
      return this.ChoosePayment;
   }

   public String getClientBackURL() {
      return this.ClientBackURL;
   }

   public void setClientBackURL(String clientBackURL) {
      this.ClientBackURL = clientBackURL;
   }

   public String getItemURL() {
      return this.ItemURL;
   }

   public void setItemURL(String itemURL) {
      this.ItemURL = itemURL;
   }

   public String getRemark() {
      return this.Remark;
   }

   public void setRemark(String remark) {
      this.Remark = remark;
   }

   public String getChooseSubPayment() {
      return this.ChooseSubPayment;
   }

   public void setChooseSubPayment(String chooseSubPayment) {
      this.ChooseSubPayment = chooseSubPayment;
   }

   public String getOrderResultURL() {
      return this.OrderResultURL;
   }

   public void setOrderResultURL(String orderResultURL) {
      this.OrderResultURL = orderResultURL;
   }

   public String getNeedExtraPaidInfo() {
      return this.NeedExtraPaidInfo;
   }

   public void setNeedExtraPaidInfo(String needExtraPaidInfo) {
      this.NeedExtraPaidInfo = needExtraPaidInfo;
   }

   public String getDeviceSource() {
      return this.DeviceSource;
   }

   public void setDeviceSource(String deviceSource) {
      this.DeviceSource = deviceSource;
   }

   public String getIgnorePayment() {
      return this.IgnorePayment;
   }

   public void setIgnorePayment(String ignorePayment) {
      this.IgnorePayment = ignorePayment;
   }

   public String getPlatformID() {
      return this.PlatformID;
   }

   public void setPlatformID(String platformID) {
      this.PlatformID = platformID;
   }

   public String getInvoiceMark() {
      return this.InvoiceMark;
   }

   public void setInvoiceMark(String invoiceMark) {
      this.InvoiceMark = invoiceMark;
   }

   public String getHoldTradeAMT() {
      return this.HoldTradeAMT;
   }

   public void setHoldTradeAMT(String holdTradeAMT) {
      this.HoldTradeAMT = holdTradeAMT;
   }

   public String getEncryptType() {
      return this.EncryptType;
   }

   public String getRedeem() {
      return this.Redeem;
   }

   public void setRedeem(String redeem) {
      this.Redeem = redeem;
   }

   public String getUnionPay() {
      return this.UnionPay;
   }

   public void setUnionPay(String unionPay) {
      this.UnionPay = unionPay;
   }

   public String getLanguage() {
      return this.Language;
   }

   public void setLanguage(String language) {
      this.Language = language;
   }

   public String getStoreID() {
      return this.StoreID;
   }

   public void setStoreID(String storeID) {
      this.StoreID = storeID;
   }

   public String getCustomField1() {
      return this.CustomField1;
   }

   public void setCustomField1(String customField1) {
      this.CustomField1 = customField1;
   }

   public String getCustomField2() {
      return this.CustomField2;
   }

   public void setCustomField2(String customField2) {
      this.CustomField2 = customField2;
   }

   public String getCustomField3() {
      return this.CustomField3;
   }

   public void setCustomField3(String customField3) {
      this.CustomField3 = customField3;
   }

   public String getCustomField4() {
      return this.CustomField4;
   }

   public void setCustomField4(String customField4) {
      this.CustomField4 = customField4;
   }

   public String getBidingCard() {
      return this.BidingCard;
   }

   public void setBidingCard(String bidingCard) {
      this.BidingCard = bidingCard;
   }

   public String getMerchantMemberID() {
      return this.MerchantMemberID;
   }

   public void setMerchantMemberID(String merchantMemberID) {
      this.MerchantMemberID = merchantMemberID;
   }

   public String toString() {
      return "AioCheckOutGooglePay [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", MerchantTradeDate=" + this.MerchantTradeDate + ", PaymentType=" + this.PaymentType + ", TotalAmount=" + this.TotalAmount + ", TradeDesc=" + this.TradeDesc + ", ItemName=" + this.ItemName + ", ReturnURL=" + this.ReturnURL + ", ChoosePayment=" + this.ChoosePayment + ", ClientBackURL=" + this.ClientBackURL + ", ItemURL=" + this.ItemURL + ", Remark=" + this.Remark + ", ChooseSubPayment=" + this.ChooseSubPayment + ", OrderResultURL=" + this.OrderResultURL + ", NeedExtraPaidInfo=" + this.NeedExtraPaidInfo + ", DeviceSource=" + this.DeviceSource + ", IgnorePayment=" + this.IgnorePayment + ", PlatformID=" + this.PlatformID + ", InvoiceMark=" + this.InvoiceMark + ", HoldTradeAMT=" + this.HoldTradeAMT + ", EncryptType=" + this.EncryptType + ", Redeem=" + this.Redeem + ", UnionPay=" + this.UnionPay + ", Language=" + this.Language + ", StoreID=" + this.StoreID + ", CustomField1=" + this.CustomField1 + ", CustomField2=" + this.CustomField2 + ", CustomField3=" + this.CustomField3 + ", CustomField4=" + this.CustomField4 + ", BidingCard=" + this.BidingCard + ", MerchantMemberID=" + this.MerchantMemberID + "]";
   }
}
