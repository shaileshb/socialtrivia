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
    private HashMap<String, ArrayList<FilePositions>> idx = new HashMap<String, ArrayList<FilePositions>>();
    private Dictionary dict = Dictionary.getInstance();
    private static WordIndex instance = new WordIndex();
    
    class FilePositions {
        String fileid;
        ArrayList<Long> poslist;
    }
    
    private WordIndex() {
        log.info("loading WordIndex");
        initFromText("big.txt");
        log.info("Done");
    }
    
    public static WordIndex getInstance() {
        return instance;
    }

    public void addWord(String str, String fileid, long pos) {
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
        
        ArrayList<FilePositions> fplist = idx.get(str);
        if (fplist == null) {
            fplist = new ArrayList<FilePositions>();
            idx.put(str, fplist);
        }

        if (fplist.size() == 0 || 
                ! fplist.get(fplist.size() - 1).fileid.equals(fileid)) {
            FilePositions fp = new FilePositions();
            fp.fileid = fileid;
            fp.poslist = new ArrayList<Long>();
            fplist.add(fp);
        }
        fplist.get(fplist.size() - 1).poslist.add(pos);
    }
    
    private void writeMap() {
        for (Map.Entry<String, ArrayList<FilePositions>> e : idx.entrySet()) {
            System.out.print(e.getKey());
            for (FilePositions fp : e.getValue()) {
                System.out.print(" " + fp.fileid);
                for (Long pos : fp.poslist)
                    System.out.print(" " + pos);
                System.out.print(" . ");
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
                        addWord(t, fname, pos++);
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
