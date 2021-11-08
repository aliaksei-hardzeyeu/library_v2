package by.hardzeyeu.libraryV2;

import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        int number = 1;
        IntStream.rangeClosed(2, number / 2).anyMatch(i -> number % i == 0);


    }
}