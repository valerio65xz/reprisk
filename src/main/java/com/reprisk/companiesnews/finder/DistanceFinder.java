package com.reprisk.companiesnews.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DistanceFinder {

    @Autowired
    private LevenshteinDistance levenshteinDistance;

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
                int[] lineIndexes = calculateLineLength(text, index);
                String line = String.valueOf(text, lineIndexes[0], lineIndexes[1]);
                String patternString = String.valueOf(pattern);
                if (levenshteinDistance.calculate(patternString, line) < 3){
                    indexes.add(index);
                }
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

    private int[] calculateLineLength(char[] text, int index){
        int endLineIndex = index;
        int startLineIndex = index;
        char endLine = text[endLineIndex];
        char startLine = text[startLineIndex];
        while(endLine != '\n' && endLine != '\r'){
            endLineIndex++;
            endLine = text[endLineIndex];
        }
        while(startLine != ';'){
            startLineIndex--;
            startLine = text[startLineIndex];
        }

        int[] indexes = new int[2];
        indexes[0] = startLineIndex+1;
        indexes[1] = endLineIndex-startLineIndex-1;

        return indexes;
    }

}
