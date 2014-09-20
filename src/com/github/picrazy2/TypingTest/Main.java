package com.github.picrazy2.TypingTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main{


	static long startTime = 0;
	static long endTime = 0;
	static long timePassed = 0;
	static Random random = new Random();

	public static void main(String[] args){


		// TODO Auto-generated method stub
		String line = null;
		String content = "";

		/*File file = new File("bank.txt");
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			while(line!=null){
				content += line + "\n";
				line = reader.readLine();
			}
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("1 minute starts after pressing 1");
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		boolean start = false;
		while(true){
			try{
				String input = systemIn.readLine();
				if(input.equals("break")){
					break;
				}
			}
			catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		startTime = System.currentTimeMillis();

		endTime = System.currentTimeMillis();
		timePassed = endTime - startTime;

	}

}
