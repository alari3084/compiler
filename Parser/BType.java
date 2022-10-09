package Parser;

import token.*;
import parserUnit.ParserUnit;

import java.util.LinkedList;

public class BType {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        ParserUnit parserUnit = new ParserUnit(tokens.get(fin));
        fin = fin + 1;
        ParserUnit.saveUnit(parserUnit);
        return fin;
    }
}
