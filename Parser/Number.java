package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class Number {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        fin = IntConst.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("Number", tokens.subList(begin, fin)));
        return fin;
    }
}
