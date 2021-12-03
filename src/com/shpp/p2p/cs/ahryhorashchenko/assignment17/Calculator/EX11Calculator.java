package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Calculator;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyArrayList;
import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyHashMap;

import java.util.Scanner;

/**
 * Program calculate the expression.
 * it looks like this: 3 * 5 + 2 or 2 + a - 23
 * or like this 1+(2+3*(4+5-sin(45*cos(a))))/7 ,
 * 1+(2+3*(4+5-sin(45*cos(a))))/7 +log2(113)
 */
public class EX11Calculator {

    /* HashMap with variables if have them */
    private static MyHashMap<String, Double> variables;

    /**
     * Run the program which calculate the expression
     */
    public void run(String[] args) {
        String line = "";

        /* Enter all parameters to one String object*/
        for (String arg : args) line += arg;
        if (line.length() > 0) {
            EX11ParserExpressions e = new EX11ParserExpressions();
            MyArrayList<String> expression = e.parseExpression(line);
            if (expression != null) {
                variables = lookForVariables(expression);
                double result = calculate(expression);
                System.out.println(result);
                if (variables.size() > 0) {
                    while (!readLine("For continue with this expression press enter if no \"n\"").equals("n")) {
                        variables.clear();
                        variables = lookForVariables(expression);
                        result = calculate(expression);
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("You entered incorrect number of brackets");
            }
        } else {
            System.out.println("You entered empty line");
        }
    }

    /**
     * Calculate the expression
     *
     * @param expression arraylist with elements of mathematical expression
     * @return result of calculate type of double
     */
    private static double calculate(MyArrayList<String> expression) {
        /*try {*/
        MyArrayList<String> expression1 = new MyArrayList<>(expression);
        double result = resolveExpression(expression1);
        if (result >= Double.POSITIVE_INFINITY || result <= Double.NEGATIVE_INFINITY) {
            System.out.println("You can`t divide by zero");
            return Double.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY;
        }
        return result;/*
        } catch (Exception e) {
            System.out.println("You entered wrong expression");
            return Double.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY;
        }*/
    }

    /**
     * resolve the expression in array list
     *
     * @param expression array list with parse expression
     * @return double result of calculating
     */
    private static double resolveExpression(MyArrayList<String> expression) {
        for (int pos = 0; pos < expression.size(); pos++) {
            String element = expression.get(pos);
            /* Switch element of expression for "sin, cos, tan, atan, log2, log10, sqrt, ("*/
            switch (element) {
                case "sin":
                    calculateSin(pos, expression);
                    continue;

                case "cos":
                    calculateCos(pos, expression);
                    continue;

                case "tan":
                    calculateTan(pos, expression);
                    continue;

                case "atan":
                    calculateAtan(pos, expression);
                    continue;

                case "log2":
                    calculateLog2(pos, expression);
                    continue;

                case "log10":
                    calculateLog10(pos, expression);
                    continue;

                case "sqrt":
                    calculateSqrt(pos, expression);
                    continue;

                case "(":
                    if (pos < expression.size() - 1) {
                        expression.remove(pos);
                        expression.add(pos, calculateTheBrackets(pos, expression));
                    }
            }
        }
        makeMathematicsOperations(expression);
        return Double.parseDouble(expression.get(0));
    }

    /**
     * Open the brackets and calculating expression in this brackets
     *
     * @param pos        position of element from expression
     * @param expression expression
     * @return the result of the calculating in brackets
     */
    private static String calculateTheBrackets(int pos, MyArrayList<String> expression) {
        MyArrayList<String> expressionInBrackets = new MyArrayList<>();
        /* counter for left brackets */
        int countOfLb = 0;

        String element = expression.get(pos);
        expression.remove(pos);
        if (element.equals("(")) {
            countOfLb++;
        }

        /* If expression element == variable program put the number on the place of variable*/
        /* Or add element to new array list*/
        if (element.length() == 1 && element.charAt(0) <= 'z' && element.charAt(0) >= 'a') {
            expressionInBrackets.add(variables.get(element.charAt(0) + "").toString());
        } else {
            expressionInBrackets.add(element);
        }

        /* Write all elements for the right bracket which is close */
        writeAllToRb(countOfLb, element, expression, expressionInBrackets, pos);
        if (expressionInBrackets.size() > 1) {
            resolveExpression(expressionInBrackets);
        }

        return expressionInBrackets.get(0);
    }

    /**
     * Write all elements from one array list to another array list to right bracket
     *
     * @param countOfLb            count of left brackets
     * @param element              one element taking from array list
     * @param expression           array list with expression
     * @param expressionInBrackets array list with expression in brackets
     * @param pos                  position of element which taking from array list
     */
    private static void writeAllToRb(int countOfLb, String element, MyArrayList<String> expression,
                                     MyArrayList<String> expressionInBrackets, int pos) {
        while (!element.equals(")") && pos <= expression.size() - 1) {
            /* Take element from expression */
            /* write it to line and remove element from array list*/
            if (expression.size() > 0) {
                element = expression.remove(pos);
            }

            /* If element != ")"-rb */
            /* If expression element == variable program put the number on the place of variable*/
            /* Or add element to new array list */
            if (!element.equals(")")) {
                if (element.length() == 1 && element.charAt(0) <= 'z' && element.charAt(0) >= 'a') {
                    expressionInBrackets.add(variables.get(element.charAt(0) + "").toString());
                } else {
                    expressionInBrackets.add(element);
                }
            }

            /* Counter for left brackets increment if element == left bracket*/
            if (element.equals("(")) {
                countOfLb++;
            }

            if (countOfLb > 0 && element.equals(")")) {
                expressionInBrackets.add(element);
                if (expression.size() > 0) {
                    element = expression.remove(pos);
                }
                countOfLb--;
                if (element.equals(")")) {
                    while (element.equals(")") && countOfLb > 0) {
                        if (expression.size() > 0) {
                            element = expression.get(pos);
                            expression.remove(pos);
                        }
                        expressionInBrackets.add(element);
                        countOfLb--;
                    }
                } else {
                    expressionInBrackets.add(element);
                }
            }
        }
    }

    /**
     * Make mathematics operations with expression like "^","*","/","+","-"
     *
     * @param expression array list with expression
     */
    private static void makeMathematicsOperations(MyArrayList<String> expression) {
        bringToDegree(expression);
        multiplicationAndDivision(expression);
        addition(expression);
    }

    /**
     * Looking if there are variables and if so ask about their values
     *
     * @param expression array list with expression
     * @return hash map
     */
    private static MyHashMap<String, Double> lookForVariables(MyArrayList<String> expression) {
        MyHashMap<String, Double> variables = new MyHashMap<String, Double>();
        boolean condition = true;

        for (int i = 0; i < expression.size(); i++) {
            String element = expression.get(i);
            if (element.length() <= 1) {
                if (element.charAt(0) <= 'z' && element.charAt(0) >= 'a') {
                    do {
                        String s = readLine(element + " = ");
                        try {
                            double number = Double.parseDouble(s);
                            variables.put(element, number);
                            condition = false;
                        } catch (Exception e) {
                            System.out.println("You entered wrong variable");
                        }
                    } while (condition);
                }
            }
        }
        return variables;
    }

    /**
     * Write the line and scan what user enter
     *
     * @param s line
     * @return line
     */
    private static String readLine(String s) {
        Scanner scan = new Scanner(System.in);
        System.out.print(s);
        return scan.nextLine();
    }

    /**
     * Bring the expression to degree if have that
     *
     * @param expression the expression is elevated to degrees
     */
    private static void bringToDegree(MyArrayList<String> expression) {
        double resultOfOneAction;
        double firstOperand;
        double secondOperand;

        for (int i = expression.size() - 1; i >= 0; i--) {
            if (expression.get(i).equals("^")) {
                if (i != 0 & i != expression.size() - 1) {
                    if (expression.get(i - 1).charAt(0) >= 'a' && expression.get(i - 1).charAt(0) <= 'z') {
                        firstOperand = variables.get(expression.get(i - 1));
                    } else {
                        firstOperand = Double.parseDouble(expression.get(i - 1));
                    }
                    if (expression.get(i + 1).charAt(0) >= 'a' && expression.get(i + 1).charAt(0) <= 'z') {
                        secondOperand = variables.get(expression.get(i + 1));
                    } else {
                        secondOperand = Double.parseDouble(expression.get(i + 1));
                    }
                    resultOfOneAction = Math.pow(firstOperand, secondOperand);
                    expression.set(i, resultOfOneAction + "");
                    expression.remove(i + 1);
                    expression.remove(i - 1);
                    i = expression.size() - 1;
                }
            }
        }
    }

    /**
     * Do multiplication and division on the
     *
     * @param expression
     */
    private static void multiplicationAndDivision(MyArrayList<String> expression) {
        double resultOfOneAction;
        double firstOperand;
        double secondOperand;
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("/") || expression.get(i).equals("*")) {
                if (i != 0 & i != expression.size() - 1) {
                    if (expression.get(i - 1).charAt(0) >= 'a' && expression.get(i - 1).charAt(0) <= 'z') {
                        firstOperand = variables.get(expression.get(i - 1));
                    } else {
                        firstOperand = Double.parseDouble(expression.get(i - 1));
                    }
                    if (expression.get(i + 1).charAt(0) >= 'a' && expression.get(i + 1).charAt(0) <= 'z') {
                        secondOperand = variables.get(expression.get(i + 1));
                    } else {
                        secondOperand = Double.parseDouble(expression.get(i + 1));
                    }
                    resultOfOneAction = expression.get(i).equals("/") ?
                            firstOperand / secondOperand :
                            firstOperand * secondOperand;
                    expression.set(i, resultOfOneAction + "");
                    expression.remove(i + 1);
                    expression.remove(i - 1);
                    i = 0;
                }
            }
        }
    }

    /**
     * Do addition and subtraction with expression
     *
     * @param expression
     */
    private static void addition(MyArrayList<String> expression) {
        double resultOfOneAction;
        double firstOperand;
        double secondOperand;
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("-") || expression.get(i).equals("+")) {
                if (i != 0 & i != expression.size() - 1) {
                    if (expression.get(i - 1).charAt(0) >= 'a' && expression.get(i - 1).charAt(0) <= 'z') {
                        firstOperand = variables.get(expression.get(i - 1));
                    } else {
                        firstOperand = Double.parseDouble(expression.get(i - 1));
                    }
                    if (expression.get(i + 1).charAt(0) >= 'a' && expression.get(i + 1).charAt(0) <= 'z') {
                        secondOperand = variables.get(expression.get(i + 1));
                    } else {
                        secondOperand = Double.parseDouble(expression.get(i + 1));
                    }
                    resultOfOneAction = expression.get(i).equals("-") ?
                            firstOperand - secondOperand :
                            firstOperand + secondOperand;
                    expression.set(i, resultOfOneAction + "");
                    expression.remove(i + 1);
                    expression.remove(i - 1);
                    i = 0;
                }
            }
        }
    }

    /**
     * Calculate the sine
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateSin(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.sin(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }

    }

    /**
     * Calculate the cosine
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateCos(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.cos(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }
    }

    /**
     * Calculate the tangent
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateTan(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.tan(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }
    }


    /**
     * Calculate the arctangent
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateAtan(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.atan(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }
    }

    /**
     * Calculate the binary logarithm
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateLog2(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.log(Double.parseDouble(calculateTheBrackets(pos, expression))) / Math.log(2);
                expression.add(pos, Double.toString(c));
            }
        }
    }

    /**
     * Calculate the decimal logarithm
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateLog10(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.log10(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }
    }

    /**
     * Calculate the the root of the number
     *
     * @param pos        position of element of array list
     * @param expression array list with expression
     */
    private static void calculateSqrt(int pos, MyArrayList<String> expression) {
        if (pos < expression.size() - 1) {
            if (expression.get(pos + 1).equals("(")) {
                expression.remove(pos);
                expression.remove(pos);
                double c = Math.sqrt(Double.parseDouble(calculateTheBrackets(pos, expression)));
                expression.add(pos, Double.toString(c));
            }
        }
    }
}