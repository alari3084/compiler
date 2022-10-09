package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class LAndExp {
    // EqExp | LAndExp && EqExp
    // LAndExp -> EqExp{ '&&' EqExp }
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // EqExp
        fin = EqExp.parse(fin, tokens);
        // ParserUnit.saveUnit(new ParserUnit("LAndExp", tokens.subList(begin, end)));
        // 若干个 && EqExp
        while(tokens.get(fin).getTokenType() == TokenType.AND){
            ParserUnit.saveUnit(new ParserUnit("LAndExp", tokens.subList(begin, fin)));
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = EqExp.parse(fin, tokens);
        }
        ParserUnit.saveUnit(new ParserUnit("LAndExp", tokens.subList(begin, fin)));
        return fin;
    }
}
