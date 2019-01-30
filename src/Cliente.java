import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
class packEnv implements Serializable {
	private String nick,ip,msj;
	
	public packEnv() {
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}
}

class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setSize(300,320);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket ss=new ServerSocket(5432);
			
			Socket s;
			
			packEnv packReci;
			
			while(true) {
				s=ss.accept();
				
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				
				packReci=(packEnv) ois.readObject();
				
				cmpchat.append("\n"+packReci.getNick()+": "+packReci.getMsj());
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public LaminaMarcoCliente(){
		//setLayout(new GridLayout(2,1));
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ip=new JTextField(8);
		nick=new JTextField(5);
		JTextArea cmpchat=new JTextArea(12,20);
		JLabel texto=new JLabel("***WppLodeiro***");
		campo1=new JTextField(20);
		miboton=new JButton("Enviar");
		EnviaTexto miEven=new EnviaTexto();
		miboton.addActionListener(miEven);
		
		add(nick);
		
		add(texto);
		
		add(ip);
		
		add(cmpchat);
		
		add(campo1);		
		
		add(miboton);	
		
		Thread mihilo=new Thread(this);
		
		mihilo.start();
	}


	
	private class EnviaTexto implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			cmpchat.append("\n"+nick+": "+campo1.getText());
			try {
				Socket s=new Socket("192.168.1.4",5432);
				
				packEnv datos=new packEnv();
				
				datos.setNick(nick.getText());
				
				datos.setIp(ip.getText());
				
				datos.setMsj(campo1.getText());
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				
				oos.writeObject(datos);
				
				s.close();
				/*DataOutputStream dts=new DataOutputStream(s.getOutputStream());
				
				dts.writeUTF(campo1.getText());
				
				dts.close();*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
		
	private JTextArea cmpchat;;

	
		
	private JTextField campo1 ,nick,ip;
	
	private JButton miboton;

	
	
}