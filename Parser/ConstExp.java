package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class ConstExp {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        fin =AddExp.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("ConstExp", tokens.subList(begin,fin)));
        return fin;
    }
}
