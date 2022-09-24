package com.reprisk.companiesnews.controller;

import com.reprisk.companiesnews.finder.Finder;
import com.reprisk.companiesnews.model.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final Finder finder;

    @Autowired
    public CompanyController(Finder finder) {
        this.finder = finder;
    }

    @GetMapping("find")
    private ResponseEntity<List<Integer>> getCompaniesByHashing(@RequestBody @Valid Paths paths) throws IOException {
        return ResponseEntity.ok(finder.getCompaniesIds(paths.getPathOfArticles(), paths.getPathOfDataset()));
    }

}
