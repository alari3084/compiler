package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class ConstDef {
    public static int parse(int begin,LinkedList<Token> tokens){
        int fin = begin;

        //ident
        if(tokens.get(fin).getTokenType() == TokenType.IDENFR) {
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        //[] rpts
        while(tokens.get(fin).getTokenType() == TokenType.LBRACK){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin ++;
            // ConstExp
            fin = ConstExp.parse(fin, tokens);
            // ]
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        //ConstInitVal
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;
        fin = ConstInitVal.parse(fin, tokens);
        ParserUnit parserUnit = new ParserUnit("ConstDef",tokens.subList(begin,fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }
}
