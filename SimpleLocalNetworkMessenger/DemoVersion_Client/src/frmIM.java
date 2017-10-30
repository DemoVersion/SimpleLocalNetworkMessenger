import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class frmIM extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JTextArea display;
	String myName,friendName;
	Client client;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public void addMessage(String MSG){
		display.setText(display.getText()+ friendName+":"+MSG+"\n");
	}
	public frmIM(Client c,String myN,String friendN) {
		 this.addWindowListener(new WindowAdapter(){
             public void windowClosing(WindowEvent e){
                 setVisible(false);
             }
         });
		this.client=c;
		this.myName=myN;
		this.friendName=friendN;
		setTitle(friendName);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 445, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	    JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

	    // create the middle panel components

	     display = new JTextArea ( 16, 58 );
	     display.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    scroll.setBounds(10, 11, 414, 207);
	    //Add Textarea in to middle panel
	    contentPane.add ( scroll );

		
		JButton btnSent = new JButton("Sent");
		btnSent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.SentMessage(new Message(myName,friendName,textField.getText()));
				
				display.setText(display.getText()+ myName+":"+textField.getText()+"\n");
				textField.setText("");
			}
		});
		btnSent.setBounds(240, 225, 89, 23);
		contentPane.add(btnSent);
		
		textField = new JTextField();
		textField.setBounds(10, 228, 220, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSentfile = new JButton("SentFile");
		btnSentfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				    File sf = fileChooser.getSelectedFile();
				    client.SentFile(sf,friendName);
				}
			}
		});
		btnSentfile.setBounds(335, 225, 89, 23);
		contentPane.add(btnSentfile);
	}
}
