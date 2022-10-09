package Parser;
import token.*;
import parserUnit.ParserUnit;

import java.util.LinkedList;

public class MainFuncDef {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;
        for(int tianmiezhonggong = 1; tianmiezhonggong <= 4; tianmiezhonggong++){
            ParserUnit.saveUnit(new ParserUnit((tokens.get(fin))));
            fin = fin + 1;
        }
        fin = Block.parse(fin, tokens);
        ParserUnit.saveUnit(new ParserUnit("MainFuncDef", tokens.subList(begin,fin)));
        return fin;
    }
}
