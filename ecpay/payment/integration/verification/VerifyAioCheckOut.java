package ecpay.payment.integration.verification;

import ecpay.payment.integration.domain.InvoiceObj;
import ecpay.payment.integration.ecpayOperator.PaymentVerifyBase;
import ecpay.payment.integration.exception.EcpayException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VerifyAioCheckOut extends PaymentVerifyBase {
   public String getAPIUrl(String mode) throws EcpayException {
      Element ele = (Element)this.doc.getElementsByTagName("AioCheckOut").item(0);
      String url = "";
      NodeList nodeList = ele.getElementsByTagName("url");

      for(int i = 0; i < nodeList.getLength(); ++i) {
         ele = (Element)nodeList.item(i);
         if (ele.getAttribute("type").equalsIgnoreCase(mode)) {
            url = ele.getTextContent();
            System.out.println(url);
            break;
         }
      }

      if (url == "") {
         throw new EcpayException("payment_conf設定擋OperatingMode設定錯誤");
      } else {
         return url;
      }
   }

   public void verifyParams(Object obj) {
      Class<?> cls = obj.getClass();
      List<String> fieldNames = new ArrayList();
      Field[] var6 = cls.getDeclaredFields();
      int var7 = var6.length;

      int i;
      for(i = 0; i < var7; ++i) {
         Field field = var6[i];
         fieldNames.add(field.getName());
      }

      Element ele = (Element)this.doc.getElementsByTagName("AioCheckOut").item(0);
      NodeList nodeList = ele.getElementsByTagName("param");

      for(i = 0; i < nodeList.getLength(); ++i) {
         Element tmpEle = (Element)nodeList.item(i);
         if (fieldNames.contains(tmpEle.getAttribute("name"))) {
            String objValue;
            try {
               Method method = cls.getMethod("get" + tmpEle.getAttribute("name"), (Class[])null);
               objValue = method.invoke(obj, (Object[])null).toString();
            } catch (Exception var11) {
               throw new EcpayException("物件缺少屬性");
            }

            if (!(obj instanceof InvoiceObj)) {
               this.requireCheck(tmpEle.getAttribute("name"), objValue, tmpEle.getAttribute("require").toString());
            }

            this.valueCheck(tmpEle.getAttribute("type"), objValue, tmpEle);
         }
      }

   }

   public void verifyInvoice(InvoiceObj obj) {
      if (obj.getCarruerType().equals("1")) {
         if (obj.getCustomerID().isEmpty()) {
            throw new EcpayException("CustomerID cannot be empty when CarruerType is 1.");
         }
      } else if (!obj.getCustomerID().isEmpty() && obj.getCarruerType().isEmpty()) {
         throw new EcpayException("CarruerType cannot be empty when CustomerID is not empty.");
      }

      if (obj.getPrint().equals("1")) {
         if (obj.getCustomerName().isEmpty() || obj.getCustomerAddr().isEmpty()) {
            throw new EcpayException("CustomerName and CustomerAddr cannot be empty when Print is 1.");
         }

         if (!obj.getCustomerID().isEmpty()) {
            throw new EcpayException("Print cannot be 1 when CustomerID is not empty.");
         }
      }

      if (obj.getCustomerPhone().isEmpty() && obj.getCustomerEmail().isEmpty()) {
         throw new EcpayException("CustomerPhone and CustomerEmail cannot both be empty.");
      } else if (obj.getTaxType().equals("2") && !obj.getClearanceMark().equals("1") && !obj.getClearanceMark().equals("2")) {
         throw new EcpayException("ClearanceMark has to be 1 or 2 when TaxType is 2.");
      } else {
         if (!obj.getCustomerIdentifier().isEmpty()) {
            label210: {
               if (!obj.getCarruerType().equals("1") && !obj.getCarruerType().equals("2")) {
                  if (obj.getDonation().equals("2") && obj.getPrint().equals("1")) {
                     break label210;
                  }

                  throw new EcpayException("Print must be 1 and Donation must be 2 when CustomerIdentifier is given.");
               }

               throw new EcpayException("CarruerType cannot be 1 or 2 when CustomerIdentifier is given");
            }
         }

         if (!obj.getCarruerType().isEmpty() && obj.getPrint().equals("1")) {
            throw new EcpayException("Print must be 0 when CarruerType is given.");
         } else {
            if (!obj.getCarruerType().isEmpty() && !obj.getCarruerType().equals("1")) {
               Pattern r;
               Matcher m;
               if (obj.getCarruerType().equals("2")) {
                  r = Pattern.compile("[A-Za-z]{2}[0-9]{14}");
                  m = r.matcher(obj.getCarruerNum());
                  if (!m.find()) {
                     throw new EcpayException("CarruerNum must be 2 alphabets and 14 numbers when CarruerType is 2.");
                  }
               } else {
                  if (!obj.getCarruerType().equals("3")) {
                     throw new EcpayException("Unexpected Value in CarruerType");
                  }

                  r = Pattern.compile("^\\/[A-Za-z0-9\\s+-]{7}$");
                  m = r.matcher(obj.getCarruerNum());
                  if (!m.find()) {
                     throw new EcpayException("CarruerNum must start with / followed by 7 alphabet and number characters when CarruerType is 3.");
                  }
               }
            } else if (!obj.getCarruerNum().isEmpty()) {
               throw new EcpayException("CarruerNum must be empty when CarruerType is empty or 1.");
            }

            if (obj.getDonation().equals("1")) {
               if (obj.getLoveCode().isEmpty()) {
                  throw new EcpayException("LoveCode cannot be empty when Donation is 1.");
               }

               if (!obj.getPrint().equals("0")) {
                  throw new EcpayException("Print must be 0 when Donation is 1.");
               }
            }

            if (obj.getInvoiceItemName().isEmpty()) {
               throw new EcpayException("InvoiceItemName cannot be empty.");
            } else if (obj.getInvoiceItemCount().isEmpty()) {
               throw new EcpayException("InvoiceItemCount cannot be empty.");
            } else if (obj.getInvoiceItemWord().isEmpty()) {
               throw new EcpayException("InvoiceItemWord cannot be empty.");
            } else if (obj.getInvoiceItemPrice().isEmpty()) {
               throw new EcpayException("InvoiceItemPrice cannot be empty.");
            } else if (obj.getInvoiceItemTaxType().isEmpty()) {
               throw new EcpayException("InvoiceItemTaxType cannot be empty.");
            } else {
               if (obj.getInvoiceItemName().contains("|")) {
                  int itemCount = obj.getInvoiceItemName().split("|").length;
                  int paramCount = false;
                  Pattern r = Pattern.compile("(\\|\\||^\\||\\|$)");
                  Matcher invCount = r.matcher(obj.getInvoiceItemCount());
                  Matcher invWord = r.matcher(obj.getInvoiceItemWord());
                  Matcher invPrice = r.matcher(obj.getInvoiceItemPrice());
                  Matcher invType = r.matcher(obj.getInvoiceItemTaxType());
                  if (invCount.find()) {
                     throw new EcpayException("InvoiceItemCount contains empty value.");
                  }

                  int paramCount = obj.getInvoiceItemCount().split("|").length;
                  if (itemCount != paramCount) {
                     throw new EcpayException("Count of item info InvoiceItemCount(" + paramCount + ") not match item count from InvoiceItemName(" + itemCount + ")");
                  }

                  if (invWord.find()) {
                     throw new EcpayException("InvoiceItemWord contains empty value.");
                  }

                  paramCount = obj.getInvoiceItemWord().split("|").length;
                  if (itemCount != paramCount) {
                     throw new EcpayException("Count of item info InvoiceItemWord(" + paramCount + ") not match item count from InvoiceItemName(" + itemCount + ")");
                  }

                  if (invPrice.find()) {
                     throw new EcpayException("InvoiceItemPrice contains empty value.");
                  }

                  paramCount = obj.getInvoiceItemPrice().split("|").length;
                  if (itemCount != paramCount) {
                     throw new EcpayException("Count of item info InvoiceItemPrice(" + paramCount + ") not match item count from InvoiceItemName(" + itemCount + ")");
                  }

                  if (invType.find()) {
                     throw new EcpayException("InvoiceItemTaxType contains empty value.");
                  }

                  paramCount = obj.getInvoiceItemTaxType().split("|").length;
                  if (itemCount != paramCount) {
                     throw new EcpayException("Count of item info InvoiceItemTaxType(" + paramCount + ") not match item count from InvoiceItemName(" + itemCount + ")");
                  }

                  String[] itemTax = obj.getInvoiceItemTaxType().split("|");
                  String[] var10 = itemTax;
                  int var11 = itemTax.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String tax = var10[var12];
                     if (!tax.equals("1") && !tax.equals("2") && !tax.equals("3")) {
                        throw new EcpayException("Ilegal InvoiceItemTaxType: " + tax);
                     }
                  }

                  if (obj.getTaxType().equals("9")) {
                     if (!itemTax.toString().contains("1")) {
                        throw new EcpayException("InvoiceItemTaxType must contain at least one 1.");
                     }

                     if (itemTax.toString().contains("2") && itemTax.toString().contains("3")) {
                        throw new EcpayException("InvoiceItemTaxType cannot contain 2 and 3 at the same time.");
                     }
                  }
               } else {
                  if (obj.getInvoiceItemCount().contains("|")) {
                     throw new EcpayException("Item info InvoiceItemCount contains pipeline delimiter but there's only one item in param InvoiceItemName.");
                  }

                  if (obj.getInvoiceItemWord().contains("|")) {
                     throw new EcpayException("Item info InvoiceItemWord contains pipeline delimiter but there's only one item in param InvoiceItemName.");
                  }

                  if (obj.getInvoiceItemPrice().contains("|")) {
                     throw new EcpayException("Item info InvoiceItemPrice contains pipeline delimiter but there's only one item in param InvoiceItemName.");
                  }

                  if (obj.getInvoiceItemTaxType().contains("|")) {
                     throw new EcpayException("Item info InvoiceItemTaxType contains pipeline delimiter but there's only one item in param InvoiceItemName.");
                  }
               }

               this.verifyParams(obj);
            }
         }
      }
   }
}
