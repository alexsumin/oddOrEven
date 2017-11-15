package ru.alexsumin.oddoreven.model;


import javafx.scene.control.TextArea;
import javafx.util.Pair;
import ru.alexsumin.oddoreven.log.ConcLog;
import ru.alexsumin.oddoreven.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private short number;
    private short totalSum;
    private Player player1;
    private Player player2;
    private List<Pair<Short, Float>> results;
    private TextArea textArea;
    private Logger logger;
    private byte first;
    private byte second;
    private byte sum;

    public Game(short number, TextArea textArea) {
        this.number = number;
        results = new ArrayList<>();
        this.textArea = textArea;
        logger = new Logger(textArea);

    }

    private static boolean isOdd(byte number) {
        if (number % 2 == 0)
            return true;
        else return false;
    }


    public void play() {
        ConcLog log = new ConcLog(textArea);

        for (short i = 1; i <= number; i++) {
            first = player1.getDecision();
            second = player2.getDecision();
            sum = (byte) (first + second);
            if (isOdd(sum)) {
                player1.add(sum);
                totalSum += sum;
            } else {
                player2.add(-sum);
                totalSum -= sum;
            }
            results.add(new Pair<>(i, Float.valueOf(totalSum / (float) i)));
            //logger.writeGame(i, first, second, isOdd(sum), sum, totalSum);
            log.addToQueue(i, first, second, isOdd(sum), sum, totalSum);

        }
        //logger.writeResult(number, totalSum);
        log.run();
        log.writeAverage(number, totalSum);

    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List getResults() {
        return results;
    }
}
