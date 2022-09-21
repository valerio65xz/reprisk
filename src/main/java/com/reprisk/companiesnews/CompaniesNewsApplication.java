package com.reprisk.companiesnews;

import com.reprisk.companiesnews.finder.ArticleParser;
import com.reprisk.companiesnews.finder.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CompaniesNewsApplication implements CommandLineRunner {

    @Autowired
    private Finder finder;

    @Autowired
    private ArticleParser parser;

    public static void main(String[] args) {
        SpringApplication.run(CompaniesNewsApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        //Path filePath = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\asd.txt");
        Path filePath = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\160408_company_list.csv");
        String content = Files.readString(filePath);

        Path filePath2 = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\data\\000D-2695-EAE6-7998.xml");
        String content2 = Files.readString(filePath2);

        char[] charArray = content.toCharArray();
        //System.out.println("Dim string: " + content.length());
        //System.out.println(content);
        //System.out.println("Occorrenze: " + StringUtils.countOccurrencesOf(content, "Uber"));
        //System.out.println("Dim string: " + content.length());
        //System.out.println("Dim char array: " + charArray.length);

        long one = System.nanoTime();
        Set<String> potentialCompanies = parser.getPotentialCompanies(content2);

        for (String potentialCompany : potentialCompanies){
            finder.getCompaniesIds(potentialCompany.toCharArray(), charArray);
        }

        //Set<String> startIndexes = finder.getCompaniesIds("Coca-Cola".toCharArray(), charArray);
        //System.out.println("boyer: " + startIndexes);
        //System.out.println("counts: " + startIndexes.size());


        //System.out.println("knuth: " + kmpFinder.search("suca".toCharArray(), charArray));

        long two = System.nanoTime();
        System.out.println("Tempo impiegato: " + (two - one));
    }

}
