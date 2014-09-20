package com.github.assisstion.Shared.logging;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class LogPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = -3723376082239462955L;
	protected Logger logger;
	protected LoggerPane loggerPane;
	protected JPanel panel;
	protected JTextField textField;
	protected JButton btnGo;

	protected PipedOutputStream pos;
	protected PipedInputStream pis;
	protected DataOutputStream dos;
	protected DataInputStream dis;

	public LogPanel(){

		logger = Logger.getLogger("log-panel");
		setLayout(new BorderLayout(0, 0));
		loggerPane = new LoggerPane(logger, false);
		add(loggerPane);

		panel = new JPanel();
		loggerPane.add(panel, BorderLayout.SOUTH);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(25);

		btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					dos.writeUTF(textField.getText());
					dos.flush();
					textField.setText("");
				}
				catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel.add(btnGo);

		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					btnGo.doClick();
				}
			}
		});

		pos = new PipedOutputStream();
		try{
			pis = new PipedInputStream(pos);
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dos = new DataOutputStream(pos);
		dis = new DataInputStream(pis);

		new Thread(this).start();
	}

	@Override
	public void run(){
		while(true){
			String input = null;
			try{
				input = dis.readUTF();
			}
			catch(IOException e){
				break;
			}
			if(!processText(input)){
				break;
			}
		}

	}

	public Logger getLogger(){
		return logger;
	}

	//Not on the event dispatch thread
	public abstract boolean processText(String input);
}
