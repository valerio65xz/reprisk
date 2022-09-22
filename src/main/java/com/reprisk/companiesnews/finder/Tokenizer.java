package com.reprisk.companiesnews.finder;

import com.reprisk.companiesnews.model.Company;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class Tokenizer {

    private final Set<String> stopWords = new HashSet<>();

    public Tokenizer(){
        stopWords.add("limited");
        stopWords.add("l.t.d.");
        stopWords.add("l.t.d");
        stopWords.add("ltd.");
        stopWords.add("ltd");
        stopWords.add("n.v.");
        stopWords.add("n.v");
        stopWords.add("nv.");
        stopWords.add("nv");
        stopWords.add("i.n.c.");
        stopWords.add("i.n.c");
        stopWords.add("inc.");
        stopWords.add("inc");
        stopWords.add("c.o.r.p.");
        stopWords.add("c.o.r.p");
        stopWords.add("corp.");
        stopWords.add("corp");
        stopWords.add("s.a.");
        stopWords.add("s.a");
        stopWords.add("sa.");
        stopWords.add("sa");
        //stopWords.add("group");
        stopWords.add("p.l.c.");
        stopWords.add("p.l.c");
        stopWords.add("plc.");
        stopWords.add("plc");
        stopWords.add("a.g.");
        stopWords.add("a.g");
        stopWords.add("ag.");
        stopWords.add("ag");
        stopWords.add("c.o.");
        stopWords.add("c.o");
        stopWords.add("co.");
        stopWords.add("co");
        stopWords.add("s.p.a.");
        stopWords.add("s.p.a");
        stopWords.add("spa.");
        stopWords.add("spa");
        stopWords.add("s.r.l.");
        stopWords.add("srl.");
        stopWords.add("s.r.l");
        stopWords.add("srl");
        stopWords.add("\"");
        stopWords.add("&");
        stopWords.add("o.y.j.");
        stopWords.add("o.y.j");
        stopWords.add("oyj.");
        stopWords.add("oyj");
        stopWords.add("s.e.");
        stopWords.add("s.e");
        stopWords.add("se.");
        stopWords.add("se");
        stopWords.add("g.m.b.h.");
        stopWords.add("g.m.b.h");
        stopWords.add("gmbh.");
        stopWords.add("gmbh");
        stopWords.add("the");
        stopWords.add("p.t.");
        stopWords.add("p.t");
        stopWords.add("pt.");
        stopWords.add("pt");
        stopWords.add("a.s.");
        stopWords.add("a.s");
        stopWords.add("as.");
        stopWords.add("a/s");
        stopWords.add("as");
        stopWords.add("b.h.d.");
        stopWords.add("b.h.d");
        stopWords.add("bhd.");
        stopWords.add("bhd");
        stopWords.add("t.b.k.");
        stopWords.add("t.b.k");
        stopWords.add("tbk.");
        stopWords.add("tbk");
        stopWords.add("p.c.l.");
        stopWords.add("p.c.l");
        stopWords.add("pcl.");
        stopWords.add("pcl");
        stopWords.add("b.v.");
        stopWords.add("b.v");
        stopWords.add("bv.");
        stopWords.add("bv");
        stopWords.add("o.a.o.");
        stopWords.add("o.a.o");
        stopWords.add("oao.");
        stopWords.add("oao");
        stopWords.add("l.l.c.");
        stopWords.add("l.l.c");
        stopWords.add("llc.");
        stopWords.add("llc");
        stopWords.add("a.b.");
        stopWords.add("a.b");
        stopWords.add("ab.");
        stopWords.add("ab");
        stopWords.add("o.y.");
        stopWords.add("o.y");
        stopWords.add("oy.");
        stopWords.add("oy");
        stopWords.add("o.h.g.");
        stopWords.add("o.h.g");
        stopWords.add("ohg.");
        stopWords.add("ohg");
        stopWords.add("m.b.h.");
        stopWords.add("m.b.h");
        stopWords.add("mbh.");
        stopWords.add("mbh");
        stopWords.add("k.g.");
        stopWords.add("k.g");
        stopWords.add("kg.");
        stopWords.add("kg");
        stopWords.add("l.p.");
        stopWords.add("lp.");
        stopWords.add("l.p");
        stopWords.add("lp");
        stopWords.add("l.l.p.");
        stopWords.add("l.l.p");
        stopWords.add("llp.");
        stopWords.add("llp");
        stopWords.add("s.d.n.");
        stopWords.add("s.d.n");
        stopWords.add("sdn.");
        stopWords.add("sdn");
        stopWords.add("j.s.c.");
        stopWords.add("j.s.c");
        stopWords.add("jsc.");
        stopWords.add("jsc");
        stopWords.add("d.d.");
        stopWords.add("d.d");
        stopWords.add("dd.");
        stopWords.add("dd");
        stopWords.add("a.d.");
        stopWords.add("a.d");
        stopWords.add("ad.");
        stopWords.add("ad");
        stopWords.add("p.t.e.");
        stopWords.add("p.t.e");
        stopWords.add("pte.");
        stopWords.add("pte");
        stopWords.add("p.t.y.");
        stopWords.add("p.t.y");
        stopWords.add("pty.");
        stopWords.add("pty");
        stopWords.add("s.l.");
        stopWords.add("s.l");
        stopWords.add("sl.");
        stopWords.add("sl");
        stopWords.add("l.t.d.a.");
        stopWords.add("l.t.d.a");
        stopWords.add("ltda.");
        stopWords.add("ltda");
        stopWords.add("s.a.s.");
        stopWords.add("s.a.s");
        stopWords.add("sas.");
        stopWords.add("sas");
    }

    public Set<String> findStopWords(String text){
        Set<String> stopWords = new HashSet<>();
        Map<String, Integer> wordCounter = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(text);

        while (tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            if (wordCounter.containsKey(token)){
                int counter = wordCounter.get(token) + 1;
                if (counter == 5){
                    stopWords.add(token);
                }
                wordCounter.put(token, counter);
            }
            else{
                wordCounter.put(token, 1);
            }
        }

        return stopWords;
    }

    public Set<Company> getCompaniesFromDataset(String filePath) throws IOException {
        Set<Company> cleanedCompanies = new LinkedHashSet<>();
        Scanner scanner = new Scanner(new File(filePath));

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Integer id = Integer.parseInt(line.substring(0, line.indexOf(';')));
            line = line.substring(line.indexOf(';')+1);

            List<String> companiesInALine = getCompaniesInALine(line);
            for(String company : companiesInALine){
                String companyCleaned = removeStopWords(company + " ");
                String finalCompany = lastClean(companyCleaned.trim());
                if (!finalCompany.isBlank()){
                    cleanedCompanies.add(new Company(id, finalCompany));
                }
            }
        }

        return cleanedCompanies;
    }

    private List<String> getCompaniesInALine(String line){
        List<String> companiesInALine = new ArrayList<>();

        String lineWithoutQuotes = line.replace("\"", "");
        StringBuilder builder = new StringBuilder(lineWithoutQuotes);

        while (lineWithoutQuotes.contains("(") && lineWithoutQuotes.contains(")")){
            int begin = lineWithoutQuotes.indexOf('(');
            int end = lineWithoutQuotes.indexOf(')');

            extractStrings(lineWithoutQuotes.substring(begin+1, end), companiesInALine);

            builder.replace(begin, end+2, "");
            lineWithoutQuotes = builder.toString();
        }

        extractStrings(lineWithoutQuotes, companiesInALine);

        return companiesInALine;
    }

    private void extractStrings(String line, List<String> companiesInALine){
        if (line.contains(",")){
            int colon = line.lastIndexOf(',');
            companiesInALine.add(line.substring(colon+1));
            line = line.substring(0, colon);
        }

        while (line.contains(";") || line.contains(" or ")){
            int semicolon = line.indexOf(';');
            int or = line.indexOf(" or ");

            if (semicolon != -1 && or != -1){
                if (semicolon < or){
                    companiesInALine.add(line.substring(0, semicolon));
                    line = getStringAfterSemicolon(line, semicolon);
                }
                else{
                    companiesInALine.add(line.substring(0, or));
                    line = getStringAfterOr(line, or);
                }
            }
            else if (semicolon != -1){
                companiesInALine.add(line.substring(0, semicolon));
                line = getStringAfterSemicolon(line, semicolon);
            }
            else {
                companiesInALine.add(line.substring(0, or));
                line = getStringAfterOr(line, or);
            }
        }
        companiesInALine.add(line);
    }

    private String getStringAfterSemicolon(String string, int index){
        if (index+2 <= string.length()){
            return string.substring(index+2);
        }
        else{
            return "";
        }
    }

    private String getStringAfterOr(String string, int index){
        if (index+4 <= string.length()){
            return string.substring(index+4);
        }
        else{
            return "";
        }
    }

    private String removeStopWords(String toClean){
        StringBuilder clean = new StringBuilder();
        int index = 0;

        while (index < toClean.length()) {
            int nextIndex = toClean.indexOf(" ", index);
            if (nextIndex == -1) {
                nextIndex = toClean.length() - 1;
            }
            String word = toClean.substring(index, nextIndex);
            if (!stopWords.contains(word.toLowerCase())) {
                clean.append(word);
                if (nextIndex < toClean.length()) {
                    clean.append(toClean.charAt(nextIndex));
                }
            }
            index = nextIndex + 1;
        }

        return clean.toString();
    }

    private String lastClean(String line){
        line = line.replace("formerly known as ", "");
        line = line.replace("Formerly known as ", "");
        line = line.replace("formerly known ", "");
        line = line.replace("Formerly known ", "");
        line = line.replace("formerly ", "");
        line = line.replace("Formerly ", "");
        line = line.replace("also known as ", "");
        line = line.replace("Also known as ", "");
        line = line.replace("known as ", "");
        line = line.replace("Known as ", "");
        line = line.replace("also ", "");
        line = line.replace("Also ", "");
        line = line.replace("please refer to either ", "");
        line = line.replace("Please refer to either ", "");
        line = line.replace("please refer to ", "");
        line = line.replace("Please refer to ", "");
        line = line.replace("refer to ", "");
        line = line.replace("Refer to ", "");
        line = line.replace("SAB de CV", "");
        line = line.replace("SA de CV", "");
        return line;
    }

}
