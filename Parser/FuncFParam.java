package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class FuncFParam {
    // BType Ident 【'【''】'{ '【'ConstExp'】' } ]
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // BType
        fin = BType.parse(fin, tokens);

        // Ident
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        // 是否是数组
        if(tokens.get(fin).getTokenType() == TokenType.LBRACK){
            // [
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // ]
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // 多个【ConstExp】
            while (tokens.get(fin).getTokenType() == TokenType.LBRACK){
                // [
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;
                // ConstExp
                fin = ConstExp.parse(fin, tokens);
                // ]
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;
            }
        }
        ParserUnit.saveUnit(new ParserUnit("FuncFParam", tokens.subList(begin, fin)));
        return fin;
    }

}
