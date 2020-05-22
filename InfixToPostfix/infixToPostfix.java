package infix_postfix;

import java.util. *;

/**
 * converts infix expression to an equivalent postfix expression
 * @author madhurim
 *
 */
public class infixToPostfix {
    
    public static StringBuilder changeExpression(String infix) {
        
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder postfix = new StringBuilder("");   
        
        for(int i = 0; i < infix.length(); i++) {
            char nextCharacter = infix.charAt(i);
            
            switch (nextCharacter) {
                case 'a' : case 'b' : case 'c' : case 'd' : case 'e' : case 'f' : case 'g' :
                    postfix.append(nextCharacter);
                    break;
                    
                case '^' :
                    operatorStack.push(nextCharacter);
                    break;
                    
                case '+' : case '-' : case '*' : case '/' : 
                    while ((!operatorStack.isEmpty()) && (precedence(nextCharacter) <= precedence(operatorStack.peek()))) {
                            postfix.append(operatorStack.peek());
                            operatorStack.pop();
                    }
                    operatorStack.push(nextCharacter);
                    break;
                    
                case '(' : 
                    operatorStack.push(nextCharacter);
                    break;
                    
                case ')' : // stack is not empty if infix expression is valid
                    char topOperator = operatorStack.pop();
                    while (!operatorStack.isEmpty() && topOperator != '(') {
                        postfix.append(topOperator);
                        topOperator = operatorStack.pop();
                    }
                    break;  
                default : break; // ignore unexpected characters
            }
        }  
        
        while (!operatorStack.isEmpty()) {
            char topOperator = operatorStack.pop();
            postfix.append(topOperator);
        }
        
        return postfix;
    }
    
    public static int precedence(char nextC) {
        switch (nextC) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            default:
                return -1;
        }
    }
    
    public static void main(String[] args) {
        String a = "a*b/(c-d)";
        String b = "(a-b*c)/(d*e*f+g)";
        String c = "a/b*(c+(d-e))";
        String d = "a+b*c/e-f";
        
        //calling my infixtopostfix
        System.out.println("Infix 1 : a * b / (c - d)");
        System.out.print("Postfix 1 : ");
        System.out.println(changeExpression(a));
        System.out.println();
        
        System.out.println("Infix 2 : (a - b * c) / (d * e * f + g)");
        System.out.print("Postfix 2 : ");
        System.out.println(changeExpression(b));
        System.out.println();
        
        System.out.println("Infix 3 : a / b * (c + (d - e))");
        System.out.print("Postfix 3 : ");
        System.out.println(changeExpression(c));
        
      
        System.out.print("Postfix 4 : ");
        System.out.println(changeExpression(d));
        
    }
}
