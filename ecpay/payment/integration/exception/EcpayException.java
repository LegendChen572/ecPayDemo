package ecpay.payment.integration.exception;

public class EcpayException extends Error {
   private static final long serialVersionUID = 1L;
   String NewExceptionMessage;

   public EcpayException(String s) {
      this.NewExceptionMessage = s;
   }

   public String getNewExceptionMessage() {
      return this.NewExceptionMessage;
   }

   public void setNewExceptionMessage(String newExceptionMessage) {
      this.NewExceptionMessage = newExceptionMessage;
   }

   public void ShowExceptionMessage() {
      System.out.println("發生錯誤: " + this.getNewExceptionMessage());
   }
}
