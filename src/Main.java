/*
Задача: "Счётчик чисел"

Напишите программу, которая создаёт два потока.

Первый поток должен увеличивать общий счётчик чисел от 0 до 1000, а второй поток должен уменьшать этот счётчик от 1000 до 0.

Каждый поток должен выполнять свою работу с небольшой задержкой, чтобы симулировать реальную работу.
Ваша задача - гарантировать корректное изменение значения счётчика, избегая состояния race condition

Требования:
        1. Используйте `ExecutorService` для управления потоками.
        2. Синхронизируйте доступ к общему счётчику, чтобы избежать состояний гонки.
        3. Выведите конечное значение счётчика после завершения работы обоих потоков.

        Подсказки:
        - Используйте ключевое слово `synchronized` для синхронизации доступа к счётчику.
        - Методы `increment` и `decrement` должны быть синхронизированы.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int numThreads = 2;

        Counter counter = new Counter();
        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        executorService.submit(incrementTask);
        executorService.submit(decrementTask);
        executorService.shutdown();

        while (!executorService.isTerminated()) { }

        System.out.println("Результат: " + counter.getCount());
    }
}