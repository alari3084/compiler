import Parser.ParserController;
import token.*;
import parserUnit.ParserUnit;

import java.util.LinkedList;
import java.io.IOException;

import static token.Tokens.tokens;

public class Compiler {
    public static void main(String[] args)throws IOException{
        Reader.readFile();
        ParserController.parse(tokens);
        Tokens.writeToken();
    }
}

