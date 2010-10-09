package com.graphsfm.stservice.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpellChecker2 {
    private static Logger log = Logger.getLogger(SpellChecker2.class.getName());
    private HashMap<String, ArrayList<Long>> windex;
    private Dictionary dict = Dictionary.getInstance();

    private void initFromMap(String... fnames) {
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
                        log.warning("Could not parse line: " + str
                                + ", tokens.length = " + tokens.length);
                        continue;
                    }

                    ArrayList<Long> plist = new ArrayList<Long>();
                    for (int i = 1; i < tokens.length; i++)
                        plist.add(Long.parseLong(tokens[i]));
                    windex.put(tokens[0], plist);
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error: " + fname, e);
            } finally {
            }
        }
    }

    public int[] findSeq(ArrayList<Long>... poslists) {
        int[] ptrs = new int[poslists.length];

        long v = poslists[0].get(ptrs[0]);
        for (int i = 1; i < poslists.length;) {
            // switch to binary search later if necessary.
            while (ptrs[i] < poslists[i].size()
                    && poslists[i].get(ptrs[i]) <= v) {
                ptrs[i]++;
            }
            if (ptrs[i] == poslists[i].size())
                return null;

            if (poslists[i].get(ptrs[i]) != v + 1) {
                ptrs[0]++;
                if (ptrs[0] >= poslists[0].size())
                    return null;
                v = poslists[0].get(ptrs[0]);
                i = 1;
            } else {
                v++;
                i++;
            }
        }
        return ptrs;
    }

    public String[] correctNGram(String[] input) {
        int n = input.length;
        String[][] clists = new String[n][]; // clists[i] is a list of
                                             // candidates - i.e. valid
                                             // mutations of input[i]
        for (int i = 0; i < n; i++) {
            HashSet<String> cset;
            if (input[i].length() <= 4)
                cset = dict.mutate1(input[i]);
            else
                cset = dict.mutate2(input[i]);
            if (cset.size() == 0)
                cset.add(input[i]);
            clists[i] = cset.toArray(new String[0]);
        }

        int[] ilist = new int[n]; // ilist[i] is the loop variable for clists[i]
        ArrayList<Long>[] poslists = new ArrayList[n];
        while (true) {
//            System.out.print("-- ");
//            for (int j = 0; j < n; j++) {
//                System.out.print(clists[j][ilist[j]] + " ");
//            }
//            System.out.println();
            
            // generate a permutation of words in clists.
            boolean alldone = true;
            for (int j = 0; j < n; j++) {
                ilist[j]++;
                if (ilist[j] == clists[j].length)
                    ilist[j] = 0;
                else {
                    alldone = false;
                    break;
                }
            }
            if (alldone)
                break;

            // search for the permutation in the training set.
            // for (int j = 0; j < n; j++)
            // poslists[j] = windex.get(clists[ilist[j]]);
            // if (findSeq(poslists) != null) {
            // }
        }
        
        int total = 1;
        for (int j = 0; j < n; j++) {
            total *= clists[j].length;
        }
        System.out.println("total = " + total);
        return null;
    }

    public static void main(String args[]) throws Exception {
        SpellChecker2 sp2 = new SpellChecker2();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String s = r.readLine();
            if (s == null)
                break;
            String[] tokens = s.split("[^a-zA-Z]+");
            sp2.correctNGram(tokens);
        }
    }
}
