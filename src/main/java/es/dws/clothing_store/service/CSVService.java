package es.dws.clothing_store.service;

import java.util.List;
import java.util.function.Function;

/**
 * CSVService
 */
public interface CSVService {

    <T> void writeObjectsToCsv(List<T> objects, String fileName);

    <T> List<T> readFromCsv(String fileName, Function<String, T> lineMapper);

    void createCsvFile(String fileName);
}
