package ru.alexsumin.oddoreven.model;

public class LuckyPlayer implements Player {
    private short result;

    @Override
    public void add(int x) {
        result += x;
    }

    @Override
    public byte getDecision() {
        byte temp = (byte) (Math.random() * 100);
        if ((temp >= 20) && (temp <= 80)) {
            return 2;
        }
        if (temp < 20) {
            return 1;
        } else return 3;
    }

    @Override
    public short getResult() {
        return result;
    }

}
