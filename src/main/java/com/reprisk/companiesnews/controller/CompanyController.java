package com.reprisk.companiesnews.controller;

import com.reprisk.companiesnews.finder.Finder;
import com.reprisk.companiesnews.finder.LevenshteinDistance;
import com.reprisk.companiesnews.finder.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private Finder finder;

    @Autowired
    private LevenshteinDistance levenshteinDistance;

    @Value("${path.of.articles}")
    private String pathOfArticles;

    @Value("${path.of.dataset}")
    private String pathOfDataset;

    @GetMapping("find-by-hashing")
    private ResponseEntity<List<Integer>> getCompaniesByHashing() throws IOException {
        return ResponseEntity.ok(finder.getCompaniesIds(pathOfArticles, pathOfDataset, SearchType.HASH));
    }

    @GetMapping("find-by-distance")
    private ResponseEntity<List<Integer>> getCompaniesByDistance() throws IOException {
        return ResponseEntity.ok(finder.getCompaniesIds(pathOfArticles, pathOfDataset, SearchType.DISTANCE));
    }

}
