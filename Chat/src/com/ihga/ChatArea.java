package com.ihga;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ChatArea extends JPanel implements KeyListener{
	
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JTextField inputField;
	private JButton sendButton;
	
	public ChatArea(){
		//Create GUI elements
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200, 300));
		
		inputField = new JTextField();
		inputField.setColumns(35);
		inputField.requestFocus();
		inputField.addKeyListener(this);
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
			
		});
		
		this.setLayout(new GridBagLayout());
		this.addKeyListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 0;
		
		c.gridwidth = 2;
		this.add(scrollPane, c);
		c.gridheight = 1;
		c.gridwidth = 1;
		
		c.gridy++;
		
		this.add(inputField, c);
		
		c.gridx++;
		
		this.add(sendButton, c);
	}
	
	public void addMessage(String msg){
		textArea.append(msg + "\n");
		//textArea.append("<html><hr></html>");
		inputField.requestFocus();
	}
	
	public void sendMessage(){
		Main.sendMessage(inputField.getText());
		inputField.setText("");
		inputField.requestFocus();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			this.sendMessage();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
