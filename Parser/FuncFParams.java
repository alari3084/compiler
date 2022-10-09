package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class FuncFParams {
    // FuncFParam {'，'FunCFParam }
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // FuncFParam
        fin = FuncFParam.parse(fin, tokens);
        // 若干个FuncFParam
        while(tokens.get(fin).getTokenType() == TokenType.COMMA){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            fin = FuncFParam.parse(fin, tokens);
        }
        ParserUnit.saveUnit(new ParserUnit("FuncFParams", tokens.subList(begin, fin)));
        return fin;
    }
}
