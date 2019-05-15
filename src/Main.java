import RPN.ReversePolishNotation;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        ReversePolishNotation r = new ReversePolishNotation();
        Stack<String> st = new Stack<>();
        
        st = r.createStack("2 * 1 + 2 * 3 ");
        for (String str:st) {
            System.out.println(str);
        }


        System.out.println(r.solve(st));


    }
}
