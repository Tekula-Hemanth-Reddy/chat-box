package chatserver;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.JTextArea;

import chatserver.node;
import chatserver.sortbyfreq;

public class server  {

	private JFrame frame;
	private static JTextArea textArea;
	private static JTextArea textPane;
	
	static ServerSocket ss;
	static Socket s;
	static DataInputStream dis;
	static DataOutputStream dos;
	static String msgin="";
	
	private static HashMap<String,String> enm = new HashMap<String,String>();//for storing ch-1010110
	public static String comsg="";
	
	private static void printcodes(node root, String str)throws Exception {
		if(root.left == null && root.right == null )
		{
			String da=""+root.data;
			 enm.put(da,str);
			// ednm.put(str, da);
			 comsg=comsg+str+":"+da+"$";
			return ;
		}
		printcodes(root.left,str+"0");
		printcodes(root.right,str+"1");
		
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server window = new server();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		 try {
				ss = new ServerSocket(2261);
				s = ss.accept();
				
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				
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
					
					textArea.setText(decodemessage);
					textPane.setText(textPane.getText()+"\n"+"rev : "+hemmsg);
					
					hemmsg="";
					decodemessage="";
					dnm="";
					ednm=null;
				}
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
	}

	
	public server() {
		initialize();
	}

	private void initialize() {
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 520);
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textPane = new JTextArea();//am
		textPane.setBounds(12, 41, 545, 197);
		frame.getContentPane().add(textPane);
		
		
		JTextArea textPane_1 = new JTextArea();//tm
		textPane_1.setBounds(12, 399, 452, 61);
		frame.getContentPane().add(textPane_1);
		
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(473, 410, 97, 25);
		frame.getContentPane().add(btnSend);
        btnSend.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String message =textPane_1.getText();
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
					
					textPane.setText(textPane.getText()+"\n"+"sen : "+encodemessage);
					encodemessage=encodemessage+"@"+comsg;
					
					dos.writeUTF(encodemessage);
					dos.flush();
					textPane_1.setText(null);
					
				} catch (Exception e1) {
					
				}
				
				
			}
			
		});
		
		JLabel lblTypeMessage = new JLabel("Type Message");
		lblTypeMessage.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 20));
		lblTypeMessage.setBounds(12, 361, 152, 25);
		frame.getContentPane().add(lblTypeMessage);
		
		JLabel lblBeforeMessages = new JLabel("Before Messages");
		lblBeforeMessages.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBeforeMessages.setBounds(12, 12, 112, 16);
		frame.getContentPane().add(lblBeforeMessages);
		
		textArea = new JTextArea();//bm
		textArea.setBounds(12, 305, 545, 38);
		frame.getContentPane().add(textArea);
		
		JLabel lblRecivedMessage = new JLabel("Recived Message");
		lblRecivedMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblRecivedMessage.setBounds(12, 262, 184, 30);
		frame.getContentPane().add(lblRecivedMessage);
	}

}
