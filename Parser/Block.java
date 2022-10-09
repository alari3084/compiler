package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class Block {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        // {
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;
        // 若干个blockitem
        while(tokens.get(fin).getTokenType() != TokenType.RBRACE){
            fin = BlockItem.parse(fin, tokens);
        }

        // }
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;

        ParserUnit.saveUnit(new ParserUnit("Block", tokens.subList(begin, fin)));
        return fin;
    }
}
