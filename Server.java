import java.io.*;
import java.net.*;

public class Server{
    

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // Constructor...
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReaing();
            startWriting();

              
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void startReaing() 
    {
           // thread - read karke deta rahega
        Runnable r1=()->{
              System.out.println("Reading started...");
             try{
              while(true){
               String msg;
            
                msg = br.readLine();
                if(msg.equals("exit")){
                 System.out.println("Client terminated the chat");
                 socket.close();
                 break;
                }
                System.out.println("Client : " + msg);

             
            
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        };
        new Thread(r1).start();
    }

 /**
     * 
     */
  
    public void startWriting() 
    {
            // thread - data ko user sai leta rahega send karega 
           Runnable r2=()->{
            System.out.println("Writer Started..");
              try {
            while(true && !socket.isClosed()){
                 
                
                      
                  BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                  String content = br1.readLine();
                  out.println(content);
                  out.flush();
                  if(content.equals("exit")){
                    socket.close();
                    break;
                  }

                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
           };
           new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server...going to start server");
        new Server(); 

    }
}
