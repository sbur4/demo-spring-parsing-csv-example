package org.example.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApacheController {
    private static final String SAMPLE_CSV_FILE_PATH = "test.csv";
    private static final String SAMPLE_CSV_FILE = "output2.csv";

    @GetMapping("/read-csv")
    public ResponseEntity<List<Person>> readCsv() {
        List<Person> persons = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                String firstName = record.get(0);
                String lastName = record.get(1);
//                String firstName = record.get("NAME");
//                String lastName = record.get("AGE");
                System.out.println("First Name: " + firstName + ", Last Name: " + lastName);

                Person person = new Person(firstName, Integer.getInteger(lastName));
                persons.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/write-csv")
    public String writeCsv() {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("NAME", "AGE"));
        ) {
            Person person = new Person("tester", 1);

            String CSV_HEADER = "NAME,AGE\n";
            StringBuilder csvContent = new StringBuilder();
            csvContent.append(CSV_HEADER);

            csvContent.append(person.getName()).append(",")
                    .append(person.getName()).append("\n");

            csvPrinter.printRecord(person);
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CSV written successfully.";
    }
}
