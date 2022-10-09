package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class PrimaryExp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        //(Exp)
        if(tokens.get(fin).getTokenType() == TokenType.LPARENT){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Exp
            fin = Exp.parse(fin, tokens);
            // )
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        //Number
        else if(tokens.get(fin).getTokenType() == TokenType.INTCON){
            fin = Number.parse(fin, tokens);
        }

        //Lval
        else fin = LVal.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("PrimaryExp", tokens.subList(begin,fin)));
        return fin;
    }
}
