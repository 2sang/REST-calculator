package univ.lecture.riotapi.model;

import java.util.*;

public class RPNCalculator{

	private RPNCalculator(){
	}

	private static boolean isAnOperator(String s) {
		return s.length() == 1 && "+-*/".indexOf(s) >= 0;
	}

	private static double evaluate(double x, double y, String op) {
		double result;
		if ("+".equals(op))
			result = x + y;
		else if ("-".equals(op))
			result = x - y;
		else if ("*".equals(op))
			result = x * y;
		else
			result = x / y;

		return result;
	}

	public static double calculateExpression(String exp){
		return calculatePostFix(infixToPostfix(exp));
	}

	private static double calculatePostFix(String[] args){
		Stack<Object> stack = new Stack<>();
		
		for (int i = 0; i < args.length; i++) {
			String input = args[i];

			if (isAnOperator(input)) {
				double y = Double.parseDouble((String) stack.pop());
				double x = Double.parseDouble((String) stack.pop());
				double z = evaluate(x, y, input);
				stack.push(Double.toString(z));
			} else
				stack.push(input);
		}
		
		return Double.parseDouble(stack.pop().toString());
	}
	
	private static String[] infixToPostfix(String infix){
		Stack<Object> stack = new Stack<>();
		
		String tempPostfix = new String();
		int count = 0;
		String[] changedArgs = infix.split("(?<=[\\(\\)\\+\\-*\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*\\/\\^A-Za-z])");
		
		for (int i = 0; i < changedArgs.length; i++) {
			if (precedence(changedArgs[i]) == 3) {
				stack.push(changedArgs[i]);
			} else if (precedence(changedArgs[i]) == 9) {
				while (!"(".equals(stack.peek())) {
					tempPostfix += (String) stack.pop() + " ";
					count++;
				}
				stack.pop();
			} else if (precedence(changedArgs[i]) == 0) {
				tempPostfix += changedArgs[i] + " ";
				count++;
			} else if (!stack.isEmpty() && (precedence(changedArgs[i]) <= precedence((String) stack.peek()))) {
				while (!stack.isEmpty() && (precedence(changedArgs[i]) <= precedence((String) stack.peek()))) {
					tempPostfix += (String) stack.pop() + " ";
					count++;
				}
				stack.push(changedArgs[i]);
			} else
				stack.push(changedArgs[i]);
		}

		while (!stack.isEmpty()) {
			tempPostfix += (String) stack.pop() + " ";
			count++;
		}

		StringTokenizer parser = new StringTokenizer(tempPostfix, " ");
		String[] postfix = new String[count];
		int j = 0;

		while (parser.hasMoreTokens()) {
			postfix[j] = parser.nextToken();
			j++;
		}

		return postfix;
	}
	
	private static int precedence(String token) {
		if ("+".equals(token) || "-".equals(token)) {
			return 5;
		} else if ("/".equals(token) || "*".equals(token)) {
			return 7;
		} else if ("(".equals(token)) {
			return 3;
		} else if (")".equals(token)) {
			return 9;
		} else
			return 0;
	}
}
