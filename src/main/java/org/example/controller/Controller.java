package org.example.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {
    @SneakyThrows
    @GetMapping("/hello")
    public ResponseEntity<List<Person>> hello() {
        String fileName = "test.csv";

        List<Person> persons = new CsvToBeanBuilder(new FileReader(fileName))
                .withSeparator(',')
                .withType(Person.class)
                .withSkipLines(1)
                .build()
                .parse();

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/buy")
    public ResponseEntity<Person> buy() {
        Person person = new Person("tester", 1);

        String CSV_HEADER = "NAME,AGE\n";
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        csvContent.append(person.getName()).append(",")
                .append(person.getName()).append("\n");

        Path filePath = Paths.get("output.csv");
        Files.write(filePath, csvContent.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}