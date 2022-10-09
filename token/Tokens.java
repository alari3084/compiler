package token;
import parserUnit.ParserUnit;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;

public class Tokens {
    public static LinkedList<Token> tokens = new LinkedList<>();
    public static LinkedList<Token> saveToken(int lines, String token, TokenType tokenType){
        Token token1 = new Token();
        token1.setContent(token);
        token1.setLine(lines);
        if(!Objects.equals(tokenType, TokenType.UNDEFINED)){
            token1.setTokenType(tokenType);
        } else {
            TokenType tokenType1 = Token.getTokenType(token);
            token1.setTokenType(tokenType1);
        }
        tokens.add(token1);
        return tokens;
    }

    public static void writeToken() throws FileNotFoundException {
        // Writer.writeToken(tokens);
        ParserUnit.writeUnit();//
    }
}
