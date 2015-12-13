package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import module.ClientControl;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.awt.event.ActionEvent;

public class Start {
	boolean isServer;
	static ClientControl system;
	JPanel pWelcome;
	JPanel pInitial;
	JPanel pChat;
	CardLayout cards;
	Container container;
	String nickName;
	static InetAddress localIP;

	private JFrame frame;
	private JTextField tNickName;
	private JTextField tIP;
private JTextField tInput;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
				
		system = new ClientControl();
		
		//get host address
		try{
			localIP = InetAddress.getLocalHost();
		}catch(Exception e2){
			System.out.println("unknown host!");
		}

		//paint user interface
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager
						.setLookAndFeel(new org.jvnet.substance.skin.SubstanceRavenLookAndFeel());
					Start window = new Start();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application.
	public Start() {
		initialize();
	}

	//set initialization
	private void initialize() {
		frame = new JFrame();
		container = frame.getContentPane();
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new CardLayout(0,0);
		frame.getContentPane().setLayout(cards);
		setPWelcome();
		setPInitial();
		setPChat();
		cards.show(frame.getContentPane(), "pWelcome");
	}
	
	//create welcome page
    private void setPWelcome(){
		pWelcome = new JPanel();
		frame.getContentPane().add(pWelcome, "pWelcome");
		pWelcome.setLayout(new BorderLayout(0, 0));
		
		JPanel pImage = new JPanel(){
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("./image/logo.png");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(),  
                        icon.getIconHeight(), icon.getImageObserver());  
  
            } 
        };
        
		pWelcome.add(pImage, BorderLayout.CENTER);
		
		JPanel pButton = new JPanel();
		pWelcome.add(pButton, BorderLayout.SOUTH);
		
		JButton bServer = new JButton("Log in to Server");
		bServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isServer = true;
				system.setServerMode();
				try {
					setPInitial();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				container.add(pInitial,"pInitial");
				cards.show(container, "pInitial");
			}
		});
		pButton.add(bServer);
		
		JButton bP2P = new JButton("Start P2P chat");
		pButton.add(bP2P);
		
    	bP2P.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					    					
    					isServer = false;
    					system.setP2PMode();
    					try {
							setPInitial();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    					container.add(pInitial,"pInitial");
    					cards.show(container, "pInitial");
    				}
    			}); 

    }
    
    //create initialization page
    private void setPInitial(){
		pInitial = new JPanel();
		frame.getContentPane().add(pInitial, BorderLayout.CENTER);
		GridBagLayout gbl_pInitial = new GridBagLayout();
		gbl_pInitial.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pInitial.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pInitial.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pInitial.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pInitial.setLayout(gbl_pInitial);
		
		JLabel label_6 = new JLabel("        ");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 6;
		gbc_label_6.gridy = 0;
		pInitial.add(label_6, gbc_label_6);
		
		JLabel label_7 = new JLabel("        ");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 6;
		gbc_label_7.gridy = 1;
		pInitial.add(label_7, gbc_label_7);
		
		JLabel label_8 = new JLabel("        ");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 6;
		gbc_label_8.gridy = 2;
		pInitial.add(label_8, gbc_label_8);
		
		JLabel label_9 = new JLabel("        ");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 6;
		gbc_label_9.gridy = 3;
		pInitial.add(label_9, gbc_label_9);
		
		JLabel lNickName = new JLabel("Your nickname:");
		GridBagConstraints gbc_lNickName = new GridBagConstraints();
		gbc_lNickName.insets = new Insets(0, 0, 5, 5);
		gbc_lNickName.gridx = 6;
		gbc_lNickName.gridy = 8;
		pInitial.add(lNickName, gbc_lNickName);
		
		tNickName = new JTextField();
		GridBagConstraints gbc_tNickName = new GridBagConstraints();
		gbc_tNickName.insets = new Insets(0, 0, 5, 5);
		gbc_tNickName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tNickName.gridx = 6;
		gbc_tNickName.gridy = 9;
		pInitial.add(tNickName, gbc_tNickName);
		tNickName.setColumns(10);
		
		JLabel lIP = new JLabel("Other side IP:");
		GridBagConstraints gbc_lIP = new GridBagConstraints();
		gbc_lIP.insets = new Insets(0, 0, 5, 5);
		gbc_lIP.gridx = 6;
		gbc_lIP.gridy = 4;
    	if(!isServer) pInitial.add(lIP, gbc_lIP);
		
		JLabel label_11 = new JLabel("        ");
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 7;
		pInitial.add(label_11, gbc_label_11);
		
		JLabel label_13 = new JLabel("        ");
		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 1;
		gbc_label_13.gridy = 7;
		pInitial.add(label_13, gbc_label_13);
		
		JLabel label_12 = new JLabel("        ");
		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.insets = new Insets(0, 0, 5, 5);
		gbc_label_12.gridx = 2;
		gbc_label_12.gridy = 7;
		pInitial.add(label_12, gbc_label_12);
		
		JLabel label_2 = new JLabel("        ");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 3;
		gbc_label_2.gridy = 7;
		pInitial.add(label_2, gbc_label_2);
		
		JLabel lblNewLabel_17 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
		gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_17.gridx = 4;
		gbc_lblNewLabel_17.gridy = 7;
		pInitial.add(lblNewLabel_17, gbc_lblNewLabel_17);
		
		JLabel lblNewLabel = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 7;
		pInitial.add(lblNewLabel, gbc_lblNewLabel);
		
		tIP = new JTextField();
		GridBagConstraints gbc_tIP = new GridBagConstraints();
		gbc_tIP.insets = new Insets(0, 0, 5, 5);
		gbc_tIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_tIP.gridx = 6;
		gbc_tIP.gridy = 5;
		if(!isServer) pInitial.add(tIP, gbc_tIP);
		tIP.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_7.gridx = 7;
		gbc_lblNewLabel_7.gridy = 7;
		pInitial.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel label_1 = new JLabel("        ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 3;
		gbc_label_1.gridy = 8;
		pInitial.add(label_1, gbc_label_1);
		
		JLabel lblNewLabel_1 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 8;
		pInitial.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_14 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
		gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14.gridx = 5;
		gbc_lblNewLabel_14.gridy = 8;
		pInitial.add(lblNewLabel_14, gbc_lblNewLabel_14);
		
		JLabel lPort = new JLabel("        ");
		GridBagConstraints gbc_lPort = new GridBagConstraints();
		gbc_lPort.insets = new Insets(0, 0, 5, 5);
		gbc_lPort.gridx = 6;
		gbc_lPort.gridy = 6;
		if(!isServer) pInitial.add(lPort, gbc_lPort);
		
		JLabel lblNewLabel_8 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_8.gridx = 7;
		gbc_lblNewLabel_8.gridy = 8;
		pInitial.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel label = new JLabel("        ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 9;
		pInitial.add(label, gbc_label);
		
		JLabel lblNewLabel_13 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 4;
		gbc_lblNewLabel_13.gridy = 9;
		pInitial.add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		JLabel lblNewLabel_2 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 9;
		pInitial.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel_port = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_port = new GridBagConstraints();
		gbc_lblNewLabel_port.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_port.gridx = 6;
		gbc_lblNewLabel_port.gridy = 7;
		pInitial.add(lblNewLabel_port, gbc_lblNewLabel_port);
		
		JLabel lblNewLabel_9 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_9.gridx = 7;
		gbc_lblNewLabel_9.gridy = 9;
		pInitial.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		JLabel label_3 = new JLabel("        ");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 3;
		gbc_label_3.gridy = 10;
		pInitial.add(label_3, gbc_label_3);
		
		JLabel lblNewLabel_3 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 10;
		pInitial.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lYortIP = new JLabel("        ");
		GridBagConstraints gbc_lYortIP = new GridBagConstraints();
		gbc_lYortIP.anchor = GridBagConstraints.WEST;
		gbc_lYortIP.insets = new Insets(0, 0, 5, 5);
		gbc_lYortIP.gridx = 5;
		gbc_lYortIP.gridy = 10;
		pInitial.add(lYortIP, gbc_lYortIP);
		
		JLabel lblNewLabel_18 = new JLabel("Your IP is: " + localIP);
		GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
		gbc_lblNewLabel_18.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_18.gridx = 6;
		gbc_lblNewLabel_18.gridy = 10;
		pInitial.add(lblNewLabel_18, gbc_lblNewLabel_18);
		
		JLabel lblNewLabel_10 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_10.gridx = 7;
		gbc_lblNewLabel_10.gridy = 10;
		pInitial.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		JLabel label_4 = new JLabel("        ");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 3;
		gbc_label_4.gridy = 11;
		pInitial.add(label_4, gbc_label_4);
		
		JLabel lblNewLabel_15 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
		gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_15.gridx = 4;
		gbc_lblNewLabel_15.gridy = 11;
		pInitial.add(lblNewLabel_15, gbc_lblNewLabel_15);
		
		JLabel lblNewLabel_4 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 5;
		gbc_lblNewLabel_4.gridy = 11;
		pInitial.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lYourPort = new JLabel("        ");
		GridBagConstraints gbc_lYourPort = new GridBagConstraints();
		gbc_lYourPort.insets = new Insets(0, 0, 5, 5);
		gbc_lYourPort.gridx = 6;
		gbc_lYourPort.gridy = 11;
		pInitial.add(lYourPort, gbc_lYourPort);
		
		JLabel lblNewLabel_11 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_11.gridx = 7;
		gbc_lblNewLabel_11.gridy = 11;
		pInitial.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		JLabel label_5 = new JLabel("        ");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 3;
		gbc_label_5.gridy = 12;
		pInitial.add(label_5, gbc_label_5);
		
		JLabel lblNewLabel_5 = new JLabel("       ");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_5.gridx = 4;
		gbc_lblNewLabel_5.gridy = 12;
		pInitial.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_16 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
		gbc_lblNewLabel_16.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_16.gridx = 5;
		gbc_lblNewLabel_16.gridy = 12;
		pInitial.add(lblNewLabel_16, gbc_lblNewLabel_16);
		
		JButton bConnect = new JButton("Connect");
		GridBagConstraints gbc_bConnect = new GridBagConstraints();
		gbc_bConnect.insets = new Insets(0, 0, 0, 5);
		gbc_bConnect.gridx = 6;
		gbc_bConnect.gridy = 12;
		pInitial.add(bConnect, gbc_bConnect);
		
		JLabel lblNewLabel_12 = new JLabel("        ");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.gridx = 7;
		gbc_lblNewLabel_12.gridy = 12;
		pInitial.add(lblNewLabel_12, gbc_lblNewLabel_12);
		
    	bConnect.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					system.clearText();
    					if(isServer){
							try {
								system.connectToServer(tNickName.getText());
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
    					}
						else
    						system.connectToP2P(tIP.getText());
						nickName = tNickName.getText();
   						cards.show(container, "pChat");
    				}
    			});
    }
    
    //create chatting page
    private void setPChat(){
		
		pChat = new JPanel();
		frame.getContentPane().add(pChat, "pChat");
		pChat.setLayout(new BoxLayout(pChat, BoxLayout.Y_AXIS));
		
		JLabel lTitle = new JLabel("Chat logs");
		pChat.add(lTitle);
			
		pChat.add(system.getTextArea());
		
		JLabel l1 = new JLabel("        ");
		pChat.add(l1);
		
		tInput = new JTextField();
		pChat.add(tInput);
		tInput.setColumns(10);
		
		JPanel pCButton = new JPanel();
		pChat.add(pCButton);
		
		JButton bSend = new JButton("Send");
		pCButton.add(bSend);
		
		JButton bExit = new JButton("Exit");
		pCButton.add(bExit);
		
    	bSend.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					try {
    						if(isServer)
    							system.inputMessage(nickName + "." + tInput.getText());
    						else
    							system.inputMessage(nickName + " (" + localIP.toString() + ") "
    									+ tInput.getText());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    				}
    			});
    	bExit.addActionListener(
    			new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(isServer)
							try {
								system.inputMessage("DIS_BYE" + "." + nickName);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						else
							try {
								system.inputMessage("Another client leaves.");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
    					cards.show(container, "pWelcome");
						system.clearText();
					}
				});	
    }
}
