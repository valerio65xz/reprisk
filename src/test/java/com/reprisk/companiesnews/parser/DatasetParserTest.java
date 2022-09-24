package com.reprisk.companiesnews.parser;

import com.reprisk.companiesnews.BaseUnitTest;
import com.reprisk.companiesnews.filter.WordFilter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DatasetParserTest extends BaseUnitTest {

    @Mock
    private WordFilter wordFilter;

    @InjectMocks
    private DatasetParser datasetParser;

    @Test
    void getCompaniesFromDataset() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/100_company_list.csv";
        String companyCleaned = "companyCleaned";
        String finalCompany = "finalCompany";

        when(wordFilter.filter(anyString())).thenReturn(companyCleaned);
        when(wordFilter.filterComplexWords(companyCleaned)).thenReturn(finalCompany);

        Map<String, Integer> result = datasetParser.getCompaniesFromDataset(filePath);

        verify(wordFilter, atLeastOnce()).filter(anyString());
        verify(wordFilter, atLeastOnce()).filterComplexWords(companyCleaned);

        assertEquals(1, result.size());
        assertEquals(100, result.get(finalCompany));
    }

}