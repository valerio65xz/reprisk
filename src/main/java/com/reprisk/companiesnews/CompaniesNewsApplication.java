package com.reprisk.companiesnews;

import com.reprisk.companiesnews.finder.ArticleTokenizer;
import com.reprisk.companiesnews.finder.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SpringBootApplication
public class CompaniesNewsApplication implements CommandLineRunner {

    @Autowired
    private Finder finder;

    @Autowired
    private ArticleTokenizer parser;

    private final String pathOfArticles = "C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\data";
    private final String pathOfDataset = "C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\160408_company_list.csv";

    public static void main(String[] args) {
        SpringApplication.run(CompaniesNewsApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        //Path filePath = Path.of("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\asd.txt");
        Path filePath = Path.of(pathOfDataset);
        File[] files = new File(pathOfArticles).listFiles();
        List<Path> articlesPaths = new ArrayList<>();
        String content = Files.readString(filePath);
        char[] companiesDataset = content.toCharArray();
        Set<Integer> companies = new HashSet<>();

        //int i=0;
        for (File file : files) {
            if (file.isFile()) {
                articlesPaths.add(Path.of(pathOfArticles + "\\" + file.getName()));
                //i++;
                //if (i==12) break;
            }
        }



        long one = System.nanoTime();
//        for (Path articlePath : articlesPaths){
//            String articleContent = Files.readString(articlePath);
//            Set<String> potentialCompanies = parser.getPotentialCompanies(articleContent);
//
//            for (String potentialCompany : potentialCompanies){
//                companies.addAll(finder.getCompaniesIds(potentialCompany.toCharArray(), companiesDataset));
//            }
//        }

        articlesPaths.parallelStream().forEach((articlePath) -> {
            String articleContent;
            try {
                articleContent = Files.readString(articlePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Set<String> potentialCompanies = parser.getPotentialCompanies(articleContent);

            for (String potentialCompany : potentialCompanies){
                Set<Integer> companiesFound = finder.getCompaniesIds(potentialCompany.toCharArray(), companiesDataset);
                companies.addAll(companiesFound);
            }
        });

        List<Integer> orderedCompanies = new ArrayList<>(companies);
        orderedCompanies.sort(Comparator.nullsLast(Comparator.naturalOrder()));

        long two = System.nanoTime();
        System.out.println("Tempo impiegato: " + (two - one)/1000000000d);
        System.out.println("Dim aziende trovate: " + orderedCompanies.size());
        System.out.println("Aziende: " + orderedCompanies);
    }

}
