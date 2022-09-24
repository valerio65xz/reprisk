package com.reprisk.companiesnews.parser;

import com.reprisk.companiesnews.filter.WordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class DatasetParser {

    private final WordFilter wordFilter;

    @Autowired
    public DatasetParser(WordFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    public Map<String, Integer> getCompaniesFromDataset(String filePath) throws IOException {
        Map<String, Integer> cleanedCompanies = new HashMap<>();
        Scanner scanner = new Scanner(new File(filePath));

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Integer id = Integer.parseInt(line.substring(0, line.indexOf(';')));
            line = line.substring(line.indexOf(';')+1);

            List<String> companiesInALine = getCompaniesInALine(line);
            for(String company : companiesInALine){
                String companyCleaned = wordFilter.filter(company + " ");
                String finalCompany = wordFilter.filterComplexWords(companyCleaned.trim());
                if (!finalCompany.isBlank()){
                    cleanedCompanies.put(finalCompany, id);
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

}
