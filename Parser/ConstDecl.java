package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class ConstDecl {
    public static int parse(int begin, LinkedList<Token> tokens ){
        int fin = begin;

        //const
        ParserUnit parserUnit = new ParserUnit(tokens.get(begin));
        ParserUnit.saveUnit(parserUnit);
        fin = fin + 1;//

        //BType
        fin = BType.parse(fin, tokens);

        //ConstDef(0,n]
        fin = ConstDef.parse(fin, tokens);
        while(tokens.get(fin).getTokenType() == TokenType.COMMA){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = ConstDef.parse(fin, tokens);
        }

        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        parserUnit = new ParserUnit("ConstDecl", tokens.subList(begin, fin));
        ParserUnit.saveUnit(parserUnit);
        return fin;

    }
}
