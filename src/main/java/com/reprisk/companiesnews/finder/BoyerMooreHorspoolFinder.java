package com.reprisk.companiesnews.finder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoyerMooreHorspoolFinder {

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