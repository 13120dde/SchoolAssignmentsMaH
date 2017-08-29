package algoritmerProjekt1;

/**This class is responsible for parsing a String to check for bracket mismatch. It's only method is static and checks
 * if brackets are missing or brackets do not match.
 *
 * Created by Patrik Lind on 2016-09-08.
 */
public class StringParser {

    /**This static method takes a String as argument and parses it to check if there is a mismatch in the brackets.
     * The method checks if the brackets match (are of the same type) and if there are any missing brackets. The object
     * returned by this method is a String that represents the result of the parse.
     *
     * @param input : String
     * @return : String
     */
    public static String parseString(String input) {

        Stack<Character> leftBrackets = new Stack();

        //To be able to inspect if left brackets are missing.
        Stack<Character> rightBrackets = new Stack();

        //Check if brackets are matching
        for (int i = 0; i < input.length(); i++) {
            char temp = input.charAt(i);

            switch (temp) {
                case '(':
                case '{':
                case '[':
                    leftBrackets.push(temp);
                    break;
                case ')':
                    rightBrackets.push(')');
                    //To prevent nullpointer exception if the first bracket encountered in the input is a right bracket.
                    if(leftBrackets.peek()==null){
                        break;
                    }
                    if (leftBrackets.peek() == '(') {
                        leftBrackets.pop();
                        rightBrackets.pop();
                    } else {
                        return "Brackets do not match.\nString parsed...";
                    }
                    break;
                case '}':
                    rightBrackets.push('}');
                    if(leftBrackets.peek()==null){
                        break;
                    }
                    if (leftBrackets.peek() == '{') {
                        leftBrackets.pop();
                        rightBrackets.pop();
                    } else {
                        return "Brackets do not match.\nString parsed...";
                    }
                    break;

                case ']':
                    rightBrackets.push(']');
                    if(leftBrackets.peek()==null){
                        break;
                    }
                    if (leftBrackets.peek() == '[') {
                        leftBrackets.pop();
                        rightBrackets.pop();
                    } else {
                        return "Brackets do not match.\nString parsed...";
                    }
                    break;
            }
        }

        //Check if a right bracket is missing.
        if(leftBrackets.count()>0){
            return "right brackets are missing.\nString parsed...";
        }

        //Check if a left bracket is missing.
        if(rightBrackets.count()>0){
            return "left brackets are missing.\nString parsed...";
        }

        return "String parsed without issues...";
    }

}
