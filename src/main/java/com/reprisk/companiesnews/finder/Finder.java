package com.reprisk.companiesnews.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class Finder {

    @Autowired
    private ArticleParser articleParser;

    @Autowired
    private DatasetParser datasetParser;

    @Autowired
    private DistanceFinder distanceFinder;

    public List<Integer> getCompaniesIds(String pathOfArticles, String pathOfDataset, SearchType searchType) throws IOException {
        File[] files = new File(pathOfArticles).listFiles();
        List<Path> articlesPaths = new ArrayList<>();
        Set<Integer> companies = new HashSet<>();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                articlesPaths.add(Path.of(pathOfArticles + "\\" + file.getName()));
            }
        }

        Map<String, Integer> companiesFromDataset = datasetParser.getCompaniesFromDataset(pathOfDataset);
        switch (searchType){
            case HASH ->
                    executeCompanyThreads(articlesPaths, companies, companiesFromDataset);
            case DISTANCE -> {
                char[] companiesDatasetArray = buildDatasetString(companiesFromDataset);
                executeCompanyThreads(articlesPaths, companies, companiesDatasetArray);
            }
        }

        List<Integer> orderedCompanies = new ArrayList<>(companies);
        orderedCompanies.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        return orderedCompanies;
    }

    private void executeCompanyThreads(List<Path> articlesPaths, Set<Integer> companies, Map<String, Integer> companiesDataset){
        articlesPaths.parallelStream().forEach((articlePath) -> {
            String articleContent;
            try {
                articleContent = Files.readString(articlePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Set<String> potentialCompanies = articleParser.getPotentialCompanies(articleContent);

            for (String potentialCompany : potentialCompanies){
                String potentialCompanyCleaned = datasetParser.removeStopWords(potentialCompany);
                if (companiesDataset.containsKey(potentialCompanyCleaned)){
                    companies.add(companiesDataset.get(potentialCompanyCleaned));
                }
            }
        });
    }

    private void executeCompanyThreads(List<Path> articlesPaths, Set<Integer> companies, char[] companiesDataset){
        articlesPaths.parallelStream().forEach((articlePath) -> {
            String articleContent;
            try {
                articleContent = Files.readString(articlePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Set<String> potentialCompanies = articleParser.getPotentialCompanies(articleContent);

            for (String potentialCompany : potentialCompanies){
                String potentialCompanyCleaned = datasetParser.removeStopWords(potentialCompany.trim());
                if (!potentialCompanyCleaned.isBlank()){
                    Set<Integer> companiesFound = distanceFinder.getCompaniesIds(potentialCompanyCleaned.toCharArray(), companiesDataset);
                    companies.addAll(companiesFound);
                }
            }
        });
    }

    private char[] buildDatasetString(Map<String, Integer> companiesFromDataset) throws IOException {
        //PrintWriter printWriter = new PrintWriter(new FileWriter("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\asd.csv"));
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, Integer> entry : companiesFromDataset.entrySet()) {
            stringBuilder.append(entry.getValue()).append(";").append(entry.getKey()).append("\n");
            //printWriter.print(entry.getValue() + ";" + entry.getKey() + "\n");
        }
        //printWriter.close();
        return stringBuilder.toString().toCharArray();
    }

}
