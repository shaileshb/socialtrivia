package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {
    private static Logger log = Logger.getLogger(Dictionary.class.getName());
    private HashMap<String, Integer> dict = new HashMap<String, Integer>();
    private static Dictionary instance = new Dictionary();
    
    private Dictionary() {
        log.info("Loading dictionary");
        initFromMap("sortedwords.txt");
        log.info("Done.");
    }
    
    public static Dictionary getInstance() {
        return instance;
    }

    protected void addWord(String token, int count) {
        Integer prevcount = dict.get(token);
        if (prevcount == null)
            dict.put(token, count);
        else
            dict.put(token, count + prevcount);
    }

    protected void addWord(String token) {
        addWord(token, 1);
    }

    protected void addWord(String str, HashSet<String> mset, boolean keepAll) {
        if (keepAll || dict.containsKey(str)) {
            mset.add(str);
        }
    }

    protected void initFromMap(String... fnames) {
        for (String fname : fnames) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fname));
                while (true) {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    String[] tokens = str.split("\\s+");
                    if (tokens.length != 2) {
                        log.warning("Could not parse line: " + str
                                + ", tokens.length = " + tokens.length);
                        continue;
                    }
                    addWord(tokens[1], Integer.parseInt(tokens[0]));
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error: " + fname, e);
            }
        }
    }

    private HashSet<String> mutate1(String str, HashSet<String> mset,
            boolean keepAll) {
        for (int i = 0; i < str.length(); i++) {
            addWord(str.substring(0, i) + str.substring(i + 1), mset, keepAll); // delete
            if (i != str.length() - 1)
                addWord(str.substring(0, i) + str.charAt(i + 1) + str.charAt(i)
                        + str.substring(i + 2), mset, keepAll); // transpose
            for (char c = 'a'; c <= 'z'; c++) {
                addWord(str.substring(0, i) + c + str.substring(i + 1), mset,
                        keepAll); // substitute
                addWord(str.substring(0, i) + c + str.substring(i), mset,
                        keepAll); // insert
            }
        }
        for (char c = 'a'; c <= 'z'; c++) {
            addWord(str + c, mset, keepAll); // insert
        }
        return mset;
    }

    public HashSet<String> mutate1(String str) {
        HashSet<String> mstr = new HashSet<String>();
        return mutate1(str, mstr, false);
    }

    public HashSet<String> mutate2(String str) {
        HashSet<String> tempset = new HashSet<String>();
        mutate1(str, tempset, true);
        HashSet<String> mset = new HashSet<String>();
        for (String str1 : tempset) {
            addWord(str1, mset, false);
            mutate1(str1, mset, false);
        }
        return mset;
    }
}
