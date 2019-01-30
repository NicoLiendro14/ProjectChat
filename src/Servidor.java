import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		//setBounds(1200,300,280,350);		
		setSize(300,300);
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread hilo1=new Thread(this);
		hilo1.start();
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket ss=new ServerSocket(5432);
			
			String nick,ip,msj;
			
			
			packEnv reci;
			
			while(true) {
			
			Socket sSev=ss.accept();
			
			ObjectInputStream ois=new ObjectInputStream(sSev.getInputStream());
			
			reci=(packEnv) ois.readObject();
			
			nick=reci.getNick();
			ip=reci.getIp();
			msj=reci.getMsj();
			
			areatexto.append("\n "+nick+": "+msj+" para "+ip);
			
			//DataInputStream dis=new DataInputStream(sSev.getInputStream());
			
			//String msj=dis.readUTF();
			
			//areatexto.append(msj);
			
			
			//tender un puente por donde va a viajar la info
			//el puerto de entrada esd 5432
			
			Socket enviaDesti=new Socket(ip,5432);
			
			ObjectOutputStream packRee=new ObjectOutputStream(enviaDesti.getOutputStream());
			
			packRee.writeObject(reci);
			
			packRee.close(); //cerra el flujo de datos
			
			enviaDesti.close();
			
			sSev.close();
			
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
