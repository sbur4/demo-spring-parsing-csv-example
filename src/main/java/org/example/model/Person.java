package org.example.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private int age;
}