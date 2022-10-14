import java.util.ArrayList;
import java.util.Objects;

public class Tokens {
    public static ArrayList<token> tokens = new ArrayList<>();
    public static ArrayList<token> saveToken(String tk, tokentype tokenType, int lines){
        token token1 = new token();
        token1.setContent(tk);
        token1.setLine(lines);
        if(Objects.equals(tokenType, tokenType.UNDEFINED)){
            tokentype tokenType1 = token.getTokenType(tk);
            token1.setType(tokenType1);
        } else {
            token1.setType(tokenType);
        }
        tokens.add(token1);
        return tokens;
    }
}
