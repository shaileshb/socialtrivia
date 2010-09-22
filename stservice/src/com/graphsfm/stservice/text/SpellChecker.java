package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpellChecker {
	private static Logger log = Logger.getLogger(SpellChecker.class.getName());
	private HashMap<String,Integer> words = new HashMap<String, Integer>();
	
	public SpellChecker() {
	}
	
	private void countWordFrequencies(String mstr, HashMap<String,Integer> model) {
		String[] tokens = mstr.split("[^a-zA-Z]+");
		for (String t : tokens) {
			t = new String(t.toLowerCase());
			Integer count = model.get(t);
			if (count == null)
				count = 1;
			else
				count = count + 1;
			model.put(t, count);
		}
	}
	
	public void initFromMap(String... fnames) {
		for (String fname : fnames) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(fname));
				while (true) {
					String str = br.readLine();
					if (str == null)
						break;
					String[] tokens = str.split("\\s*");
					if (tokens.length != 2) {
						log.warning("Could not parse line: " + str);
						continue;
					}
					Integer count = words.get(tokens[1]);
					if (count == null)
						words.put(tokens[1], Integer.parseInt(tokens[0]));
					else
						words.put(tokens[1], count + Integer.parseInt(tokens[0]));
				}
			} catch (IOException e) {
				log.log(Level.WARNING, "Error: " + fname, e);
			}
			finally {}
		}
	}
	
	public void initFromText(String...fnames) {
		for (String fname : fnames) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(fname));
				while (true) {
					String str = br.readLine();
					if (str == null)
						break;
					countWordFrequencies(str, words);
				}
			} catch (IOException e) {
				log.log(Level.WARNING, "Error: " + fname, e);
			}
			finally {}
		}
	}
	
	protected void debugDump() {
		for (Map.Entry<String, Integer> e : words.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}
	
	private void mutate1(HashSet<String> mset, String str) {
		for (int i = 0; i < str.length(); i++) {
			mset.add(str.substring(0, i) + str.substring(i+1)); // delete
			if (i != str.length() - 1)
				mset.add(str.substring(0, i) + str.charAt(i+1) + str.charAt(i) + str.substring(i+2)); // transpose
			for (char c = 'a'; c <= 'z'; c++) {
				mset.add(str.substring(0, i) + c + str.substring(i+1)); // substitute
				mset.add(str.substring(0, i) + c + str.substring(i)); // insert
			}
		}
		for (char c = 'a'; c <= 'z'; c++) {
			mset.add(str + c); // insert
		}
	}
	
	private void includeKnownWords(HashSet<String> mset, String candidate, HashMap<String,Integer>...models) {
		for (HashMap<String,Integer> model : models) {
			if (model.containsKey(candidate)) {
				mset.add(candidate);
				return;
			}
		}
	}
	
	private void mutate2(HashSet<String> mset, String str, HashMap<String,Integer>...models) {
		for (int i = 0; i < str.length(); i++) {
			String mutation = str.substring(0, i) + str.substring(i+1); // delete
			includeKnownWords(mset, mutation, models);
			if (i != str.length() - 1) {
				mutation = str.substring(0, i) + str.charAt(i+1) + str.charAt(i) + str.substring(i+2); // transpose
				includeKnownWords(mset, mutation, models);
			}
			for (char c = 'a'; c <= 'z'; c++) {
				mutation = str.substring(0, i) + c + str.substring(i+1); // substitute
				includeKnownWords(mset, mutation, models);
				
				mutation = str.substring(0, i) + c + str.substring(i); // insert
				includeKnownWords(mset, mutation, models);
			}
		}
		for (char c = 'a'; c <= 'z'; c++) {
			includeKnownWords(mset, str + c, models); // insert
		}
	}
	
	public String correct(String in, HashMap<String,Integer>...models) {
		for (HashMap<String, Integer> model : models) {
			if (model.containsKey(in))
				return in;
		}
		
		HashSet<String> m2set = null;
		HashSet<String> m1set = new HashSet<String>();
		mutate1(m1set, in);
		String ret = null;
		int score = 0;
		for (HashMap<String,Integer> model : models) {
			for (String candidate : m1set) {
				Integer candidateScore = model.get(candidate);
				if (candidateScore != null && candidateScore > score) {
					ret = candidate;
					score = candidateScore;
				}
			}
			if (ret != null)
				return ret;
			
			if (m2set == null) {
				m2set = new HashSet<String>();
				for (String m1str : m1set) {
					mutate2(m2set, m1str, models);
				}
			}
			
			for (String candidate : m2set) {
				Integer candidateScore = model.get(candidate);
				if (candidateScore != null && candidateScore > score) {
					ret = candidate;
					score = candidateScore;
				}
			}
			if (ret != null)
				return ret;
		}
		return in;
	}
	
	public String correct(String in) {
		return correct(in, words);
	}
	
	public static void main(String...args) throws Exception {
		SpellChecker m = new SpellChecker();
		if (args[0].equals("-t"))
			m.initFromText(args[1]);
		else
			m.initFromMap(args[0]);
		
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String s = r.readLine();
			if (s == null)
				break;
			System.out.println(m.correct(s));
		}
	}
}
