package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class ConstInitVal {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // '{'【 ConstInitVal {'，'ConstInitVal }】'}'//
        if(tokens.get(fin).getTokenType() == TokenType.LBRACE){
            // {
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // 0个或多个ConstInitVal
            if(tokens.get(fin).tokenType != TokenType.RBRACE){
                fin = ConstInitVal.parse(fin, tokens);
            }
            while(tokens.get(fin).tokenType == TokenType.COMMA){
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;
                fin = ConstInitVal.parse(fin, tokens);
            }
            // }
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        // ConstExp
        else{
            fin = ConstExp.parse(fin, tokens);
        }

        ParserUnit parserUnit = new ParserUnit("ConstInitVal", tokens.subList(begin, fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }

}