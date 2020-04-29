package chatting;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.JTextField;

import chatting.node;
import chatting.sortbyfreq;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;

public class client  {

	private JFrame frame;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextArea textArea_1;
	private static JTextArea textArea;
	
	static Socket S;
	
	static DataInputStream dis;
	static DataOutputStream dos;
	static String msgin="";
	
	private static HashMap<String,String> enm = new HashMap<String,String>();//for storing ch-1010110
	
	static String comsg="";
	
	private static void printcodes(node root, String str)throws Exception {
		if(root.left == null && root.right == null )
		{
			String da=""+root.data;
			 enm.put(da,str);
			 //ednm.put(str, da);
			 comsg=comsg+str+":"+da+"$";
			return ;
		}
		printcodes(root.left,str+"0");
		printcodes(root.right,str+"1");
		
	}
	
	

	
	public static void main(String[] args) throws Exception
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client window = new client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//int port = Integer.parseInt(textField_1.getText());
		try {
			S = new Socket("localhost",2261);//(textField.getText(),port);
			
			dos = new DataOutputStream(S.getOutputStream());
			dis = new DataInputStream(S.getInputStream());

			String hemmsg="";
			String demmsg="";
			int i,j;
			String dnm="";
			String decodemessage="";
			
			while(msgin!="$$$$")
			{
				HashMap<String,String> ednm = new HashMap<String,String>();//for storing 1010110-ch
				msgin = dis.readUTF();
				
				for(i=0;i<msgin.length();i++)
				{
					if(msgin.charAt(i)=='@')
					{
						i++;
						break;
					}
					hemmsg=hemmsg+msgin.charAt(i);
				}
				textArea.setText(textArea.getText()+"\n"+"rev : "+hemmsg);
				for(j=i;j<msgin.length();j++)
				{
					if(msgin.charAt(j)==':')
					{
						j++;
						ednm.put(demmsg,""+msgin.charAt(j));
						demmsg="";
						j=j+2;
						if(j==msgin.length())
						{
							break;
						}
					}
					demmsg=demmsg+msgin.charAt(j);
				}
				
				
				for(i=0;i<hemmsg.length();i++)
				{
					dnm=dnm+hemmsg.charAt(i);
					if(ednm.containsKey(dnm))
					{
						decodemessage=decodemessage+ednm.get(dnm);
						dnm="";
					}
				}
				
				
				textArea_1.setText(decodemessage);
				
				hemmsg="";
				decodemessage="";
				dnm="";
				
			}
			
			
			
		} catch (Exception e1) {
			//not handle
		} 
		
	}

	
	public client() {
		initialize();
	}

	
	private void initialize() {
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 600, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblIpAddress.setBounds(23, 29, 109, 22);
		frame.getContentPane().add(lblIpAddress);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPortNumber.setBounds(229, 34, 131, 17);
		frame.getContentPane().add(lblPortNumber);
		
		textField = new JTextField();
		textField.setBounds(12, 64, 173, 22);
		frame.getContentPane().add(textField);
		textField.setText("Hemanth");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(229, 64, 142, 22);
		frame.getContentPane().add(textField_1);
		textField_1.setText("    - $ - * - $ -");
		textField_1.setColumns(10);
		
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(446, 75, 97, 25);
		frame.getContentPane().add(btnDisconnect);
		btnDisconnect.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				try {
dos = new DataOutputStream(S.getOutputStream());
					
					dos.writeUTF("$$$$");
					dos.flush();
					S.close();
					
				} catch (Exception e1) {
					
				}
				
			}
			
		});
		
		JLabel lblBeforeMessages = new JLabel("Before Messages");
		lblBeforeMessages.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBeforeMessages.setBounds(22, 111, 163, 22);
		frame.getContentPane().add(lblBeforeMessages);
		
		textArea = new JTextArea();
		textArea.setBounds(23, 146, 530, 145);
		frame.getContentPane().add(textArea);
		
		JLabel lblRecivedMessage = new JLabel("Recived Message");
		lblRecivedMessage.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRecivedMessage.setBounds(23, 316, 190, 22);
		frame.getContentPane().add(lblRecivedMessage);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(23, 351, 530, 46);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblTypeMessage = new JLabel("Type Message");
		lblTypeMessage.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTypeMessage.setBounds(23, 410, 162, 22);
		frame.getContentPane().add(lblTypeMessage);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(23, 445, 442, 45);
		frame.getContentPane().add(textArea_2);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(477, 444, 97, 25);
		frame.getContentPane().add(btnSend);
		btnSend.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				
				try {
					String message =textArea_2.getText();
					String encodemessage="";
					int[] fre = new int[message.length()];
					int i,j,n=0;
					char str[] = message.toCharArray();

					for(i=0;i<message.length();i++)
					{
						fre[i]=1;
						for(j=i+1;j<message.length();j++)
						{
							if(str[i]==str[j])
							{
								fre[i]++;
								str[j]='0';
							}
						}
					}
					
					for(i=0;i<fre.length;i++)
					{
						if( str[i]!='0')
						{
							n=n+1;
						}
					}
					
					PriorityQueue<node> msg = new PriorityQueue<node>(n,new sortbyfreq());
					
					for(i=0;i<fre.length;i++)
					{
						if( str[i]!='0')
						{
							node h=new node(fre[i],str[i]);
							msg.add(h);
						}
					}
					
					node root=null;//encoding
					
					while(msg.size()>1)
					{
						node p = msg.peek();
						msg.poll();//remove last
						
						node q = msg.peek();
						msg.poll();//remove second last
						
						 node temp = new node(p.frequency+q.frequency);
						 temp.left=p;
						 temp.right=q;
						 
						 root=temp;
						 msg.add(temp);//add sum 2
						
					}
					comsg="";
					
					printcodes(root,"");
					for(i=0;i<message.length();i++)
					{
						String da=""+message.charAt(i);
						//System.out.println(enm.get(da));
						encodemessage=encodemessage+enm.get(da);
					}
					
					textArea.setText(textArea.getText()+"\n"+"sen : "+encodemessage);
					encodemessage=encodemessage+"@"+comsg;
					
					dos.writeUTF(encodemessage);
					dos.flush();
					textArea_2.setText(null);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					
				}
				
				
			}
			
		});
		
		
	}
}

