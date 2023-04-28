import java.net.Socket;
import java.io.*;
public class Client {
    

    Socket socket;

    BufferedReader br;
    PrintWriter out;
    public Client() {

        try {
            System.out.println("Sending request to server..");
            socket = new Socket("122.161.65.68",7777);
            System.out.println("Connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReaing();
            startWriting();

        } catch(Exception e){


        }
    }
    public void startReaing() 
    {
           // thread - read karke deta rahega
        Runnable r1=()->{
              System.out.println("Reader started...");
try{
              while(true){
               String msg;
            
                msg = br.readLine();
                if(msg.equals("exit")){
                 System.out.println("Server terminated the chat");
                 socket.close();
                 break;
                }
                System.out.println("Server : " + msg);
            }
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("connection is closed");
            }
          
            
        };
        new Thread(r1).start();
    }
    public void startWriting() 
    {
            // thread - data ko user sai leta rahega send karega 
           Runnable r2=()->{
            System.out.println("Writer Started..");

            try {
              while( !socket.isClosed()){

                
                      
                  BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                  String content = br1.readLine();

                  out.println(content);
                  out.flush();
                  
                  if(content.equals("exit")){
                    socket.close();
                    break;
                  }
               
              }
              System.out.println("Connection closed");
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("connection closed");
            }
           };
           new Thread(r2).start();
    }

    public static void main(String[] args) {
        
        System.out.println("this is client....");
        new Client();
    }
}
