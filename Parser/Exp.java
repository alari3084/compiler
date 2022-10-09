package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class Exp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        fin = AddExp.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("Exp", tokens.subList(begin,fin)));
        return fin;
    }
}
