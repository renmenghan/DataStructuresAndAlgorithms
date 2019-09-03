package 栈;

import java.util.Stack;

public class _20_有效的括号 {
	public boolean isValid(String s) {
       
		Stack<Character> leftStack = new Stack<Character>();
		for (int i= 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '{' || c == '[') {
				leftStack.push(c);
			}else {
				if (leftStack.size() == 0) {
					return false;
				}else {
					if (c==')' && leftStack.pop()!='(') {
						return false;
					} else if (c==']' && leftStack.pop()!='[') {
						return false;
					}else if (c=='}' && leftStack.pop()!='{') {
						return false;
					}
					
				}
			}
			
		}
		if (leftStack.size() != 0) {
			return false;
		}
		
		
		
		return true;
    }
}
