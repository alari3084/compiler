package Parser;

import token.*;
import parserUnit.ParserUnit;
import java.util.LinkedList;

public class Stmt {
    public static int parse(int begin, LinkedList<Token> tokens) {
        int fin = begin;
        Token token = tokens.get(fin);
        // if
        if(token.getTokenType() == TokenType.IFTK){
            // if
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // (
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Cond
            fin = Cond.parse(fin, tokens);
            // )
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Stmt
            fin = Stmt.parse(fin, tokens);
            // else
            if (tokens.get(fin).getTokenType() == TokenType.ELSETK){
                // else
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;

                //Stmt
                fin = Stmt.parse(fin, tokens);
            }
        }
        // while
        else if(token.getTokenType() == TokenType.WHILETK){
            // while
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // (
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Cond
            fin = Cond.parse(fin, tokens);
            // )
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            // Stmt
            fin = Stmt.parse(fin, tokens);
        }
        // break
        else if(token.getTokenType() == TokenType.BREAKTK){
            // break
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            // ;
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        // continue
        else if(token.getTokenType() == TokenType.CONTINUETK){
            // continue
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            // ;
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }

        // return
        else if(token.getTokenType() == TokenType.RETURNTK){
            // return
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            // exp
            if(tokens.get(fin).getTokenType() != TokenType.SEMICN){
                fin = Exp.parse(fin, tokens);
            }

            // ;
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        // printf
        else if(token.getTokenType() == TokenType.PRINTFTK){
            // printf
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            //(
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            // formatString
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
            while(tokens.get(fin).getTokenType() == TokenType.COMMA) {
                // ,
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;

                // Exp
                fin = Exp.parse(fin, tokens);
            }
            // )
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;

            // ;
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        // Block
        else if(tokens.get(fin).getTokenType() == TokenType.LBRACE){
            fin = Block.parse(fin, tokens);
        }
        // LVal || [Exp]
        else if(tokens.get(fin).getTokenType() == TokenType.IDENFR || tokens.get(fin).getTokenType() == TokenType.INTCON){
            int i = fin, flag = 0;
            while(tokens.get(i).getTokenType() != TokenType.SEMICN){
                if(tokens.get(i).getTokenType() == TokenType.ASSIGN){
                    flag = 1;
                    break;
                }
                i++;
            }
            // LVal
            if(flag == 1 && tokens.get(fin).getTokenType() == TokenType.IDENFR) {
                fin = LVal.parse(fin, tokens);
                // =
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;
                // getint
                if(tokens.get(fin).getTokenType() == TokenType.GETINTTK){
                    // getint
                    ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                    fin = fin + 1;
                    // (
                    ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                    fin = fin + 1;
                    // )
                    ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                    fin = fin + 1;
                    // ;
                    ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                    fin = fin + 1;
                }
                // Exp ;
                else {
                    fin = Exp.parse(fin, tokens);
                    // ;
                    ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                    fin = fin + 1;
                }
            }
            // [Exp] ';'
            else {
                if(tokens.get(fin).getTokenType() != TokenType.SEMICN){
                    fin = Exp.parse(fin, tokens);
                }
                // ;
                ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
                fin = fin + 1;
            }
        }
        // ;
        else if (tokens.get(fin).getTokenType() == TokenType.SEMICN){
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        else {
            if(tokens.get(fin).getTokenType() != TokenType.SEMICN){
                fin = Exp.parse(fin, tokens);
            }
            // ;
            ParserUnit.saveUnit(new ParserUnit(tokens.get(fin)));
            fin = fin + 1;
        }
        ParserUnit.saveUnit(new ParserUnit("Stmt", tokens.subList(begin, fin)));
        return fin;
    }

}
