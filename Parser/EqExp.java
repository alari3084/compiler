package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class EqExp {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // RelExp
        fin = RelExp.parse(fin, tokens);
        // ParserUnit.saveUnit(new ParserUnit("EqExp", tokens.subList(begin, fin)));
        // 检验是否有多个 + 或 -
        while(tokens.get(fin).getTokenType() == TokenType.EQL || tokens.get(fin).getTokenType() == TokenType.NEQ){
            ParserUnit.saveUnit(new ParserUnit("EqExp", tokens.subList(begin, fin)));
            // 处理符号
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // 处理RelExp
            fin = RelExp.parse(fin, tokens);
        }
        ParserUnit.saveUnit(new ParserUnit("EqExp", tokens.subList(begin, fin)));
        return fin;
    }

}
