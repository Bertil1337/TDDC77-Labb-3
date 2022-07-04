package se.liu.ida.tddc77;
import java.util.Scanner;

import java.util.Stack;

import lincalc.LinCalcJohn;
public class LinCalc {

    public static void printArray(String[] array){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        // Replace the last ", " with "]"
        sb.replace(sb.length() - 2, sb.length(), "]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expression;
        double result;

        System.out.print("Enter expression: ");
        expression = in.nextLine();
        do {
            result = evaluate(expression);
            System.out.println("Result was: " + result);
            System.out.print("Enter expression: ");
        } while (!"".equals(expression = in.nextLine()));
        in.close();
    }

    public static double calc(String[] lexedPostfix) {
        return Metoder_Calc.calc(lexedPostfix);
    }

    public static String[] toPostfix(String[] inputData) {
        return Metoder_Calc.toPostfix(inputData);
    }

    public static double evaluate(String expression) {
        String[] lexedInfix = lex(expression);
        String[] lexedPostfix = toPostfix(lexedInfix);
        printArray(lexedPostfix);
        return calc(lexedPostfix);
    }

    public static String[] lex(String expression) {
        return Metoder_Calc.lex(expression);
    }
}


