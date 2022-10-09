package Parser;

import token.*;
import parserUnit.ParserUnit;

import java.util.LinkedList;

public class CompUnit {
    public static void parse(int begin, LinkedList<Token> tokens){
        int fin = begin;

        while(// 常量
                ( tokens.get(fin).getTokenType() == TokenType.CONSTTK && tokens.get(fin+1).getTokenType() == TokenType.INTTK &&
                        tokens.get(fin+2).getTokenType() != TokenType.MAINTK && tokens.get(fin+3).getTokenType() != TokenType.LPARENT)||
                        // 变量
                        ( tokens.get(fin).getTokenType() == TokenType.INTTK && tokens.get(fin+1).getTokenType() != TokenType.MAINTK &&
                                tokens.get(fin+2).getTokenType() != TokenType.LPARENT)){
            fin = Decl.parse(fin, tokens);
        }

        while((
                // 无返回值
                tokens.get(fin).getTokenType() == TokenType.VOIDTK ||
                        // 返回int类型
                        tokens.get(fin).getTokenType() == TokenType.INTTK
        ) && (
                tokens.get(fin+1).getTokenType() != TokenType.MAINTK
        ) && (
                tokens.get(fin+2).getTokenType() == TokenType.LPARENT
        )){
            fin = FuncDef.parse(fin, tokens);
        }

        fin = MainFuncDef.parse(fin, tokens);

        ParserUnit.saveUnit(new ParserUnit("CompUnit", tokens.subList(begin, fin)));
    }

}
