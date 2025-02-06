package ru.comfortsoft.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.comfortsoft.model.FileRequest;
import ru.comfortsoft.service.ExcelService;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "XLSX Parser", description = "Парсинг XLSX-файлов и поиск N-го максимального числа")
public class XlsxController {

    private final ExcelService excelService;

    public XlsxController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Operation(summary = "Получить N-е максимальное число из файла")
    @GetMapping("/max-number")
    public ResponseEntity<Integer> getMaxNumber(@RequestBody FileRequest request) {
        return ResponseEntity.ok(excelService.findNthMax(request));
    }
}
