package com.reprisk.companiesnews.finder;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleParser {

    public Set<String> getPotentialCompanies(String article){
        Set<String> potentialCompanies = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(article);
        StringBuilder company = new StringBuilder();
        boolean canStart = false;
        boolean possibleWordDetected = false;

        while (tokenizer.hasMoreTokens()){
            String word = tokenizer.nextToken();

            if (canStart){
                String cleanWord = word.replaceAll("[\"()<>\\[\\],.:?;-]", "");

                if (word.contains("(")){
                    restartToSearchCompany(potentialCompanies, company);
                    possibleWordDetected = false;
                }

                if (wordContainsSpecialChars(word)){
                    if (wordStartsWithUpperCase(cleanWord)){
                        if (company.isEmpty()){
                            company.append(cleanWord);
                        }
                        else{
                            company.append(" ").append(cleanWord);
                        }
                    }
                    restartToSearchCompany(potentialCompanies, company);
                    possibleWordDetected = false;
                }
                if (!cleanWord.isBlank() && !wordContainsSpecialChars(word)) {
                    if (possibleWordDetected) {
                        if (!wordStartsWithUpperCase(cleanWord)) {
                            restartToSearchCompany(potentialCompanies, company);
                            possibleWordDetected = false;
                        } else {
                            company.append(" ").append(cleanWord);
                        }
                    }
                    else{
                        if (wordStartsWithUpperCase(cleanWord)){
                            possibleWordDetected = true;
                            company.append(cleanWord);
                        }
                    }
                }
                else if (cleanWord.isBlank()){
                    restartToSearchCompany(potentialCompanies, company);
                    possibleWordDetected = false;
                }
            }
            else{
                if (word.contains("CDATA")){
                    canStart = true;
                }
            }
        }

        return potentialCompanies;
    }

    private boolean wordStartsWithUpperCase(String word){
        if (word == null || word.isBlank()){
            return false;
        }
        else return word.charAt(0) >= 65 && word.charAt(0) <= 90;

    }

    private boolean wordContainsSpecialChars(String word){
        if (word == null || word.isBlank()){
            return false;
        }
        else return word.contains("(") ||
                word.contains(")") ||
                word.contains(",") ||
                word.contains(";") ||
                word.contains(".") ||
                word.contains(":") ||
                word.contains("?");
    }

    private void restartToSearchCompany(Set<String> potentialCompanies, StringBuilder company){
        if (company.toString().length() > 3){
            potentialCompanies.add(company.toString());
        }

        company.setLength(0);
    }

}
