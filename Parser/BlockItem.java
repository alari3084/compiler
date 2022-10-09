package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class BlockItem {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        //decl | stmt
        if(tokens.get(fin).getTokenType() == TokenType.CONSTTK || tokens.get(fin).getTokenType() == TokenType.INTTK){
            fin = Decl.parse(fin, tokens);

        }else{
            fin = Stmt.parse(fin, tokens);
        }
        return fin;
    }
}
