package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class Cond {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        // LorExp
        fin = LOrExp.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("Cond", tokens.subList(begin,fin)));
        return fin;
    }
}
