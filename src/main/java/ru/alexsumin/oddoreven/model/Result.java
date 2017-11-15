package ru.alexsumin.oddoreven.model;

public class Result {
    short iter;
    byte first;
    byte second;
    boolean isFirstWin;
    byte result;
    short sum;

    public Result(short iter, byte first, byte second, boolean isFirstWin, byte result, short sum) {

        this.iter = iter;
        this.first = first;
        this.second = second;
        this.isFirstWin = isFirstWin;
        this.result = result;
        this.sum = sum;
    }

    public short getIter() {
        return iter;
    }

    public byte getFirst() {
        return first;
    }

    public byte getSecond() {
        return second;
    }

    public boolean isFirstWin() {
        return isFirstWin;
    }

    public byte getResult() {
        return result;
    }

    public short getSum() {
        return sum;
    }
}
