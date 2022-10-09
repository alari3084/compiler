package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class FuncType {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
        fin = fin + 1;
        ParserUnit.saveUnit(new ParserUnit("FuncType", tokens.subList(begin, fin)));
        return fin;
    }
}
