package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class RelExp {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // AddExp
        fin = AddExp.parse(fin, tokens);
        // ParserUnit.saveUnit(new ParserUnit("RelExp", tokens.subList(begin, fin)));
        Token token = tokens.get(fin);
        // 检验是否有多个 + 或 -
        while(tokens.get(fin).getTokenType() == TokenType.GRE || tokens.get(fin).getTokenType() == TokenType.GEQ ||
                tokens.get(fin).getTokenType() == TokenType.LEQ || tokens.get(fin).getTokenType() == TokenType.LSS
        ){
            ParserUnit.saveUnit(new ParserUnit("RelExp", tokens.subList(begin, fin)));
            // 处理符号
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // 处理AddExp
            fin = AddExp.parse(fin, tokens);
        }
        ParserUnit.saveUnit(new ParserUnit("RelExp", tokens.subList(begin, fin)));
        return fin;
    }

}
