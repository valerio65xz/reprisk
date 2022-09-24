package com.reprisk.companiesnews.finder;

import com.reprisk.companiesnews.BaseUnitTest;
import com.reprisk.companiesnews.filter.WordFilter;
import com.reprisk.companiesnews.parser.ArticleParser;
import com.reprisk.companiesnews.parser.DatasetParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FinderTest extends BaseUnitTest {

    @Mock
    private ArticleParser articleParser;

    @Mock
    private DatasetParser datasetParser;

    @Mock
    private WordFilter wordFilter;

    @Spy
    @InjectMocks
    private Finder finder;

    @Test
    void getCompaniesIds() throws IOException {
        String pathOfArticles = "pathOfArticles";
        String pathOfDataset = "pathOfDataset";
        Set<Integer> companies = new HashSet<>();
        List<Path> articlesPaths = new ArrayList<>();
        Map<String, Integer> companiesFromDataset = new HashMap<>();

        doReturn(articlesPaths).when(finder).getArticlesPaths(anyString());
        when(datasetParser.getCompaniesFromDataset(anyString())).thenReturn(companiesFromDataset);
        doNothing().when(finder).executeCompanyThreads(articlesPaths, companies, companiesFromDataset);

        List<Integer> result = finder.getCompaniesIds(pathOfArticles, pathOfDataset);

        verify(finder).getArticlesPaths(pathOfArticles);
        verify(datasetParser).getCompaniesFromDataset(pathOfDataset);
        verify(finder).executeCompanyThreads(eq(articlesPaths), refEq(companies), eq(companiesFromDataset));

        assertTrue(result.isEmpty());
    }

    @Test
    void getArticlesPathsShouldGet(){
        String pathOfArticles = System.getProperty("user.dir") + "/src/test/resources";

        List<Path> results = finder.getArticlesPaths(pathOfArticles);

        assertEquals(results.get(0).getFileName().toString(), "000D-2695-EAE6-7998.xml");
        assertEquals(results.get(1).getFileName().toString(), "100_company_list.csv");
    }

    @Test
    void getArticlesPathsShouldNotGet(){
        String pathOfArticles = "123";

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> finder.getArticlesPaths(pathOfArticles));
    }

    @Test
    void executeCompanyThreadsShouldExecute(){
        String filePath = System.getProperty("user.dir") + "/src/test/resources/000D-2695-EAE6-7998.xml";
        List<Path> articlesPaths = Collections.singletonList(Path.of(filePath));
        Set<String> potentialCompanies = new HashSet<>();
        Set<Integer> companies = new HashSet<>();
        Map<String, Integer> companiesDataset = new HashMap<>();
        String notFiltered = "BBC notFilterd";
        String filtered = "BBC";

        potentialCompanies.add(notFiltered);
        companiesDataset.put(filtered, 1);

        when(articleParser.getPotentialCompanies(anyString())).thenReturn(potentialCompanies);
        when(wordFilter.filter(notFiltered.trim())).thenReturn(filtered);

        finder.executeCompanyThreads(articlesPaths, companies, companiesDataset);

        verify(articleParser).getPotentialCompanies(anyString());
        verify(wordFilter).filter(notFiltered.trim());

        assertTrue(companies.contains(1));
    }

    @Test
    void executeCompanyThreadsShouldNotExecute(){
        String filePath = "123";
        List<Path> articlesPaths = Collections.singletonList(Path.of(filePath));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> finder.executeCompanyThreads(articlesPaths, new HashSet<>(), new HashMap<>()))
                .withMessage("Can't access file in location: " + filePath);
    }

}