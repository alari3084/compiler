package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class VarDecl {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        //BType
        fin = BType.parse(fin, tokens);

        //VarDef
        fin = VarDef.parse(fin, tokens);
        while(tokens.get(fin).getTokenType() == TokenType.COMMA){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = VarDef.parse(fin, tokens);
        }

        // ;
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;
        ParserUnit parserUnit = new ParserUnit("VarDecl", tokens.subList(begin,fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }
}
