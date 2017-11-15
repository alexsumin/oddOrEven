package ru.alexsumin.oddoreven.log;

import javafx.scene.control.TextArea;
import ru.alexsumin.oddoreven.model.Result;

import java.text.DecimalFormat;
import java.util.concurrent.ArrayBlockingQueue;

public class ConcLog extends Thread {
    private static String pattern = "##0.00";
    private static DecimalFormat decimalFormat = new DecimalFormat(pattern);
    private ArrayBlockingQueue<Result> queue = new ArrayBlockingQueue<>(2000);
    private TextArea textArea;

    public ConcLog(TextArea textArea) {
        this.textArea = textArea;
    }

    private static double round(double number) {
        String s = decimalFormat.format(number);
        return Double.valueOf(s);

    }

    public void addToQueue(short iter, byte first, byte second, boolean isFirstWin, byte result, short sum) {
        Result r = new Result(iter, first, second, isFirstWin, result, sum);
        queue.add(r);
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Result r = null;
            try {
                r = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeGame(r.getIter(), r.getFirst(), r.getSecond(), r.isFirstWin(), r.getResult(), r.getSum());

        }
    }

    private void writeGame(short iter, byte first, byte second, boolean isFirstWin, byte result, short sum) {
        //Platform.runLater(() -> {
        textArea.appendText(iter + ") Первый игрок выбрал: " + first + " Второй игрок выбрал: " + second + ".");
        if (isFirstWin)
            textArea.appendText("Первый игрок выиграл со счётом: " + result + ". Сумма: " + sum + "\n\n");
        else
            textArea.appendText("Второй игрок выиграл со счётом: -" + result + ". Сумма: " + sum + "\n\n");
        //});
    }

    public void writeAverage(short iter, double result) {
        textArea.appendText("Среднее арифметическое: " + round(result / iter) + "\n");
    }
}
