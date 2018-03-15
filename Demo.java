package readingWarNPeace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Demo {

	public static void main(String[] args) {
		
		try {
			File f = new File("warnpeace.txt");
			Scanner sc = new Scanner(f);
			//		String text = "And then he said \" = I'm a virgin!\"";
			//		String[] result = text.toLowerCase().split("[^a-z’]+");
			//		System.out.println(Arrays.toString(result));
			TreeMap<String,Integer> swords = new TreeMap<String, Integer>();
			TreeMap<String,Integer> symbolsMap = new TreeMap<>();
			int sumSymbols = 0;
			try {
				
				while (sc.hasNextLine()) {
					String row = sc.nextLine();
					String[] words = row.toLowerCase().split("[^a-z’']+");
					for (String s : words) {
						if (swords.containsKey(s)) {
							swords.put(s, swords.get(s) + 1);
						} else {
							swords.put(s, 1);
						}
					}
					
					
					
					String[] symbols = row.toLowerCase().split("[a-z ]+");
					for(String s:symbols){
						sumSymbols+=s.length();
						if(s.length()==1){
							if (symbolsMap.containsKey(s)) {
								symbolsMap.put(s, symbolsMap.get(s) + 1);
							} else {
								symbolsMap.put(s, 1);
							}
						}
						else{
							for(int i=0;i<s.length();i++){
								String s2=String.valueOf(s.charAt(i));
								if (symbolsMap.containsKey(s2)) {
									symbolsMap.put(s2, symbolsMap.get(s2) + 1);
								} else {
									symbolsMap.put(s2, 1);
								}
							}
						}
					}
				} 
			} finally {
				sc.close();
			}
			
			int sum=0;
			TreeMap<Integer,ArrayList<String>> swords2 = new TreeMap<Integer,ArrayList<String>>();
			for(Entry<String,Integer> e : swords.entrySet()){
				sum+= e.getValue();
				if(swords2.containsKey(e.getKey().length())){
					swords2.get(e.getKey().length()).add(e.getKey());
				}else{
					swords2.put(e.getKey().length(),new ArrayList());
					swords2.get(e.getKey().length()).add(e.getKey());
				}
			}
			
			int current = 0;
			
			for(Entry<Integer,ArrayList<String>> e : swords2.entrySet()){
				current = 0;
				f = new File(e.getKey() + " words.txt");
				PrintStream ps = new PrintStream(f);
				
				try{
					for(String s : e.getValue()){
						current+= swords.get(s);
						ps.println(s);
					}
				}finally{
					ps.close();
				}
				System.out.print("Words with "+ e.getKey() + " letters are "+ current + " and percentage ");
				System.out.format("%.4f%n", (double)(current*100)/sum); 
			}

			//		f= new File("swords.txt");
			//		PrintStream ps = new PrintStream(f);
			//		for(Entry<String,Integer> e : swords.entrySet()){
			//			ps.println(e.getKey() + " - " + e.getValue());
			//		}
			//		ps.close();

			System.out.println();
			System.out.println();
			System.out.println();
			
			//Symbol stuff
			TreeSet<Entry<String,Integer>> sortedSymbols = new TreeSet<>((o1,o2)->o2.getValue()-o1.getValue());
			for(Entry<String,Integer> e :symbolsMap.entrySet()){
				
				sortedSymbols.add(e);
			}
			
			for(Entry<String,Integer> e: sortedSymbols){
				System.out.print(e.getKey() + " - "+ e.getValue() + " percentage ");
				System.out.format("%.4f%n", (double)(e.getValue()*100)/sumSymbols); 
			}
			
			System.out.println("Done");
		}
		catch (IOException e){
			System.out.println("Something wrong with the files");
		}
	}
}
