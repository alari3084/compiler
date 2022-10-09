package Parser;

import token.*;

import java.util.LinkedList;

public class Decl {
    public static int parse(int begin, LinkedList<Token> tokens){
        int fin = begin;

        // token current is const
        if(tokens.get(fin).getTokenType() == TokenType.CONSTTK){
            fin = ConstDecl.parse(fin, tokens);
        }
        //now token current must be int
        else if(tokens.get(fin).getTokenType() == TokenType.INTTK){
            fin = VarDecl.parse(fin, tokens);
        }

        return fin;
    }
}
