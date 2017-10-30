import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

public class Server {

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            
            public void run() {
                try {
                	Friends friends = new Friends();
                	Users users= new Users();
                	Messages messages = new Messages();
                	Files files = new Files();
                    ServerSocket serverSocket = new ServerSocket(4789);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(friends,messages,users,files,clientSocket));
                    }
                } catch (IOException e) {
                    
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;
        private Friends friends;
        private Messages messages;
        private Users users;
        private Files files;
        private ClientTask(Friends friends,Messages messages,Users users,Files files,Socket clientSocket) {
        	this.friends=friends;
        	this.users=users;
        	this.messages=messages;
            this.clientSocket = clientSocket;
            this.files=files;
        }

        @Override
        public void run() {
        	///Start Service
        	try{
				PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String line = br.readLine();
				String []arLine = line.split("_");
				
				while(true){
					if(arLine[0].equals("LOG")){
						if(users.Exist(arLine[1], arLine[2]))break;
						pw.println("FLD_");
					}else if(arLine[0].equals("CRT")){
						users.insert(arLine[1], arLine[2]);
						pw.println("CRT_");
					}
					 line = br.readLine();
					 arLine = line.split("_");
				}
				pw.println("CON_");
				String Username=arLine[1];
				line="PEK_";
	        	while(true){

						
						arLine = line.split("_");

						if(arLine[0].equals("PEK")){
							pw.println(messages.getCodeMessages(Username)+friends.getCodeFriends(Username)+files.getFilesCode(Username)+friends.getCodeNewAdds(Username));
						}else if(arLine[0].equals("MSG")){
							messages.SentMessage(new Message(line));
						}else if(arLine[0].equals("FIL")){

						      byte [] buf  = new byte [Integer.parseInt(arLine[4])];
						      InputStream is = clientSocket.getInputStream();
						      int bytesRead = is.read(buf,0,buf.length);
						      int current = bytesRead;

						      do {
						         bytesRead =
						            is.read(buf, current, (buf.length-current));
						         if(bytesRead >= 0) current += bytesRead;
						      } while(bytesRead > -1 && current<buf.length);
						      files.storeFile(new DVFile(Username, arLine[2], arLine[3], buf));
						}else if(arLine[0].equals("GFL")){
					    	pw.println("GFL_"+arLine[1]+"_"+arLine[2]+"_"+arLine[3]+"_"+arLine[4]);
					        byte [] buf  = files.getFile(Username, arLine[3]).getData();
					        try {
							    OutputStream os = clientSocket.getOutputStream();
						        os.write(buf,0,buf.length);
							} catch (Exception e) {
						    	JOptionPane.showMessageDialog(null, "Error on senting File!", "Server", JOptionPane.OK_OPTION);

							}

						}else if(arLine[0].equals("ADD")){
							friends.add(arLine[1], arLine[2]);
						}
						line = br.readLine();

	        	}
        	}catch(Exception e){}
        	
        	finally{
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        	}

        }
    }

}