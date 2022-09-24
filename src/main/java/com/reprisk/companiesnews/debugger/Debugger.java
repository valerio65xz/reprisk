package com.reprisk.companiesnews.debugger;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class Debugger {

    private PrintWriter printWriterDataset;
    private PrintWriter printWriterResults;
    public Map<Integer, String> originalDataset = new HashMap<>();

    public void openDatasetFile(String fileName) throws IOException {
        printWriterDataset = new PrintWriter(new FileWriter(fileName));
    }

    public void openResultsFile(String fileName) throws IOException {
        printWriterResults = new PrintWriter(new FileWriter(fileName));
    }

    public void writeDatasetFile(String line){
        printWriterDataset.print(line);
    }

    public void writeResultsFile(String line){
        printWriterResults.print(line);
    }

    public void closeDatasetFile(){
        printWriterDataset.close();
    }

    public void closeResultsFile(){
        printWriterResults.close();
    }

}
