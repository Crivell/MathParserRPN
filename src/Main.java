import RPN.ReversePolishNotation;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        ReversePolishNotation r = new ReversePolishNotation();
        Stack<String> st = new Stack<>();
//        st.push("sin");
//        st.push("10");
//        st.push("10");
//        System.out.println(r.solve(st));

        st = r.createStack("10 + ( 8 - ( 3 * 3 ) )");
        for (String str:st) {
            System.out.println(str);
        }

        System.out.println(r.solve(st));
    }

    public static String calc(String exp){

        Scanner st = new Scanner(exp);

        BigDecimal big = new BigDecimal(st.next());
        String co = st.next();
        BigDecimal big2 = new BigDecimal(st.next());
        System.out.println(co);
        if(co.equals("+")){
            big = big.add(big2).pow(2);
        }

        return big.toString();
    }
}
