package ru.comfortsoft.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.comfortsoft.model.FileRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Определяем, что инстанс теста должен быть создан один для класса
class ExcelServiceTest {
    @TempDir
    Path tempDir;
    @InjectMocks
    private ExcelService excelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Нахождение N-го максимального числа с валидными данными")
    void testFindNthMaxValid() throws Exception {
        File file = createTestXlsx("test.xlsx", new int[]{10, 20, 30, 40, 50});
        FileRequest request = new FileRequest(file.getAbsolutePath(), 3);
        assertEquals(30, excelService.findNthMax(request));
    }

    @Test
    @DisplayName("Пустой Excel файл — исключение при запросе числа")
    void testFindNthMaxWithEmptyFile() throws Exception {
        File file = createTestXlsx("empty.xlsx", new int[]{});
        FileRequest request = new FileRequest(file.getAbsolutePath(), 1);
        assertThrows(IllegalArgumentException.class, () -> excelService.findNthMax(request));
    }

    @Test
    @DisplayName("Некорректное значение N (отрицательное)")
    void testFindNthMaxWithNegativeN() {
        FileRequest request = new FileRequest("test.xlsx", -1);
        assertThrows(IllegalArgumentException.class, () -> excelService.findNthMax(request));
    }

    private File createTestXlsx(String fileName, int[] numbers) throws Exception {
        File file = tempDir.resolve(fileName).toFile();
        try (FileOutputStream fos = new FileOutputStream(file);
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);
            for (int i = 0; i < numbers.length; i++) {
                row.createCell(i).setCellValue(numbers[i]);
            }
            workbook.write(fos);
        }
        return file;
    }
}

