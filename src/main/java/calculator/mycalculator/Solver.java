package calculator.mycalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solver {

    public static String evaluateExpression(String expression) {
        // Regular expression to match operands and operator
        String regex = "(-?\\d+(\\.\\d+)?)([+\\-x/%])(-?\\d+(\\.\\d+)?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        if (matcher.matches()) {
            String operand1Str = matcher.group(1);
            String operator = matcher.group(3);
            String operand2Str = matcher.group(4);

            double operand1 = Double.parseDouble(operand1Str);
            double operand2 = Double.parseDouble(operand2Str);

            switch (operator) {
                case "+":
                    return String.valueOf(operand1 + operand2);
                case "-":
                    return String.valueOf(operand1 - operand2);
                case "x":
                    return String.valueOf(operand1 * operand2);
                case "/":
                    if (operand2 == 0) {
                        return "Can not Divisible by zero";
                    }
                    return String.valueOf(operand1 / operand2);
                case "%":
                    return String.valueOf(operand1 % operand2);
                default:
                    return "";
            }
        } else {
            return "";
        }
    }
}