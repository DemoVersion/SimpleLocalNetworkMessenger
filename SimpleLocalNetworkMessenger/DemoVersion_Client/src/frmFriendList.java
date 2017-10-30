import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;


public class frmFriendList extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private HashMap<String, Boolean> hmFriends= new HashMap<String, Boolean>();
	JList list;
	Client client;
	public JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */

	public void Query(String s){
		String [] ar = s.split("_");
		Boolean On= (ar[2].equals("ON"));
		if(!hmFriends.containsKey(ar[1])||!hmFriends.get(ar[1]).equals(On)){
			hmFriends.put(ar[1], On);
			newList();
		}
	}
	private void newList(){
		Vector<String> vs= new Vector<String>();
		for(String s:hmFriends.keySet() ){
			vs.add(s+" ("+(hmFriends.get(s)?"Online)":"Offline)"));
		}
		list.setListData(vs);
	}
	public frmFriendList(Client c) {
		client = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 58, 166, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.SentRequest(textField.getText());
			}
		});
		btnAdd.setBounds(186, 57, 89, 23);
		contentPane.add(btnAdd);

		
		list = new JList();
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {


		           String selectedItem = (String) list.getSelectedValue();
		           ///JOptionPane.showMessageDialog(null, selectedItem, "Messenger", JOptionPane.INFORMATION_MESSAGE);
				    client.showfrmIM(selectedItem.split(" ")[0]);

		         }
		    }
		};
		list.addMouseListener(mouseListener);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 91, 265, 307);
		contentPane.add(list);
		
		lblNewLabel = new JLabel("Hello _");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblNewLabel.setBounds(10, 9, 265, 38);
		contentPane.add(lblNewLabel);
	}
}
