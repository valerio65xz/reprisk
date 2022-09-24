package com.reprisk.companiesnews.finder;

import com.reprisk.companiesnews.debugger.Debugger;
import com.reprisk.companiesnews.filter.WordFilter;
import com.reprisk.companiesnews.parser.ArticleParser;
import com.reprisk.companiesnews.parser.DatasetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
    private WordFilter wordFilter;

    @Autowired
    private Debugger debugger;

    public List<Integer> getCompaniesIds(String pathOfArticles, String pathOfDataset) throws IOException {
        Set<Integer> companies = new HashSet<>();

        List<Path> articlesPaths = getArticlesPaths(pathOfArticles);
        Map<String, Integer> companiesFromDataset = datasetParser.getCompaniesFromDataset(pathOfDataset);

        //debugger.openResultsFile("C:\\Users\\vale-\\OneDrive\\Old\\Desktop\\reprisk\\matchedResults.csv");
        executeCompanyThreads(articlesPaths, companies, companiesFromDataset);
        //debugger.closeResultsFile();

        List<Integer> orderedCompanies = new ArrayList<>(companies);
        orderedCompanies.removeAll(Collections.singleton(null));
        Collections.sort(orderedCompanies);
        return orderedCompanies;
    }

    List<Path> getArticlesPaths(String pathOfArticles){
        File[] files = new File(pathOfArticles).listFiles();
        List<Path> articlesPaths = new ArrayList<>();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                articlesPaths.add(Path.of(pathOfArticles + "\\" + file.getName()));
            }
        }

        return articlesPaths;
    }

    void executeCompanyThreads(List<Path> articlesPaths, Set<Integer> companies, Map<String, Integer> companiesDataset){
        articlesPaths.parallelStream().forEach((articlePath) -> {
            String articleContent;

            try {
                articleContent = Files.readString(articlePath);
            } catch (IOException e) {
                throw new RuntimeException("Can't access file in location: " + articlePath);
            }

            Set<String> potentialCompanies = articleParser.getPotentialCompanies(articleContent);
            for (String potentialCompany : potentialCompanies){
                String potentialCompanyCleaned = wordFilter.filter(potentialCompany.trim());
                if (companiesDataset.containsKey(potentialCompanyCleaned)){
                    //debugger.writeResultsFile(debuggerGetArticleRelative(articlePath.toString()) + ": [" + companiesDataset.get(potentialCompanyCleaned) + "] " + potentialCompanyCleaned + " - " + debugger.originalDataset.get(companiesDataset.get(potentialCompanyCleaned)) + "\n");
                    companies.add(companiesDataset.get(potentialCompanyCleaned));
                }
            }
        });
    }

    private String debuggerGetArticleRelative(String articlePath){
        return articlePath.substring(articlePath.lastIndexOf('\\') + 1);
    }

}
