package com.reprisk.companiesnews.finder;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleParser {

    public Set<String> getPotentialCompanies(String article){
        Set<String> potentialCompanies = new LinkedHashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(article);
        StringBuilder company = new StringBuilder();
        boolean canStart = false;
        boolean possibleWordDetected = false;

        while (tokenizer.hasMoreTokens()){
            String word = tokenizer.nextToken();

            if (canStart){
                String cleanWord = word.replaceAll("[\"()<>\\[\\],.:?;-]", "");

                if (word.contains("(")){
                    if (company.toString().length() > 3){
                        potentialCompanies.add(company.toString());
                    }

                    possibleWordDetected = false;
                    company.setLength(0);
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
                    if (company.toString().length() > 3){
                        potentialCompanies.add(company.toString());
                    }

                    possibleWordDetected = false;
                    company.setLength(0);
                }
                if (!cleanWord.isBlank() && !wordContainsSpecialChars(word)) {
                    if (possibleWordDetected) {
                        if (!wordStartsWithUpperCase(cleanWord)) {
                            if (company.toString().length() > 3) {
                                potentialCompanies.add(company.toString());
                            }

                            possibleWordDetected = false;
                            company.setLength(0);
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
                    if (company.toString().length() > 3){
                        potentialCompanies.add(company.toString());
                    }

                    possibleWordDetected = false;
                    company.setLength(0);
                }




//                if (word.equals("-") || word.contains("(") || word.contains(")") || word.contains(",") || word.contains(";") || word.contains(".")){
//                    possibleWordDetected = false;
//                    if (company.toString().length() > 3){
//                        potentialCompanies.add(company.toString());
//                    }
//
//                    company.setLength(0);
//                }
//                if (!cleanWord.isBlank()){
//                    if (possibleWordDetected){
//                        if (word.contains("(") || word.contains(")") || word.contains(",") || word.contains(";") || word.contains(".")){
//                            possibleWordDetected = false;
//                            if (company.toString().length() > 3){
//                                potentialCompanies.add(company.toString());
//                            }
//
//                            company.setLength(0);
//
//                            if (cleanWord.charAt(0) >= 65 && cleanWord.charAt(0) <= 90){
//                                possibleWordDetected = true;
//                                company.append(cleanWord);
//                            }
//                        }
//                        else{
//                            if (cleanWord.charAt(0) < 65 || cleanWord.charAt(0) > 90){
//                                possibleWordDetected = false;
//                                if (company.toString().length() > 3){
//                                    potentialCompanies.add(company.toString());
//                                }
//
//                                company.setLength(0);
//                            }
//                            else{
//                                company.append(" ").append(cleanWord);
//                            }
//                        }
//                    }
//                    else{
//                        if (cleanWord.charAt(0) >= 65 && cleanWord.charAt(0) <= 90){
//                            possibleWordDetected = true;
//                            company.append(cleanWord);
//                        }
//                    }
//                }
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
        return word.charAt(0) >= 65 && word.charAt(0) <= 90;
    }

    private boolean wordContainsSpecialChars(String word){
        return word.contains("(") ||
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
