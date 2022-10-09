package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class MulExp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;

        fin = UnaryExp.parse(fin, tokens);


        while(tokens.get(fin).getTokenType() == TokenType.MULT || tokens.get(fin).getTokenType() == TokenType.DIV || tokens.get(fin).getTokenType() == TokenType.MOD){
            ParserUnit.saveUnit(new ParserUnit("MulExp",tokens.subList(begin,fin)));
            //expose * or /
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = UnaryExp.parse(fin, tokens);

        }
        ParserUnit.saveUnit(new ParserUnit("MulExp",tokens.subList(begin,fin)));
        return fin;

    }
}
