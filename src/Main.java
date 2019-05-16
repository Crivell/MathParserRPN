import RPN.ReversePolishNotation;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println(ReversePolishNotation.solveEq("1 + ( 2 + 1 ) * 10 "));
        System.out.println(ReversePolishNotation.solveEq("10 * ( 2 + 1 ) + 1"));
        System.out.println(ReversePolishNotation.solveEq("10 * ( 2 + 1 ) + 1 + ln 10"));
        System.out.println(ReversePolishNotation.solveEq("10 * ( 2 + 1 ) + 1 + ln 10"));
        System.out.println(ReversePolishNotation.solveEq("10 * ( 2 + 1 ) + 3 * ln 10"));


    }
}
