package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class FuncRParams {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        fin = Exp.parse(fin, tokens);

        while(tokens.get(fin).tokenType == TokenType.COMMA){
            // ,
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            fin = Exp.parse(fin, tokens);
        }

        ParserUnit.saveUnit(new ParserUnit("FuncRParams", tokens.subList(begin,fin)));
        return fin;
    }

}
