package ru.comfortsoft.service;

import org.springframework.stereotype.Service;
import ru.comfortsoft.exception.FileProcessingException;
import ru.comfortsoft.model.FileRequest;
import ru.comfortsoft.utils.ExcelReader;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * Сервис для обработки данных из XLSX-файла.
 */
@Service
public class ExcelService {

    /**
     * Находит N-ое максимальное число в файле.
     *
     * @param request тело запроса
     * @return N-ое максимальное число
     */
    public int findNthMax(FileRequest request) {
        if (request.getN() <= 0) {
            throw new IllegalArgumentException("N должно быть больше 0");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        try (Stream<Integer> stream = ExcelReader.readNumbers(request.getFilePath())) {
            stream.forEach(num -> {
                if (minHeap.size() < request.getN()) {
                    minHeap.offer(num);
                } else if (!minHeap.isEmpty() && num > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.offer(num);
                }
            });
        } catch (IOException e) {
            throw new FileProcessingException("Ошибка чтения файла", e);
        }
        if (minHeap.isEmpty()) {
            throw new IllegalArgumentException("Файл не содержит достаточное количество чисел");
        }
        return minHeap.peek();
    }
}

// Сложность:
// O(M log N), где:
//
//M — количество чисел в файле.
//N — какое по счёту максимальное число ищем.

