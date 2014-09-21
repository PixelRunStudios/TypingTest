package com.github.assisstion.Shared.logging;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class LoggerPane extends JPanel{

	public Logger log;

	private static final long serialVersionUID = 1022490441168101112L;
	protected JTextPane textPane;
	private JScrollPane scrollPane;
	protected LogWorker worker;
	protected ProgressWorker progress;
	private JProgressBar progressBar;
	protected Style style;
	protected StyledDocument document;
	//protected String separator;
	protected Color color = Color.BLACK;

	/**
	 * Create the frame.
	 */
	public LoggerPane(Logger log, boolean showProgress){
		//setTitle();

		setLayout(new BorderLayout(0, 0));

		this.log = log;
		LogHandler handler = new LogHandler(this);
		log.addHandler(handler);

		scrollPane = new JScrollPane();
		add(scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);

		progressBar = new JProgressBar(0, 0);
		progressBar.setEnabled(false);
		progressBar.setStringPainted(false);
		if(showProgress){
			add(progressBar, BorderLayout.SOUTH);
		}

		DefaultCaret caret = (DefaultCaret) textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


		document = textPane.getStyledDocument();
		style = document.addStyle("text-style", null);
		StyleConstants.setForeground(style, Color.BLACK);

		worker = new LogWorker();
		progress = new ProgressWorker();

		//separator = "\n";
	}
	/*
	public void setSeparator(String separator){
		this.separator = separator;
	}

	public String getSeparator(){
		return separator;
	}
	 */
	public void setProgress(IntegerPair values){
		progress.push(values);
	}

	public class LogWorker extends SwingWorker<Object, Pair<String, Color>>{

		protected void push(Pair<String, Color> message){
			publish(message);
		}

		@Override
		protected Object doInBackground() throws Exception{
			return null;
		}

		@Override
		protected void process(List<Pair<String, Color>> messages){
			for(Pair<String, Color> messageOne : messages){
				/*if(!textPane.getText().equals("") || !message.equals("")){
					message += separator;
				}*/
				StyleConstants.setForeground(style, messageOne.getValueTwo());
				try{
					document.insertString(document.getLength(), messageOne.getValueOne(), style);
				}
				catch(BadLocationException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public class ProgressWorker extends SwingWorker<Object, IntegerPair>{

		protected void push(IntegerPair progress){
			publish(progress);
		}

		@Override
		protected Object doInBackground() throws Exception{
			return null;
		}

		@Override
		protected void process(List<IntegerPair> integerPairs){
			for(IntegerPair pairOne : integerPairs){
				if(pairOne.getValueTwo().equals(0)){
					progressBar.setEnabled(false);
					progressBar.setStringPainted(false);
				}
				else{
					progressBar.setEnabled(true);
					progressBar.setStringPainted(true);
				}
				progressBar.setValue(pairOne.getValueOne());
				progressBar.setMaximum(pairOne.getValueTwo());
			}
		}
	}
}