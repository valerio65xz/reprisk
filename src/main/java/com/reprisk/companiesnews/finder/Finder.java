package com.reprisk.companiesnews.finder;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Finder {

    public Set<Integer> getCompaniesIds(char[] pattern, char[] text){
        Set<Integer> companiesIds = new HashSet<>();
        List<Integer> indexes = getStartIndexes(pattern, text);

        for (Integer index : indexes){
            StringBuilder idToBuild = new StringBuilder();
            boolean possibleId = false;
            for (int i = index-1; i>0; i--){
                if (text[i] == ';'){
                    possibleId=true;
                    i--;
                }
                if (possibleId){
                    if (text[i] >= 48 && text[i] <= 57){
                        idToBuild.append(text[i]);
                    }

                    if (text[i-1] != '\n' && (text[i-1] < 48 || text[i-1] > 57)){
                        idToBuild.setLength(0);
                        possibleId = false;
                    }
                    else if (text[i-1] == '\n'){
                        companiesIds.add(Integer.parseInt(idToBuild.reverse().toString()));
                        break;
                    }
                }
            }
        }

        return companiesIds;
    }

    public List<Integer> getStartIndexes(char[] pattern, char[] text){
        List<Integer> indexes = new ArrayList<>();

        int[] shift = new int[65536];
        int index = 0;

        for (int k = 0; k < 65536; k++) {
            shift[k] = pattern.length;
        }
        for (int k = 0; k < pattern.length - 1; k++){
            shift[pattern[k]] = pattern.length - 1 - k;
        }

        while (index != -1){
            index = search(pattern, text, shift, index);
            if (index != -1){
                indexes.add(index);
                index = index + pattern.length;
            }
        }

        return indexes;
    }

    private int search(char[] pattern, char[] text, int[] shift, int i) {
        int j;

        while ((i + pattern.length) <= text.length) {
            j = pattern.length - 1;

            while (text[i + j] == pattern[j]) {
                j -= 1;
                if (j < 0)
                    return i;
            }

            i = i + shift[text[i + pattern.length - 1]];
        }

        return -1;
    }

}