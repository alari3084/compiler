package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class AddExp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        fin = MulExp.parse(fin, tokens);
        while(tokens.get(fin).getTokenType() == TokenType.PLUS || tokens.get(fin).getTokenType() == TokenType.MINU){
            ParserUnit.saveUnit(new ParserUnit("AddExp", tokens.subList(begin,fin)));
            //expose + or -
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // goto MulExp
            fin = MulExp.parse(fin,tokens);

        }
        ParserUnit.saveUnit(new ParserUnit("AddExp", tokens.subList(begin,fin)));
        return fin;
    }
}
