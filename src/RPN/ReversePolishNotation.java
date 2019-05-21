package RPN;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Stack;

public class ReversePolishNotation {

    BigDecimal percent = new BigDecimal("0.01");

    public enum Operator {
        ADD("+"),
        SUB("-"),
        DIV("/"),
        MUL("*"),
        POW("^"),
        PER("%"),
        SQRT("sqrt"),
        SIN("SIN"),
        COS("COS"),
        TAN("TAN"),
        LN("LN"),
        LOG("LOG");

        private final String name;

        private Operator(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    /*

//        Przeczytaj symbol.
//        Jeśli symbol jest liczbą dodaj go do kolejki wyjście.
//        Jeśli symbol jest funkcją włóż go na stos.
//        Jeśli symbol jest znakiem oddzielającym argumenty funkcji (np. przecinek):
//        Dopóki najwyższy element stosu nie jest lewym nawiasem, zdejmij element ze stosu i dodaj go do kolejki wyjście. Jeśli lewy nawias nie został napotkany oznacza to, że znaki oddzielające zostały postawione w złym miejscu lub nawiasy są źle umieszczone.
//        Jeśli symbol jest operatorem, o1, wtedy:
//        1) dopóki na górze stosu znajduje się operator, o2 taki, że:
//        o1 jest lewostronnie łączny i jego kolejność wykonywania jest mniejsza lub równa kolejności wyk. o2,
//        lub
//        o1 jest prawostronnie łączny i jego kolejność wykonywania jest mniejsza od o2,
//        zdejmij o2 ze stosu i dołóż go do kolejki wyjściowej i wykonaj jeszcze raz 1)
//        2) włóż o1 na stos operatorów.
//        Jeżeli symbol jest lewym nawiasem to włóż go na stos.
//        Jeżeli symbol jest prawym nawiasem to zdejmuj operatory ze stosu i dokładaj je do kolejki wyjście, dopóki symbol na górze stosu nie jest lewym nawiasem, kiedy dojdziesz do tego miejsca zdejmij lewy nawias ze stosu bez dokładania go do kolejki wyjście. Teraz, jeśli najwyższy element na stosie jest funkcją, także dołóż go do kolejki wyjście. Jeśli stos zostanie opróżniony i nie napotkasz lewego nawiasu, oznacza to, że nawiasy zostały źle umieszczone.
//        Jeśli nie ma więcej symboli do przeczytania, zdejmuj wszystkie symbole ze stosu (jeśli jakieś są) i dodawaj je do kolejki wyjścia. (Powinny to być wyłącznie operatory, jeśli natrafisz na jakiś nawias oznacza to, że nawiasy zostały źle umieszczone.)
     */
    public Stack<String> createStack(String st) {
        Stack<String> out = new Stack<>();
        Stack<String> stack = new Stack<>();
        Scanner scanner = new Scanner(st);

        while (scanner.hasNext()) { //Póki zostały symbole do przeczytania wykonuj:
            String buf;
            if (scanner.hasNextBigDecimal()) { //Jeśli symbol jest liczbą dodaj go do kolejki wyjście.
                out.push(scanner.next());
            } else {                      //Jeśli symbol jest operatorem, o1, wtedy:
                buf = scanner.next();
                if (buf.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        out.push(stack.pop());
                    }
                    stack.pop();
                } else {
                    if (stack.size() > 0) {
                        while (stack.size() > 0 && operatorPriority(stack.peek())  >= operatorPriority(buf) && !stack.peek().equals("(") && !buf.equals("(")) {
                            out.push(stack.pop());
                        }
                    }
                    //Todo Piorytet dzialan

                    stack.push(buf);        //włóż o1 na stos operatorów.
                }
            }
        }

        int size = stack.size();
        for (int i = 0; i < size; i++) {     //Jeśli nie ma więcej symboli do przeczytania, zdejmuj wszystkie symbole ze stosu (jeśli jakieś są) i dodawaj je do kolejki wyjścia. (Powinny to być wyłącznie operatory)
            out.push(stack.pop());
        }

        Stack<String> buf = new Stack<>();          //Todo metoda do odwracania stosu
        size = out.size();
        for (int i = 0; i < size; i++) {
            buf.push(out.pop());
        }

        return buf;
    }

    /*
       // jeśli i-ty symbol jest funkcją to:
       // zdejmij ze stosu oczekiwaną liczbę parametrów funkcji(ozn. a1...an)
       // odłóż na stos wynik funkcji dla parametrów a1...an
       // Zdejmij ze stosu wynik.
     */
    public String solve(Stack<String> st) {

        Stack<String> buf = new Stack<String>();

        while (!st.empty()) { //Dla wszystkich symboli z wyrażenia ONP wykonuj:
            String topOfStack = st.pop();
            if (isNumber(topOfStack)) {    //jeśli i-ty symbol jest liczbą, to odłóż go na stos,
                buf.push(topOfStack);
            } else {   // jeśli i-ty symbol jest operatorem to:

                BigDecimal a = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu jeden element (ozn. a),
                switch (valueOfOperator(topOfStack)) {

                    case ADD: {
                        BigDecimal b = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu kolejny element (ozn. b),;
                        a = a.add(b);
                        break;
                    }
                    case SUB: {
                        BigDecimal b = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu kolejny element (ozn. b),;
                        a = b.subtract(a);
                        break;
                    }
                    case MUL: {
                        BigDecimal b = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu kolejny element (ozn. b),;
                        a = a.multiply(b);
                        break;
                    }
                    case DIV: {
                        BigDecimal b = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu kolejny element (ozn. b),;
                        a = b.divide(a,9,RoundingMode.HALF_UP);
                        break;
                    }
                    case POW: {
                        BigDecimal b = new BigDecimal(buf.pop().replace(',', '.'));   // zdejmij ze stosu kolejny element (ozn. b),;
                        a = a.pow(Integer.valueOf(b.toString()));
                        break;
                    }
                    case PER: {
                        a = a.multiply(this.percent); //Mnorzenie przez 0.01 percent jest BigDecimalem co przechowuje mi
                        break;                         // ta zmienna aby nie tworzyc jej za kazdym razem
                    }
                    case SQRT: {
                        a = BigDecimal.valueOf(Math.sqrt(a.doubleValue()));
                        break;
                    }
                    case SIN: {
                        a = BigDecimal.valueOf(Math.sin(a.doubleValue()));
                        break;
                    }
                    case COS: {
                        a = BigDecimal.valueOf(Math.cos(a.doubleValue()));
                        break;
                    }
                    case TAN: {
                        a = BigDecimal.valueOf(Math.tan(a.doubleValue()));
                        break;
                    }
                    case LN: {
                        a = BigDecimal.valueOf(Math.log(a.doubleValue()));
                        break;
                    }
                    case LOG: {
                        a = BigDecimal.valueOf(Math.log10(a.doubleValue()));
                        break;
                    }
                }
                buf.push(a.toString());  // odłóż na stos wartość b operator a.
            }
        }
        return buf.pop();
    }

    private boolean isNumber(String st) {
        Scanner scanner = new Scanner(st);
        return scanner.hasNextDouble() || scanner.hasNextBigDecimal();
    }

    private Operator valueOfOperator(String st) {
        switch (st) {
            case "+": {
                return Operator.ADD;

            }
            case "-": {
                return Operator.SUB;

            }
            case "/": {
                return Operator.DIV;

            }
            case "*": {
                return Operator.MUL;

            }
            case "^": {
                return Operator.POW;

            }
            case "%": {
                return Operator.PER;

            }
            case "sqrt": {
                return Operator.SQRT;

            }
            case "sin": {
                return Operator.SIN;

            }
            case "cos": {
                return Operator.COS;

            }
            case "tan": {
                return Operator.TAN;

            }
            case "ln": {
                return Operator.LN;

            }
            case "log": {
                return Operator.LOG;

            }

        }
        return Operator.ADD;
    }

    private void clearStack(Stack<String> st) {
        while (!st.empty()) {
            st.pop();
        }
    }

    private int operatorPriority(String st) {
        if (st.equals("(")) {
            return 0;
        }
        switch (valueOfOperator(st)) {
            case ADD: {
                return 1;
            }
            case SUB: {
                return 1;
            }
            case MUL: {
                return 2;
            }
            case DIV: {
                return 2;
            }
            case POW: {
                return 3;
            }
            case PER: {
                return 2;
            }
            case SQRT: {
                return 3;
            }
            case SIN: {
                return 3;
            }
            case COS: {
                return 3;
            }
            case TAN: {
                return 3;
            }
            case LN: {
                return 3;
            }
            case LOG: {
                return 3;
            }
        }
        return 0;
    }

    public static String solveEq(String eq) {
        Stack<String> stack = new Stack<>();
        ReversePolishNotation r = new ReversePolishNotation();
        stack = r.createStack(eq);
        for (String string:stack) {
            System.out.println(string);
        }
        return r.solve(stack);
    }
}
