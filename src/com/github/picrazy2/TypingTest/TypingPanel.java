package com.github.picrazy2.TypingTest;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.github.assisstion.Shared.logging.CustomLevel;
import com.github.assisstion.Shared.logging.LogPanel;

public class TypingPanel extends LogPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -8342697066345190524L;

	ArrayList<String> cont;
	ArrayList<String> order;
	ArrayList<String> fullCont;
	long startTime;
	long currentTime;

	boolean start;
	boolean finish;

	boolean closed;

	int correctCounter;
	int counter;

	int timeInMillis;
	int numberDisplayed;

	public TypingPanel(int timeInMillis, int numberDisplayed, String bank){
		cont = new ArrayList<String>();
		order = new ArrayList<String>();

		fullCont = new ArrayList<String>();

		this.timeInMillis = timeInMillis;
		this.numberDisplayed = numberDisplayed;

		startTime = 0;
		currentTime = 0;

		closed = false;

		// TODO Auto-generated method stub
		String line = null;
		correctCounter = 0;

		File file = new File(bank);
		BufferedReader reader = null;

		try{
			reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			while(line!=null){
				cont.add(line);
				fullCont.add(line);
				line = reader.readLine();
			}
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try{
					reader.close();
				}
				catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		getLogger().log(CustomLevel.NOMESSAGE,timeInMillis/1000+ " seconds starts after pressing 1");
		getLogger().log(CustomLevel.NOMESSAGE,"-1 to quit\n\n");

		start = true;
		finish = false;

		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					btnGo.doClick();
				}
			}
		});

	}

	public void makeWords(ArrayList<String> cont, ArrayList<String> order){
		Random random = new Random();
		while(cont.size()!=0){
			int temp = random.nextInt(cont.size());
			order.add(cont.get(temp));
			cont.remove(temp);
		}
		//setSeparator(" ");
		/*
		for(int i = 0; i<order.size();i++){
			getLogger().log(CustomLevel.NOMESSAGE, order.get(i));
		}
		getLogger().log(CustomLevel.NOMESSAGE,"\n");*/
	}



	@Override
	public boolean processText(String input){
		if(closed){
			return true;
		}
		String s = input;
		while(s.startsWith(" ")){
			s = s.substring(1);
		}
		input = s;
		out:{
			if(input.equals("-1")){
				//setSeparator("\n");
				currentTime = System.currentTimeMillis();
				getLogger().log(CustomLevel.NOMESSAGE,"Time elapsed: "+(currentTime-startTime)/1000.0 + " seconds");
				getLogger().log(CustomLevel.NOMESSAGE,"Time up! Correct: "+correctCounter + ", Wrong: "+(counter-correctCounter));

				closed = true;
				break out;
			}
			else if(input.equals("1") && start){
				makeWords(cont, order);
				start = false;
				startTime = System.currentTimeMillis();
				String value = "";
				for(int i = counter; i<counter+numberDisplayed; i++){
					value +=order.get(i%order.size()) + " ";
				}
				getLogger().log(CustomLevel.NOMESSAGE, value+ "\n");
				System.out.println();
			}
			else if(!start){
				//setSeparator("\n");
				currentTime = System.currentTimeMillis();
				if(currentTime-startTime>=timeInMillis){
					getLogger().log(CustomLevel.NOMESSAGE,"Time up! Correct: "+correctCounter + ", Wrong: "+(counter-correctCounter));
					closed = true;
					break out;

				}
				else{
					if(input.equals(order.get(counter%order.size()))){
						correctCounter++;
						setLoggerColor(Color.green);
						getLogger().log(CustomLevel.NOMESSAGE, order.get(counter%order.size()) + "\n\n");
						setLoggerColor(Color.black);
					}else{
						setLoggerColor(Color.red);
						getLogger().log(CustomLevel.NOMESSAGE, order.get(counter%order.size()) + "\n\n");
						setLoggerColor(Color.black);
					}
					counter++;

					String value = "";
					for(int i = counter; i<counter+numberDisplayed; i++){
						value+=order.get(i%order.size()) + " ";
					}
					getLogger().log(CustomLevel.NOMESSAGE, value + "\n");

					System.out.println();

				}
			}
		}

		return true;
	}
}
