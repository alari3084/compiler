package Parser;

import token.*;
import parserUnit.ParserUnit;

import java.util.LinkedList;

public class ParserController {
    public static void parse(LinkedList<Token> tokens) {
        CompUnit.parse(0, tokens);
    }
}
