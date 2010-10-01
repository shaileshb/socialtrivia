package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpellChecker2 {
	private static Logger log = Logger.getLogger(SpellChecker2.class.getName());
	private HashMap<String,ArrayList<Long>> windex;

	public void initFromMap(String... fnames) {
		for (String fname : fnames) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(fname));
				while (true) {
					String str = br.readLine();
					if (str == null)
						break;
					
					// word pos1 pos2 pos3...
					String[] tokens = str.split("\\s+");
					if (tokens.length < 2) {
						log.warning("Could not parse line: " + str + ", tokens.length = " + tokens.length);
						continue;
					}

					ArrayList<Long> plist = new ArrayList<Long>();
					for (int i = 1; i < tokens.length; i++)
						plist.add(Long.parseLong(tokens[i]));
					windex.put(tokens[0], plist);
				}
			} catch (IOException e) {
				log.log(Level.WARNING, "Error: " + fname, e);
			}
			finally {}
		}
	}
	
	public int[] findSeq(ArrayList<Long>... poslists) {
		int[] ptrs = new int[poslists.length];
		
		long v = poslists[0].get(ptrs[0]);
		for (int i = 1; i < poslists.length;) {
			// switch to binary search later if necessary.
			while (ptrs[i] < poslists[i].size() && poslists[i].get(ptrs[i]) <= v) {
				ptrs[i]++;
			}
			if (ptrs[i] == poslists[i].size())
				return null;
			
			if (poslists[i].get(ptrs[i]) != v+1) {
				ptrs[0]++;
				if (ptrs[0] >= poslists[0].size())
					return null;
				v = poslists[0].get(ptrs[0]);
				i = 1;
			}
			else {
				v++;
				i++;
			}
		}
		return ptrs;
	}
	
	public void correct(String w, String wlist) {
	}
}
