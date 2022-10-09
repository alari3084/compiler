package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class FuncDef {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        //FuncType
        fin = FuncType.parse(fin, tokens);

        //Ident
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        // (
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        //FuncFParams
        if(tokens.get(fin).getTokenType() != TokenType.RPARENT){
            fin = FuncFParams.parse(fin, tokens);

        }

        // )
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        //Block

        fin = Block.parse(fin,tokens);

        ParserUnit.saveUnit(new ParserUnit("FuncDef", tokens.subList(begin, fin)));
        return fin;
    }
}
