package ru.alexsumin.oddoreven.log;

import javafx.scene.control.TextArea;

import java.text.DecimalFormat;

public class Logger {

    private static String pattern = "##0.00";
    private static DecimalFormat decimalFormat = new DecimalFormat(pattern);
    private TextArea textArea;

    public Logger(TextArea textArea) {
        this.textArea = textArea;
    }

    private static double round(double number) {
        String s = decimalFormat.format(number);
        return Double.valueOf(s);

    }

    //TODO: Do I need a builder?
    public void writeGame(short iter, byte first, byte second, boolean isFirstWin, byte result, short sum) {
        textArea.appendText(iter + ") Первый игрок выбрал: " + first + " Второй игрок выбрал: " + second + ".");
        if (isFirstWin)
            textArea.appendText("Первый игрок выиграл со счётом: " + result + ". Сумма: " + sum + "\n\n");
        else
            textArea.appendText("Второй игрок выиграл со счётом: -" + result + ". Сумма: " + sum + "\n\n");
    }

    public void writeResult(short iter, double result) {
        textArea.appendText("Среднее арифметическое: " + round(result / iter) + "\n");

    }

}
