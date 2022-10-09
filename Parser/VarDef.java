package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class VarDef {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // Ident
        if(tokens.get(fin).getTokenType() == TokenType.IDENFR) {
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin++;
        }
        // []s
        while(tokens.get(fin).getTokenType() == TokenType.LBRACK){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin ++;
            // ConstExp
            fin = ConstExp.parse(fin, tokens);
            // ]
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin ++;
        }
        // = initVal
        if(tokens.get(fin).getTokenType() == TokenType.ASSIGN){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin++;
            fin = InitVal.parse(tokens, fin);
        }
        ParserUnit parserUnit = new ParserUnit("VarDef", tokens.subList(begin, fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }

}
