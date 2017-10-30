import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.ws.handler.MessageContext.Scope;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Client {
	private String username;
	private Socket socket ;
	public String strRecive;
	private Timer timer;
	private frmFriendList fFL;
	private frmLogin fL;
	HashMap<String, frmIM> hmsf;
    public static void main(String[] args) {
        new Client().startClient();
    }
    public void successLogin(){
    	timer.start();
    	fL.setVisible(false);
    	fFL.setVisible(true);
    }
    public void CreateUser(String User,String Pass){
    	Sent("CRT_"+User+"_"+Pass);
    }
    public void failedLogin(){
    	JOptionPane.showMessageDialog(null, "Username and/or Password is wrong, Try Again!", "Messenger", JOptionPane.OK_OPTION);
    }
    public void SentFile(File f,String reciver){
    	
    	Sent("FIL_"+username+"_"+reciver+"_"+f.getName()+"_"+f.length());
        byte [] buf  = new byte [(int)f.length()];
        JOptionPane.showMessageDialog(null, f.getPath(), "Messenger", JOptionPane.OK_OPTION);
        FileInputStream fis;
		try {
			fis = new FileInputStream(f);
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        bis.read(buf,0,buf.length);
	        OutputStream os = socket.getOutputStream();
	        os.write(buf,0,buf.length);
		} catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error on senting File!", "Messenger", JOptionPane.OK_OPTION);

		}

    	 
    }
    public void sentLogin(String User,String Pass){
    	Sent("LOG_"+User+"_"+Pass);
    	username=User;
    	fFL.lblNewLabel.setText("Welcome "+User+"!");
    	fFL.setTitle(User);
    }
    public void showfrmIM(String Name){
    	if(!hmsf.containsKey(Name)){
			hmsf.put(Name, new frmIM(this, username, Name));
		}
		hmsf.get(Name).setVisible(true);
    }
	public Client() {
		fL= new frmLogin(this);
		fL.setVisible(true);
		
		fFL = new frmFriendList(this);
		hmsf=new HashMap<String, frmIM>();
		strRecive="";
		timer= new Timer(3000,new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sent("PEK_");
			}
		});
		timer.setInitialDelay(0);
		timer.start();

	}

	public void startClient(){
		try {
			socket = new Socket("127.0.0.1", 4789);
		}catch (Exception e) {}
		ServerTask serverTask=new ServerTask(this,socket);
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
	}
	public synchronized void Sent(String str){
		try{
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			pw.println(str);
    	}catch(Exception e){}
	}
	public void SentRequest(String name){

			Sent("ADD_"+username+"_"+name);
	}
	public void SentMessage(Message m){
			Sent(m.getCode());
	}
	public void ReciveMessage(Message m){
		if(!hmsf.containsKey(m.getSenter())){
			hmsf.put(m.getSenter(), new frmIM(this, username, m.getSenter()));
		}
		hmsf.get(m.getSenter()).setVisible(true);
		hmsf.get(m.getSenter()).addMessage(m.getMSG());
	}
	public void Query(String line){
		String ar[]=line.split("_");
		if(ar[0].equals("MSG")){
			ReciveMessage(new Message(line));
		}else if(ar[0].equals("FIL")){
			int select= JOptionPane.showConfirmDialog(null, "Do you want to recive file "+ar[3]+" ("+ar[4]+" Byte) from "+ar[1]+"?", "Messenger", JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.OK_OPTION){
				Sent("GFL_"+ar[1]+"_"+ar[2]+"_"+ar[3]+"_"+ar[4]+"_");
			}
		}else if(ar[0].equals("FRN")){
			fFL.Query(line);
		}else if(ar[0].equals("ADD")){
			int select= JOptionPane.showConfirmDialog(null, "Do you confirm "+ar[1]+" friend request?", "Messenger", JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.OK_OPTION){
				Sent("ADD_"+username+"_"+ar[1]);
			}
		}else if(ar[0].equals("CON")){
			successLogin();
		}else if(ar[0].equals("FLD")){
			failedLogin();
		}else if(ar[0].equals("CRT")){
			JOptionPane.showMessageDialog(null, "Created Successfully!! Now You Can Login !!", "Messenger", JOptionPane.INFORMATION_MESSAGE);
		    
		}
	}
    private class ServerTask implements Runnable {
        private Socket sSocket;
        private Client sClient;
        BufferedReader br;
        private ServerTask(Client c ,Socket sSocket) {
        	this.sSocket =sSocket;
        	
            sClient=c;
        }

        @Override
        public void run() {
        	///Start Service
        	try{
        		br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
				while(true){
					if(br.ready()){
						String line=br.readLine();
						Query(line);
					}
				}
        	}catch(Exception e){
        		
        		JOptionPane.showMessageDialog(null, "Server Connection Faild!", "Messenger", JOptionPane.INFORMATION_MESSAGE);
    		    System.exit(0);
        	}
        	
        	finally{
                try {

                	sSocket.close();
                } catch (IOException e) {
                	JOptionPane.showMessageDialog(null, "Server Connection Faild!", "Messenger", JOptionPane.INFORMATION_MESSAGE);
                	System.exit(0);
                }
        	}

        }
    }
}
