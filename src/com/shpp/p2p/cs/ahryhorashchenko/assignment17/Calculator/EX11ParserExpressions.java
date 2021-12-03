package com.shpp.p2p.cs.ahryhorashchenko.assignment17.Calculator;

import com.shpp.p2p.cs.ahryhorashchenko.assignment17.MyArrayList;

public class EX11ParserExpressions {

    public MyArrayList<String> expression = new MyArrayList<String>();
    private int conditionForBrackets = 0;
    private boolean conditionForMines = false;

    /**
     * Parsing the expression and entering it to the Arraylist
     *
     * @param formula line with entering formula
     * @return array list with the parsin formula
     */
    public MyArrayList<String> parseExpression(String formula) {
        for (int pos = 0; pos < formula.length(); pos++) {
            pos = lookingForNumbersAndPutToExpression(formula, pos);
            pos = lookingForAnotherSymbols(formula, pos);
        }
        if (conditionForBrackets != 0) {
            return null;
        }
        return this.expression;
    }

    /**
     * Looking for numbers and put that to array list
     *
     * @param formula entered formula
     * @param pos     position of symbol from formula
     * @return position
     */
    private int lookingForNumbersAndPutToExpression(String formula, int pos) {
        if (formula.charAt(pos) <= '9' && formula.charAt(pos) >= '0') {
            StringBuilder s = new StringBuilder();
            if (conditionForMines) {
                s.append('-');
                conditionForMines = false;
            }
            do {
                s.append(formula.charAt(pos));
                if (pos != formula.length() - 1) {
                    pos++;
                    if (formula.charAt(pos) == '.') {
                        s.append(formula.charAt(pos));
                        if (pos != formula.length() - 1) {
                            pos++;
                        }
                    } else if (formula.charAt(pos) >= 'a' && formula.charAt(pos) <= 'z') {
                        this.expression.add(s.toString());
                        s = new StringBuilder();
                        s.append('*');
                        break;
                    }
                } else {
                    break;
                }
            } while (formula.charAt(pos) <= '9' && formula.charAt(pos) >= '0');
            this.expression.add(s.toString());
        }
        return pos;
    }


    /**
     * Looking for all another symbols
     * and put they to array
     *
     * @param formula entered formula
     * @param pos     position of symbol from formula
     * @return position
     */
    private int lookingForAnotherSymbols(String formula, int pos) {
        if (formula.charAt(pos) >= 'a' && formula.charAt(pos) <= 'z') {
            StringBuilder s = new StringBuilder();
            do {
                s.append(formula.charAt(pos));
                if (pos != formula.length() - 1) {
                    pos++;
                } else {
                    break;
                }
            } while (formula.charAt(pos) >= 'a' && formula.charAt(pos) <= 'z' ||
                    formula.charAt(pos) <= '9' && formula.charAt(pos) >= '0');
            this.expression.add(s.toString());
        }

        if (formula.charAt(pos) != ' ' && (formula.charAt(pos) > '9' || formula.charAt(pos) < '0') &&
                formula.charAt(pos) < 'a' || formula.charAt(pos) > 'z') {
            if (formula.charAt(pos) == '(') {
                conditionForBrackets++;
            } else if (formula.charAt(pos) == ')') {
                conditionForBrackets--;
            }
            if (pos != 0 && formula.charAt(pos) == '-' && formula.charAt(pos - 1) == '(') {
                conditionForMines = true;
            } else {
                this.expression.add(formula.charAt(pos) + "");
            }
        }
        return pos;
    }
}

