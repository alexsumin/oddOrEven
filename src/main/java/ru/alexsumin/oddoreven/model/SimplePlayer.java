package ru.alexsumin.oddoreven.model;

public class SimplePlayer implements Player {

    private short result = 0;

    @Override
    public void add(int x) {
        result += x;
    }

    @Override
    public byte getDecision() {
        byte i = (byte) (Math.random() * 100);

        if (i <= 33)
            return 1;
        else if (i <= 66)
            return 2;
        else return 3;
    }

    @Override
    public short getResult() {
        return result;
    }

}
