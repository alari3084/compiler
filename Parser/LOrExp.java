package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class LOrExp {
    // LAndExp | LOrExp || LAndExp
// LOrExp -> LAndExp { '||' LAndExp }
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // LAndExp
        fin = LAndExp.parse(fin, tokens);
        // ParserUnit.saveUnit(new ParserUnit("LOrExp", tokens.subList(begin, fin)));
        // 若干个 || LAndExp
        while(tokens.get(fin).getTokenType() == TokenType.OR){
            ParserUnit.saveUnit(new ParserUnit("LOrExp", tokens.subList(begin, fin)));
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = LAndExp.parse(fin, tokens);
        }
        ParserUnit.saveUnit(new ParserUnit("LOrExp", tokens.subList(begin, fin)));
        return fin;
    }

}
