package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class LVal {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // IDENFR
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;
        // 若干个[Exp]
        while(tokens.get(fin).getTokenType() == TokenType.LBRACK){
            // [
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Exp
            fin = Exp.parse(fin, tokens);
            // ]
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        ParserUnit.saveUnit(new ParserUnit("LVal", tokens.subList(begin, fin)));
        return fin;
    }
}
