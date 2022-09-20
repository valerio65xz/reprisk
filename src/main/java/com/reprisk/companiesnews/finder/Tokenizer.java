//package com.reprisk.companiesnews.finder;
//
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//@Service
//public class Tokenizer {
//
//    public List<String>[] tokenize(String path) throws FileNotFoundException {
//        char[][] companiesByLetter = new char[26][];
//        StringBuilder[] fileString = new StringBuilder[26];
//        for (int i=0; i<26; i++){
//            fileString[i] = new StringBuilder();
//        }
//
//        //Scanner scanner = new Scanner(new File("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\asd.txt"));
//        Scanner scanner = new Scanner(new File("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\160408_company_list.csv"));
//
//        scanner.nextLine();
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//
//
//            companiesByLetter[0].add(line);
//
//            if (line.indexOf(";"))
//        }
//    }
//
//}
