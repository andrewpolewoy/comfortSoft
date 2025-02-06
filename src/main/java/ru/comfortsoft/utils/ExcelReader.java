package ru.comfortsoft.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Утилитарный класс для чтения чисел из XLSX-файла.
 */
public class ExcelReader {

    /**
     * Читает числовые значения из первого столбца XLSX-файла и возвращает поток чисел.
     *
     * @param filePath путь к файлу XLSX
     * @return поток целых чисел
     * @throws IOException если произошла ошибка при чтении файла
     */
    public static Stream<Integer> readNumbers(String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        return StreamSupport.stream(sheet.spliterator(), false)
                .flatMap(row -> StreamSupport.stream(row.spliterator(), false))
                .filter(cell -> cell.getCellType() == CellType.NUMERIC)
                .map(cell -> (int) cell.getNumericCellValue());
    }
}

