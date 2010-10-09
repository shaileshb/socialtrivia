package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordIndex {
    private static Logger log = Logger.getLogger(Dictionary.class.getName());
    private HashMap<String, ArrayList<Long>> idx = new HashMap<String, ArrayList<Long>>();
    private Dictionary dict = Dictionary.getInstance();
    private static WordIndex instance = new WordIndex();
    
    private WordIndex() {
        log.info("loading WordIndex");
        initFromText("big.txt");
        log.info("Done");
    }
    
    public static WordIndex getInstance() {
        return instance;
    }

    public void addWord(String str, long pos) {
        /*
         * Filter out certain strings. e.g. words that are either very rare
         * (e.g. spelling mistakes) or extremely common "stop words".
         * http://en.wikipedia.org/wiki/Stop_words
         */
        if (str.length() < 3)
            return;
        int freq = dict.getFrequency(str);
        if (freq == 0 || freq > 90000)
            return;
        
        ArrayList<Long> poslist = idx.get(str);
        if (poslist == null) {
            poslist = new ArrayList<Long>();
            idx.put(str, poslist);
        }
        poslist.add(pos);
    }
    
    private void writeMap() {
        for (Map.Entry<String, ArrayList<Long>> e : idx.entrySet()) {
            System.out.print(e.getKey());
            for (Long pos : e.getValue()) {
                System.out.print(" " + pos);
            }
            System.out.println();
        }
    }
    
    public void initFromText(String... fnames) {
        long pos = 0;
        for (String fname : fnames) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fname));
                pos += 1000; // Create a discontinuity between last word of Nth file and first word of (N+1)th file.
                while (true) {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    
                    String[] tokens = str.split("[^a-zA-Z]+");
                    for (String t : tokens) {
                        t = new String(t.toLowerCase());
                        addWord(t, pos++);
                    }
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error: " + fname, e);
            } finally {
            }
        }
    }
    
    public static void main(String args[]) {
        getInstance().writeMap();
    }
}
