package ru.comfortsoft.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {
    private String filePath; // filePath путь к файлу XLSX
    private int n; // n порядковый номер максимального числа
}
