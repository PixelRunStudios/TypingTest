package com.github.picrazy2.TypingTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Main{

	public static void main(String[] args){
		typingTest(60000,5, "bank.txt");
	}

	public static void typingTest(int timeInMillis, int numberDisplayed, String bank){
		ArrayList<String> cont = new ArrayList<String>();
		ArrayList<String> order = new ArrayList<String>();

		ArrayList<String> fullCont = new ArrayList<String>();

		long startTime = 0;
		long currentTime = 0;

		// TODO Auto-generated method stub
		String line = null;
		int correctCounter = 0;

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
		System.out.println(timeInMillis/1000+ " seconds starts after pressing 1");
		System.out.println("-1 to quit");
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		boolean start = true;
		int counter = 0;

		while(true){
			try{
				String input = systemIn.readLine();
				if(input.equals("-1")){
					currentTime = System.currentTimeMillis();
					System.out.println("Time elapsed: "+(currentTime-startTime)/1000.0 + " seconds");
					break;
				}
				else if(input.equals("1") && start){
					makeWords(cont, order);
					start = false;
					startTime = System.currentTimeMillis();
					for(int i = counter; i<counter+numberDisplayed; i++){
						System.out.print(order.get(i%order.size()) + " ");
					}
					System.out.println();
				}else if(!start){
					currentTime = System.currentTimeMillis();
					if(currentTime-startTime>=timeInMillis){

						break;

					}
					else{
						if(input.equals(order.get(counter%order.size()))){
							correctCounter++;
						}
						counter++;
						for(int i = counter; i<counter+numberDisplayed; i++){
							System.out.print(order.get(i%order.size()) + " ");
						}
						System.out.println();

					}
				}
			}
			catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Time up! Correct: "+correctCounter + ", Wrong: "+(counter-correctCounter));
	}

	public static void makeWords(ArrayList<String> cont, ArrayList<String> order){
		Random random = new Random();
		while(cont.size()!=0){
			int temp = random.nextInt(cont.size());
			order.add(cont.get(temp));
			cont.remove(temp);
		}
	}

}
