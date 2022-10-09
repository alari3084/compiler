package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class UnaryExp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        //solve funcs
        if(tokens.get(fin).getTokenType() == TokenType.IDENFR && tokens.get(fin+1).getTokenType() == TokenType.LPARENT){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // (
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            if(tokens.get(fin).getTokenType() != TokenType.RPARENT){
                fin = FuncRParams.parse(fin, tokens);
            }
            // )
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        //then it must be unaryOp or unaryExp
        else if(tokens.get(fin).getTokenType() == TokenType.PLUS || tokens.get(fin).getTokenType() == TokenType.NOT || tokens.get(fin).getTokenType() == TokenType.MINU){
            fin = UnaryOp.parse(fin, tokens);
            fin = UnaryExp.parse(fin, tokens);
        }

        //PrimaryExp
        else fin = PrimaryExp.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("UnaryExp", tokens.subList(begin,fin)));
        return fin;
    }
}
