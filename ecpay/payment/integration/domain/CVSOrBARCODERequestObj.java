package ecpay.payment.integration.domain;

public class CVSOrBARCODERequestObj {
   private String MerchantID;
   private String MerchantTradeNo;
   private String RtnCode;
   private String RtnMsg;
   private String TradeNo;
   private String TradeAmt;
   private String PaymentType;
   private String PaymentTypeChargeFee;
   private String TradeDate;
   private String SimulatePaid;
   private String CheckMacValue;
   private String PaymentNo;
   private String ExpireDate;
   private String Barcode1;
   private String Barcode2;
   private String Barcode3;
   private String StoreID;
   private String CustomField1;
   private String CustomField2;
   private String CustomField3;
   private String CustomField4;

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

   public String getRtnCode() {
      return this.RtnCode;
   }

   public void setRtnCode(String rtnCode) {
      this.RtnCode = rtnCode;
   }

   public String getRtnMsg() {
      return this.RtnMsg;
   }

   public void setRtnMsg(String rtnMsg) {
      this.RtnMsg = rtnMsg;
   }

   public String getTradeNo() {
      return this.TradeNo;
   }

   public void setTradeNo(String tradeNo) {
      this.TradeNo = tradeNo;
   }

   public String getTradeAmt() {
      return this.TradeAmt;
   }

   public void setTradeAmt(String tradeAmt) {
      this.TradeAmt = tradeAmt;
   }

   public String getPaymentType() {
      return this.PaymentType;
   }

   public void setPaymentType(String paymentType) {
      this.PaymentType = paymentType;
   }

   public String getTradeDate() {
      return this.TradeDate;
   }

   public void setTradeDate(String tradeDate) {
      this.TradeDate = tradeDate;
   }

   public String getCheckMacValue() {
      return this.CheckMacValue;
   }

   public void setCheckMacValue(String checkMacValue) {
      this.CheckMacValue = checkMacValue;
   }

   public String getPaymentNo() {
      return this.PaymentNo;
   }

   public void setPaymentNo(String paymentNo) {
      this.PaymentNo = paymentNo;
   }

   public String getExpireDate() {
      return this.ExpireDate;
   }

   public void setExpireDate(String expireDate) {
      this.ExpireDate = expireDate;
   }

   public String getBarcode1() {
      return this.Barcode1;
   }

   public void setBarcode1(String barcode1) {
      this.Barcode1 = barcode1;
   }

   public String getBarcode2() {
      return this.Barcode2;
   }

   public void setBarcode2(String barcode2) {
      this.Barcode2 = barcode2;
   }

   public String getBarcode3() {
      return this.Barcode3;
   }

   public void setBarcode3(String barcode3) {
      this.Barcode3 = barcode3;
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

   public String getPaymentTypeChargeFee() {
      return this.PaymentTypeChargeFee;
   }

   public void setPaymentTypeChargeFee(String paymentTypeChargeFee) {
      this.PaymentTypeChargeFee = paymentTypeChargeFee;
   }

   public String getSimulatePaid() {
      return this.SimulatePaid;
   }

   public void setSimulatePaid(String simulatePaid) {
      this.SimulatePaid = simulatePaid;
   }

   public String toString() {
      return "CVSOrBARCODERequestObj [MerchantID=" + this.MerchantID + ", MerchantTradeNo=" + this.MerchantTradeNo + ", RtnCode=" + this.RtnCode + ", RtnMsg=" + this.RtnMsg + ", TradeNo=" + this.TradeNo + ", TradeAmt=" + this.TradeAmt + ", PaymentType=" + this.PaymentType + ", PaymentTypeChargeFee=" + this.PaymentTypeChargeFee + ", TradeDate=" + this.TradeDate + ", SimulatePaid=" + this.SimulatePaid + ", CheckMacValue=" + this.CheckMacValue + ", PaymentNo=" + this.PaymentNo + ", ExpireDate=" + this.ExpireDate + ", Barcode1=" + this.Barcode1 + ", Barcode2=" + this.Barcode2 + ", Barcode3=" + this.Barcode3 + ", StoreID=" + this.StoreID + ", CustomField1=" + this.CustomField1 + ", CustomField2=" + this.CustomField2 + ", CustomField3=" + this.CustomField3 + ", CustomField4=" + this.CustomField4 + "]";
   }
}
