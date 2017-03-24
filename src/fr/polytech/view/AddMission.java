package fr.polytech.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddMission extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMission frame = new AddMission();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddMission() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 762, 565);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(227, 48, 153, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblClientName = new JLabel("Name:");
		lblClientName.setBounds(173, 51, 40, 18);
		contentPane.add(lblClientName);
		
		JLabel lblClient = new JLabel("Client");
		lblClient.setBounds(112, 13, 72, 18);
		contentPane.add(lblClient);
		
		JLabel lblNewLabel = new JLabel("xCoordinate:");
		lblNewLabel.setBounds(112, 98, 101, 18);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(227, 95, 153, 24);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(227, 148, 153, 24);
		contentPane.add(textField_2);
		
		JLabel lblYcoordinate = new JLabel("yCoordinate:");
		lblYcoordinate.setBounds(112, 154, 101, 18);
		contentPane.add(lblYcoordinate);
	}
}
