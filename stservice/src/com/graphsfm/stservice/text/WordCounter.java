package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordCounter {
    private static Logger log = Logger.getLogger(WordCounter.class.getName());
    private HashMap<String, Integer> words = new HashMap<String, Integer>(
            10 * 1024 * 1024);

    private void countWordFrequencies(String mstr,
            HashMap<String, Integer> model) {
        String[] tokens = mstr.split("[^a-zA-Z]+");
        for (String t : tokens) {
            if (t.length() < 3)
                continue;
            // t = t.toLowerCase();
            t = new String(t.toLowerCase()); // create a new String.. otherwise
                                             // we end up keeping references
                                             // to the big char arrays
                                             // allocated by the buffered
                                             // reader!
            Integer count = model.get(t);
            if (count == null)
                count = 1;
            else
                count = count + 1;
            model.put(t, count);
        }
    }

    public void init(String... fnames) {
        long count = 0;
        long last = 0;
        for (String fname : fnames) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fname));
                while (true) {
                    String str = br.readLine();
                    if (str == null)
                        break;
                    countWordFrequencies(str, words);
                    count += str.length();
                    if (count > last + 1024 * 1024) {
                        System.err.printf(
                                "processed = %d Mb, word count = %d\n",
                                count / 1024 / 1024, words.size());
                        last = count;
                    }
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error: " + fname, e);
            } finally {
            }
        }
    }

    private void writeMap() {
        for (Map.Entry<String, Integer> e : words.entrySet()) {
            System.out.print(e.getValue());
            System.out.print(" ");
            System.out.println(e.getKey());
        }
    }

    public static void main(String[] args) {
        WordCounter wc = new WordCounter();
        wc.init(args);
        wc.writeMap();
    }
}
