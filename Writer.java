import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Objects;

public class Writer {
    public static LinkedList<Token> tokens = new LinkedList<>();

    public static String filename = "output.txt";

    public static void saveToken(int lines, String token, TokenType tokenType){
        Token token1 = new Token();
        token1.setContent(token);
        token1.setLine(lines);
        if(Objects.equals(tokenType, TokenType.UNDEFINED)){
            TokenType tokenType1 = Token.getTokenType(token);
            token1.setTokenType(tokenType1);
        } else {
            token1.setTokenType(tokenType);
        }
        tokens.add(token1);
    }

    public static void writeToken() throws FileNotFoundException {
        PrintStream ps = new PrintStream(filename);
        //可能会出现异常，直接throws就行了
        System.setOut(ps);
        //把创建的打印输出流赋给系统。即系统下次向 ps输出
        for(Token token : tokens){
            System.out.println(token.getTokenType() + " " + token.getContent());
        }
        ps.close();
    }
}
