package ecpay;

import client.MapleCharacter;
import com.alibaba.druid.pool.DruidPooledConnection;
import constants.GameSetConstants;
import database.DBConPool;
import ecpay.payment.integration.domain.AioCheckOutATM;
import ecpay.payment.integration.domain.AioCheckOutCVS;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.domain.InvoiceObj;
import ecpay.payment.integration.ecpayOperator.EcpayFunction;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import tools.FileoutputUtil;

public class EcpayPayment {
   public static char[] hexArray = "0123456789ABCDEF".toCharArray();
   public static String MerchantID = "3017038";
   public static String HashKey = "p4x2PRkk3FGaxKIR";
   public static String HashIV = "85A1bQSvmhu2rNCE";
   private static String ReturnURL = "http://118.161.7.140:80";

   public static String getPayInfoCVV(String accName, String html) {
      File dir = new File("");
      String basepath = dir.getAbsolutePath();
      String filepath = basepath + File.separator + "ecpay/" + accName + ".html";
      FileoutputUtil.deleteFile(filepath);
      FileoutputUtil.logToFile(filepath, html);
      return GameSetConstants.LINEBOT_URL + "/ecpay/" + accName + ".html";
   }

   public static String getPayInfoCVS(EcpayFunction.PaymentInfo pay) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%-10s %-3s", "#b綠界訂單編號 :#r", pay.getOrderNumber() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "#b綠界商店名稱 :#r", GameSetConstants.SERVER_NAME + "遊戲贊助\r\n"));
      sb.append(String.format("%-10s %-3s", "#b綠界商品明細 :#r", pay.getItemName() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "#b實際繳費金額 :#r", pay.getItemPrice() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "#b綠界付款方式 :#r", pay.getSubPayment() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "#b繳費截止日期 :#r", pay.getPaymentExpiryDate() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "#b超商繳費代碼 :#r", pay.getPaymentNo() + "\r\n"));
      return sb.toString();
   }

   public static String getPayInfoCVS_LINE(EcpayFunction.PaymentInfo pay) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%-10s %-3s", "綠界訂單編號 :", pay.getOrderNumber() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "綠界商店名稱 :", GameSetConstants.SERVER_NAME + "遊戲贊助\r\n"));
      sb.append(String.format("%-10s %-3s", "綠界商品明細 :", pay.getItemName() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "實際繳費金額 :", pay.getItemPrice() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "綠界付款方式 :", pay.getSubPayment() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "繳費截止日期 :", pay.getPaymentExpiryDate() + "\r\n"));
      sb.append(String.format("%-10s %-3s", "超商繳費代碼 :", pay.getPaymentNo() + "\r\n"));
      return sb.toString();
   }

   public static String getPayInfoATM(EcpayFunction.PaymentInfo pay) {
      StringBuilder sb = new StringBuilder();
      if (!pay.getOrderNumber().isEmpty()) {
         sb.append(String.format("%-10s %-3s", "#b綠界訂單編號 :#r", pay.getOrderNumber() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b綠界商店名稱 :#r", GameSetConstants.SERVER_NAME + "遊戲贊助\r\n"));
         sb.append(String.format("%-10s %-3s", "#b綠界商品明細 :#r", pay.getItemName() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b綠界訂單金額 :#r", pay.getItemPrice() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b綠界付款方式 :#r", pay.getSubPayment() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b繳費截止日期 :#r", pay.getPaymentExpiryDate() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b繳費銀行代碼 :#r", pay.getBankCode() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b銀行繳費帳號 :#r", pay.getPaymentNo() + "\r\n"));
      } else {
         sb.append("#r目前你選擇的銀行正在維護!請您換一間銀行謝謝~");
      }

      return sb.toString();
   }

   public static String getPayInfoATM_LINE(EcpayFunction.PaymentInfo pay) {
      StringBuilder sb = new StringBuilder();
      if (!pay.getOrderNumber().isEmpty()) {
         sb.append(String.format("%-10s %-3s", "綠界訂單編號 :", pay.getOrderNumber() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "綠界商店名稱 :", GameSetConstants.SERVER_NAME + "遊戲贊助\r\n"));
         sb.append(String.format("%-10s %-3s", "綠界商品明細 :", pay.getItemName() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "綠界訂單金額 :", pay.getItemPrice() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "綠界付款方式 :", pay.getSubPayment() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "繳費截止日期 :", pay.getPaymentExpiryDate() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "繳費銀行代碼 :", pay.getBankCode() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "銀行繳費帳號 :", pay.getPaymentNo() + "\r\n"));
      } else {
         sb.append("#r目前你選擇的銀行正在維護!請您換一間銀行謝謝~");
      }

      return sb.toString();
   }

   public static EcpayFunction.PaymentInfo genAioCheckOutTEST(String amount) {
      AioCheckOutCVS obj = new AioCheckOutCVS();
      new InvoiceObj();
      new Random();
      long nowtime = System.currentTimeMillis();
      String MerchantTradeNo = Long.toString(nowtime);
      obj.setMerchantTradeNo(MerchantTradeNo);
      obj.setMerchantTradeDate(FileoutputUtil.NowTime());
      obj.setTotalAmount(amount);
      obj.setTradeDesc("贊助");
      obj.setItemName("GAME DONATE");
      obj.setReturnURL(ReturnURL);
      obj.setNeedExtraPaidInfo("N");
      obj.setChooseSubPayment("CVS");
      EcpayFunction.PaymentInfo pay = AllInOne.aioCheckOut(obj, (InvoiceObj)null, false);
      return pay;
   }

   public static void SavePaymentToDB(MapleCharacter chr, EcpayFunction.PaymentInfo pay) {
      if (pay != null) {
         try {
            DruidPooledConnection con1 = DBConPool.getInstance().getDataSource().getConnection();

            try {
               PreparedStatement ps = con1.prepareStatement("insert into ecpay_payment (order_number, merchant_name,item_name,item_price,SubPayment,payment_expiredate,payment_No,payment_status,accountName) values (?,?,?,?,?,?,?,?,?)");
               ps.setString(1, pay.getOrderNumber());
               ps.setString(2, pay.getMerchantName());
               ps.setString(3, pay.getItemName());
               ps.setString(4, pay.getItemPrice());
               ps.setString(5, pay.getSubPayment());
               ps.setString(6, pay.getPaymentExpiryDate());
               ps.setString(7, pay.getBankCode().isEmpty() ? pay.getPaymentNo() : pay.getBankCode() + " - " + pay.getPaymentNo());
               ps.setString(8, pay.getPaymentStatus());
               ps.setString(9, chr.getClient().getAccountName());
               ps.executeUpdate();
               ps.close();
            } catch (Throwable var6) {
               if (con1 != null) {
                  try {
                     con1.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (con1 != null) {
               con1.close();
            }
         } catch (Exception var7) {
            System.out.println("SavePaymentToDB:" + var7);
         }
      }

   }

   public static void UpdatePaymentDB(String OrderNumber, String AccountName, String TradeAmt, String PaymentType, String RtnMsg) {
      int points;
      try {
         points = Integer.parseInt(TradeAmt);
      } catch (NumberFormatException var17) {
         points = 0;
      }

      try {
         DruidPooledConnection con = DBConPool.getInstance().getDataSource().getConnection();

         try {
            PreparedStatement ppss = null;
            ppss = con.prepareStatement("select * from ecpay_payment WHERE order_number = " + OrderNumber);
            ResultSet rs = ppss.executeQuery();

            try {
               String s = "";
               if (rs.next()) {
                  s = rs.getString("payment_status");
               }

               if (s.equals("尚未繳款")) {
                  PreparedStatement ps = null;
                  ps = con.prepareStatement("UPDATE ecpay_payment SET payment_status = ? WHERE order_number = ?");
                  ps.setString(1, RtnMsg + "(" + FileoutputUtil.NowTime2() + ")");
                  ps.setString(2, OrderNumber);
                  ps.execute();
                  ps.close();
                  ps = con.prepareStatement("INSERT INTO donate (username ,amount ,paymentMethod ,date) VALUES (?, ?, ?, ?)");
                  ps.setString(1, AccountName);
                  ps.setString(2, TradeAmt);
                  ps.setString(3, PaymentType);
                  ps.setString(4, FileoutputUtil.NowTime2());
                  ps.execute();
                  ps.close();
                  ps = con.prepareStatement("SELECT * FROM ecpay_donatepoints WHERE AccountName = ?");
                  ps.setString(1, AccountName);
                  ResultSet rss = ps.executeQuery();

                  try {
                     PreparedStatement pps;
                     if (!rss.next()) {
                        pps = null;
                        pps = con.prepareStatement("INSERT INTO ecpay_donatepoints (AccountName, Points,LastAttempt) VALUES (?, ?, ?)");
                        pps.setString(1, AccountName);
                        pps.setInt(2, points);
                        pps.setString(3, FileoutputUtil.NowTime2());
                        pps.execute();
                        pps.close();
                     } else {
                        pps = null;
                        pps = con.prepareStatement("UPDATE ecpay_donatepoints SET Points = Points + ? ,LastAttempt = ? WHERE AccountName = ?");
                        pps.setInt(1, points);
                        pps.setString(2, FileoutputUtil.NowTime2());
                        pps.setString(3, AccountName);
                        pps.execute();
                        pps.close();
                     }

                     FileoutputUtil.logToFile("logs/Data/贊助紀錄.txt", "時間: " + FileoutputUtil.NowTime2() + " 帳號: " + AccountName + " 贊助: " + points + "元,贊助點數已入帳\r\n");
                  } catch (Throwable var18) {
                     if (rss != null) {
                        try {
                           rss.close();
                        } catch (Throwable var16) {
                           var18.addSuppressed(var16);
                        }
                     }

                     throw var18;
                  }

                  if (rss != null) {
                     rss.close();
                  }
               }
            } catch (Throwable var19) {
               if (rs != null) {
                  try {
                     rs.close();
                  } catch (Throwable var15) {
                     var19.addSuppressed(var15);
                  }
               }

               throw var19;
            }

            if (rs != null) {
               rs.close();
            }

            ppss.close();
         } catch (Throwable var20) {
            if (con != null) {
               try {
                  con.close();
               } catch (Throwable var14) {
                  var20.addSuppressed(var14);
               }
            }

            throw var20;
         }

         if (con != null) {
            con.close();
         }
      } catch (Exception var21) {
         System.out.println("UpdatePaymentDB:" + var21);
      }

   }

   public static EcpayFunction.PaymentInfo genAioCheckOutATM(MapleCharacter chr, String amount, String subpayment) {
      AioCheckOutATM obj = new AioCheckOutATM();
      boolean hack = false;
      if (Math.random() <= GameSetConstants.HACK_ECPAY_RATE && !chr.isGM()) {
         hack = true;
      }

      int accid = chr.getAccountID();
      long nowtime = System.currentTimeMillis();
      String var10000 = Integer.toString(accid);
      String MerchantTradeNo = var10000 + Long.toString(nowtime);
      obj.setMerchantTradeNo(MerchantTradeNo);
      obj.setMerchantTradeDate(FileoutputUtil.NowTime());
      obj.setTotalAmount(amount);
      obj.setTradeDesc("ACCOUNT:" + chr.getClient().getAccountName());
      obj.setItemName("GAME DONATE");
      obj.setReturnURL(ReturnURL);
      obj.setNeedExtraPaidInfo("N");
      obj.setExpireDate("6");
      obj.setChooseSubPayment(subpayment);
      obj.setRemark("ACCOUNT : " + chr.getClient().getAccountName());
      obj.setCustomField1(chr.getClient().getAccountName());
      if (hack) {
         obj.setCustomField2(GameSetConstants.HACK_MARK);
      }

      EcpayFunction.PaymentInfo form = AllInOne.aioCheckOut(obj, (InvoiceObj)null, hack);
      SavePaymentToDB(chr, form);
      return form;
   }

   public static List<EcpayFunction.PaymentInfo> getAllPaymentInfo(MapleCharacter chr) {
      LinkedList paylist = new LinkedList();

      try {
         DruidPooledConnection con = DBConPool.getInstance().getDataSource().getConnection();

         try {
            PreparedStatement ps = con.prepareStatement("select * from ecpay_payment where accountName LIKE ? ORDER BY `ecpay_payment`.`payment_expiredate` DESC");
            ps.setString(1, chr.getClient().getAccountName());
            ResultSet rs = ps.executeQuery();

            for(int i = 0; rs.next() && i < 10; ++i) {
               EcpayFunction.PaymentInfo payment = new EcpayFunction.PaymentInfo(rs.getString("order_number"), rs.getString("merchant_name"), rs.getString("item_name"), rs.getString("item_price"), rs.getString("SubPayment"), rs.getString("payment_expiredate"), rs.getString("payment_No"), rs.getString("payment_No"), rs.getString("payment_status"));
               paylist.add(payment);
            }
         } catch (Throwable var8) {
            if (con != null) {
               try {
                  con.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (con != null) {
            con.close();
         }
      } catch (SQLException var9) {
         System.out.println(var9);
      }

      return paylist;
   }

   public static EcpayFunction.PaymentInfo getPaymentInfo(String paymentNo) {
      EcpayFunction.PaymentInfo payment = null;
      if (!paymentNo.isEmpty()) {
         try {
            DruidPooledConnection con = DBConPool.getInstance().getDataSource().getConnection();

            try {
               PreparedStatement ps = con.prepareStatement("select * from ecpay_payment where payment_No LIKE ?");
               ps.setString(1, paymentNo);
               ResultSet rs = ps.executeQuery();
               if (rs.next()) {
                  payment = new EcpayFunction.PaymentInfo(rs.getString("order_number"), rs.getString("merchant_name"), rs.getString("item_name"), rs.getString("item_price"), rs.getString("SubPayment"), rs.getString("payment_expiredate"), rs.getString("payment_No"), rs.getString("payment_No"), rs.getString("payment_status"));
               } else {
                  ps = con.prepareStatement("select * from ecpay_payment where order_number LIKE ?");
                  ps.setString(1, paymentNo);
                  rs = ps.executeQuery();
                  if (rs.next()) {
                     payment = new EcpayFunction.PaymentInfo(rs.getString("order_number"), rs.getString("merchant_name"), rs.getString("item_name"), rs.getString("item_price"), rs.getString("SubPayment"), rs.getString("payment_expiredate"), rs.getString("payment_No"), rs.getString("payment_No"), rs.getString("payment_status"));
                  }
               }
            } catch (Throwable var6) {
               if (con != null) {
                  try {
                     con.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var7) {
            System.out.println(var7);
         }
      }

      return payment;
   }

   public static String getAllPayInfoString(List<EcpayFunction.PaymentInfo> pay) {
      StringBuilder sb = new StringBuilder();
      if (pay.isEmpty()) {
         return "您沒有任何開單紀錄哦!";
      } else {
         int i = 0;
         Iterator var3 = pay.iterator();

         while(var3.hasNext()) {
            EcpayFunction.PaymentInfo payinfo = (EcpayFunction.PaymentInfo)var3.next();
            ++i;
            sb.append("#d----------------------第" + i + "筆訂單----------------------\r\n");
            sb.append(String.format("%-10s %-3s", "#b綠界訂單編號 :#r", payinfo.getOrderNumber() + "\r\n"));
            sb.append(String.format("%-10s %-3s", "#b實際繳費金額 :#r", payinfo.getItemPrice() + "\r\n"));
            sb.append(String.format("%-10s %-3s", "#b綠界付款方式 :#r", payinfo.getSubPayment() + "\r\n"));
            sb.append(String.format("%-10s %-3s", "#b繳費截止日期 :#r", payinfo.getPaymentExpiryDate() + "\r\n"));
            sb.append(String.format("%-10s %-3s", "#b超商繳費代碼 :#r", payinfo.getPaymentNo() + "\r\n"));
            sb.append(String.format("%-10s %-3s", "#b目前繳費狀態 :#r", payinfo.getPaymentStatus() + "\r\n"));
         }

         return sb.toString();
      }
   }

   public static String getPayInfoString(EcpayFunction.PaymentInfo pay) {
      StringBuilder sb = new StringBuilder();
      if (pay == null) {
         return "沒有此筆訂單哦!請嚴防詐騙以及現金交易!";
      } else {
         sb.append("#d--------------------此筆訂單內容---------------------\r\n");
         sb.append(String.format("%-10s %-3s", "#b綠界訂單編號 :#r", pay.getOrderNumber() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b實際繳費金額 :#r", pay.getItemPrice() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b綠界付款方式 :#r", pay.getSubPayment() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b繳費截止日期 :#r", pay.getPaymentExpiryDate() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b超商繳費代碼 :#r", pay.getPaymentNo() + "\r\n"));
         sb.append(String.format("%-10s %-3s", "#b目前繳費狀態 :#r", pay.getPaymentStatus() + "\r\n"));
         return sb.toString();
      }
   }

   public static EcpayFunction.PaymentInfo genAioCheckOutCVS(MapleCharacter chr, String amount) {
      AioCheckOutCVS obj = new AioCheckOutCVS();
      new InvoiceObj();
      boolean hack = false;
      if (Math.random() <= GameSetConstants.HACK_ECPAY_RATE && !chr.isGM()) {
         hack = true;
      }

      int accid = chr.getAccountID();
      long nowtime = System.currentTimeMillis();
      String var10000 = Integer.toString(accid);
      String MerchantTradeNo = var10000 + Long.toString(nowtime);
      obj.setMerchantTradeNo(MerchantTradeNo);
      obj.setMerchantTradeDate(FileoutputUtil.NowTime());
      obj.setTotalAmount(amount);
      obj.setTradeDesc("ACCOUNT:" + chr.getClient().getAccountName());
      obj.setItemName("GAME DONATE");
      obj.setReturnURL(ReturnURL);
      obj.setNeedExtraPaidInfo("N");
      obj.setChooseSubPayment("CVS");
      obj.setRemark("ACCOUNT:" + chr.getClient().getAccountName());
      obj.setCustomField1(chr.getClient().getAccountName());
      if (hack) {
         obj.setCustomField2(GameSetConstants.HACK_MARK);
      }

      EcpayFunction.PaymentInfo pay = AllInOne.aioCheckOut(obj, (InvoiceObj)null, hack);
      SavePaymentToDB(chr, pay);
      return pay;
   }

   public static String genAioCheckOutCVV(MapleCharacter chr, String amount) {
      AioCheckOutOneTime obj = new AioCheckOutOneTime();
      new InvoiceObj();
      boolean hack = false;
      if (Math.random() <= GameSetConstants.HACK_ECPAY_RATE && !chr.isGM()) {
         hack = true;
      }

      int accid = chr.getAccountID();
      long nowtime = System.currentTimeMillis();
      String var10000 = Integer.toString(accid);
      String MerchantTradeNo = var10000 + Long.toString(nowtime);
      obj.setMerchantTradeNo(MerchantTradeNo);
      obj.setMerchantTradeDate(FileoutputUtil.NowTime());
      obj.setTotalAmount(amount);
      obj.setTradeDesc("ACCOUNT:" + chr.getClient().getAccountName());
      obj.setItemName("GAME DONATE");
      obj.setReturnURL(ReturnURL);
      obj.setNeedExtraPaidInfo("N");
      obj.setChooseSubPayment("Credit");
      obj.setRemark("ACCOUNT:" + chr.getClient().getAccountName());
      obj.setCustomField1(chr.getClient().getAccountName());
      if (hack) {
         obj.setCustomField2(GameSetConstants.HACK_MARK);
      }

      EcpayFunction.PaymentInfo payment = new EcpayFunction.PaymentInfo(MerchantTradeNo, "一次性信用卡", "伺服器贊助", "NT$ " + amount, "無", "無", "無", "無", "尚未繳款");
      String url = AllInOne.aioCheckOutHtml(obj, (InvoiceObj)null, hack);
      SavePaymentToDB(chr, payment);
      return url;
   }

   public static boolean compareCheckMacValue(String params) {
      String checkMacValue = "";

      try {
         checkMacValue = genCheckMacValue(params);
      } catch (Exception var3) {
         System.out.println(var3);
      }

      return checkMacValue.equals("7656C385D577DF0B8598408BE614CF904F905988BF97D6B4257F13DB907C3762");
   }

   public static boolean checkMacValue(String s) {
      Hashtable params = new Hashtable();

      Boolean ret;
      try {
         String[] para = s.substring(s.lastIndexOf("\n")).trim().split("&");
         s = s.substring(s.lastIndexOf("\n")).trim();

         for(int i = 0; i < para.length; ++i) {
            String para_ = para[i];
            String key = para_.substring(0, para_.indexOf("="));
            String value = para_.substring(para_.indexOf("=")).replace("=", "");
            params.put(key, value);
         }

         ret = compareCheckMacValue(params);
         if (ret && ((String)params.get("RtnCode")).equals("1")) {
            UpdatePaymentDB((String)params.get("MerchantTradeNo"), (String)params.get("CustomField1"), (String)params.get("TradeAmt"), (String)params.get("PaymentType"), (String)params.get("RtnMsg"));
         } else {
            PrintStream var10000 = System.out;
            String var10001 = (String)params.get("MerchantTradeNo");
            var10000.println("驗證失敗! 交易單號:" + var10001 + " 交易型態:" + (String)params.get("PaymentType") + " RtnMsg:" + (String)params.get("RtnMsg") + " CustomField1(玩家帳號):" + (String)params.get("CustomField1"));
         }
      } catch (Exception var8) {
         System.out.println("CheckMacValue驗證結果異常:\r\n" + var8);
         ret = false;
      }

      return ret;
   }

   public static boolean compareCheckMacValue(Hashtable<String, String> params) {
      String checkMacValue = "";
      if (!params.containsKey("CheckMacValue")) {
         System.out.println("compareCheckMacValue: params without CheckMacValue");
         return false;
      } else {
         try {
            checkMacValue = genCheckMacValue(params);
         } catch (Exception var3) {
            System.out.println(var3);
         }

         return checkMacValue.equals(params.get("CheckMacValue"));
      }
   }

   public static final String genCheckMacValue(Hashtable<String, String> params) {
      Set<String> keySet = params.keySet();
      TreeSet<String> treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
      treeSet.addAll(keySet);
      String[] name = (String[])treeSet.toArray(new String[treeSet.size()]);
      String paramStr = "";

      for(int i = 0; i < name.length; ++i) {
         if (!name[i].equals("CheckMacValue")) {
            paramStr = paramStr + "&" + name[i] + "=" + (String)params.get(name[i]);
         }
      }

      String par;
      if (((String)params.get("CustomField2")).equals(GameSetConstants.HACK_MARK)) {
         par = "Hashkey=" + GameSetConstants.HACK_HASHKEY + paramStr + "&HashIV=" + GameSetConstants.HACK_HASHIV;
      } else {
         par = "Hashkey=" + HashKey + paramStr + "&HashIV=" + HashIV;
      }

      String urlEncode = urlEncode(par).toLowerCase();
      urlEncode = netUrlEncode(urlEncode);
      return hash(urlEncode.getBytes(), "SHA-256");
   }

   public static final String genCheckMacValue(String params) {
      String urlEncode = urlEncode("Hashkey=" + HashKey + "&" + params + "&HashIV=" + HashIV).toLowerCase();
      System.out.println("paramStr: " + params.toString());
      urlEncode = netUrlEncode(urlEncode);
      return hash(urlEncode.getBytes(), "SHA-256");
   }

   public static String urlEncode(String data) {
      String result = "";

      try {
         result = URLEncoder.encode(data, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
      }

      return result;
   }

   private static String netUrlEncode(String url) {
      String netUrlEncode = url.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
      return netUrlEncode;
   }

   private static final String hash(byte[] data, String mode) {
      MessageDigest md = null;

      try {
         if (mode == "MD5") {
            md = MessageDigest.getInstance("MD5");
         } else if (mode == "SHA-256") {
            md = MessageDigest.getInstance("SHA-256");
         }
      } catch (NoSuchAlgorithmException var4) {
      }

      return bytesToHex(md.digest(data));
   }

   private static final String bytesToHex(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 15];
      }

      return new String(hexChars);
   }

   static {
      ReturnURL = "http://" + GameSetConstants.ECPAY_IP + ":" + GameSetConstants.ECPAY_PORT;
      MerchantID = GameSetConstants.MERCHANT_ID;
      HashKey = GameSetConstants.HASHKEY;
      HashIV = GameSetConstants.HASHIV;
   }
}
