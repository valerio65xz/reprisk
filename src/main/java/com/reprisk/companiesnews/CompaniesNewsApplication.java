package com.reprisk.companiesnews;

import com.reprisk.companiesnews.finder.BoyerMooreHorspoolFinder;
import com.reprisk.companiesnews.finder.KnuthMorrisPrattFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CompaniesNewsApplication implements CommandLineRunner {

    @Autowired
    private BoyerMooreHorspoolFinder bmhFinder;

    @Autowired
    private KnuthMorrisPrattFinder kmpFinder;

    public static void main(String[] args) {
        SpringApplication.run(CompaniesNewsApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
//        Scanner scanner = new Scanner(new File("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\asd.txt"));
//        //Scanner scanner = new Scanner(new File("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\160408_company_list.csv"));
//        StringBuilder fileString = new StringBuilder();
//
//        fileString.append(scanner.nextLine());
//        while (scanner.hasNextLine()) {
//            fileString.append(scanner.nextLine());
//        }
//
//        char[] fileCharArray = new char[fileString.length()];
//        fileString.getChars(0, fileString.length(), fileCharArray, 0);
//
//        System.out.println("Dim string builder: " + fileString.length());
//        System.out.println("Dim char array: " + fileCharArray.length);
//        System.out.println("boyer: " + bmhFinder.search("suca".toCharArray(), fileCharArray));
//        System.out.println("knuth: " + kmpFinder.search("suca".toCharArray(), fileCharArray));
//
//        for (char c : fileCharArray){
//            System.out.print(c);
//        }


        //long one = System.nanoTime();
        Path filePath = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\asd.txt");
        //Path filePath = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\160408_company_list.csv");
        String content = Files.readString(filePath);
        char[] charArray = content.toCharArray();
        //System.out.println("Dim string: " + content.length());
        //System.out.println(content);
        //System.out.println("Occorrenze: " + StringUtils.countOccurrencesOf(content, "Uber"));

        System.out.println("Dim string: " + content.length());
        System.out.println("Dim char array: " + charArray.length);
        long one = System.nanoTime();
        List<Integer> startIndexes = bmhFinder.getStartIndexes("Uber ".toCharArray(), charArray);
        System.out.println("boyer: " + startIndexes);
        System.out.println("knuth: " + kmpFinder.search("suca".toCharArray(), charArray));

        long two = System.nanoTime();
        System.out.println("Tempo impiegato: " + (two - one));
    }

}
