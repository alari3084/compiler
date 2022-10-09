package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class InitVal {
    public static int parse(LinkedList<Token> tokens, int begin) {
        int fin = begin;
        // '{'【 InitVal {'，'InitVal }】'}'//
        if(tokens.get(fin).getTokenType() == TokenType.LBRACE){
            // {
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin++;
            // 0个或多个InitVal
            if(tokens.get(fin).tokenType != TokenType.RBRACE){
                fin = InitVal.parse(tokens, fin);
            }
            while(tokens.get(fin).tokenType == TokenType.COMMA){
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin++;
                fin = InitVal.parse(tokens, fin);
            }
            // }
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin++;
        }
        // Exp
        else{
            fin = Exp.parse(fin, tokens);
        }

        ParserUnit parserUnit = new ParserUnit("InitVal", tokens.subList(begin, fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }

}
