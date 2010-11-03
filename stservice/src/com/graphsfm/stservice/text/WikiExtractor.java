package com.graphsfm.stservice.text;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Extracts the text from wikipedia dump and writes the output to an intermediate file.
 *  
 * @author shaileshb
 */
public class WikiExtractor {
    private static final String[] TEXT_START = { "text" };
    private static final String[] PAGE_TITLE = { "title" };

    private static final Pattern TEXT_PATTERN = Pattern.compile(".*[a-zA-Z]+.*", Pattern.DOTALL); 
        
    private static final String REDIRECT = "(^#REDIRECT.*)";
    private static final String URL = "(https?\\:\\/\\/\\S*)";
    private static final String WIKIMETA1 = "(\\[\\[[^:\\s]*?:.*?\\]\\])";
    private static final String REFBLOCK = "(<ref[^>]*?/>)|(<ref.*?</ref>)";
    private static final String MATHBLOCK = "(<math[^>]*?/>)|(<math.*?</math>)";
    private static final String FOOTERS = "(=*See also.*)|(=*References.*)";
    private static final Pattern LINK_OR_REF = Pattern.compile(MATHBLOCK + "|"
            + REFBLOCK + "|" + WIKIMETA1 + "|" + URL + "|" + FOOTERS + "|"
            + REDIRECT, Pattern.DOTALL);

    private static final Pattern WIKILINK = Pattern.compile(
            "\\[\\[([^\\]]*?\\|)?(.*?)\\]\\]", Pattern.DOTALL);

    private XMLStreamReader reader;
    private DataOutputStream outstream;

    public WikiExtractor(String infile, String outfile) throws IOException,
            XMLStreamException {
        XMLInputFactory f = XMLInputFactory.newInstance();
        reader = f.createXMLStreamReader(new FileInputStream(infile));
        this.outstream = new DataOutputStream(new FileOutputStream(outfile));
    }

    public String findElement(String[] names) {
        try {
            while (true) {
                int event = reader.next();
                if (event == XMLStreamConstants.END_DOCUMENT)
                    return null;
                if (event == XMLStreamConstants.START_ELEMENT) {
                    String lname = reader.getLocalName();
                    for (String name : names) {
                        if (lname.equalsIgnoreCase(name))
                            return name;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void filterNestedBraces(StringBuilder sb) {
        int level = 0;
        int start = -1;
        for (int i = 0; i < sb.length(); i++) {
            switch (sb.charAt(i)) {
            case '{':
                if (level == 0)
                    start = i;
                level++;
                break;
            case '}':
                level--;
                if (level == 0) {
                    for (int j = start; j <= i; j++)
                        sb.setCharAt(j, ' ');
                }
            }
        }
    }

    private String filterText(StringBuilder sb) {
        Matcher matcher = LINK_OR_REF.matcher(sb);
        while (matcher.find()) {
            for (int i = matcher.start(); i < matcher.end(); i++)
                sb.setCharAt(i, ' ');
        }

        matcher = WIKILINK.matcher(sb);
        while (matcher.find()) {
            if (matcher.groupCount() == 2) {
                for (int i = matcher.start(1); i < matcher.end(1); i++) {
                    sb.setCharAt(i, ' ');
                }
            }
        }
        filterNestedBraces(sb);
        return sb.toString();
    }

    public String getTitleText() {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                int event = reader.next();
                if (event == XMLStreamConstants.CHARACTERS)
                    sb.append(reader.getText());
                else {
                    return sb.toString();
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                int event = reader.next();
                if (event == XMLStreamConstants.CHARACTERS)
                    sb.append(reader.getText());
                else {
                    return filterText(sb);
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(long fileid, String text) throws IOException {
        if (! TEXT_PATTERN.matcher(text).matches())
            return;
        
        outstream.writeLong(fileid);
        byte[] bytes = text.getBytes();
        outstream.writeLong(bytes.length);
        outstream.write(bytes);
    }
    
    public void process() throws XMLStreamException, IOException {
        long len = 0;
        long last = 0;
        long fileid = 1;
        while (true) {
            String e = findElement(TEXT_START);
            if (e == null)
                break;
            String t = getText();
            writeFile(fileid++, t);

            len += t.length();
            if (len > last + 1024 * 1024) {
                System.err.printf("%d Mb processed\n", len / 1024 / 1024);
                last = len;
            }
        }
    }

    public static void main(String args[]) throws Exception {
        WikiExtractor w = new WikiExtractor(args[0], args[1]);
        w.process();
    }
}
