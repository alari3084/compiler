import java.util.ArrayList;
/*
* This class is contributed in order to traverse the parse tree.
* */
public class ParseAnalysis {
    // Entrance
    public static void entrance(ArrayList<token> tokens){ParseAnalysis.CompUnit(0,tokens);}
    //编译单元 CompUnit → {Decl} {FuncDef} MainFuncDef // 1.是否存在Decl 2.是否存在
    //FuncDef
    public static void CompUnit(int from, ArrayList<token> tokens){
        int to = from;
        while((tokens.get(to).getType() == tokentype.CONSTTK && tokens.get(to + 1).getType() == tokentype.INTTK && tokens.get(to + 2).getType() != tokentype.MAINTK && tokens.get(to + 3).getType() != tokentype.LPARENT)||
                (tokens.get(to).getType() == tokentype.INTTK && tokens.get(to + 1).getType() != tokentype.MAINTK && tokens.get(to + 2).getType() != tokentype.LPARENT)){
            to = ParseAnalysis.Decl(to, tokens);
        }
        while((tokens.get(to).getType() == tokentype.VOIDTK || tokens.get(to).getType() == tokentype.INTTK)&&
                tokens.get(to+1).getType() != tokentype.MAINTK && tokens.get(to+2).getType() == tokentype.LPARENT){
            to = ParseAnalysis.FuncDef(to,tokens);
        }
        to = ParseAnalysis.MainFuncDef(to,tokens);
        pUnit.addUnit(new pUnit("CompUnit", tokens.subList(from,to)));
    }
    /*
    * The enums are ordered in alphaBet.
    * */

    /*加减表达式 AddExp → MulExp | AddExp ('+' | '−') MulExp // 1.MulExp 2.+ 需覆盖 3.-
需覆盖*/
    public static int AddExp(int from, ArrayList<token> tokens){
        int to = from;
        to = ParseAnalysis.MulExp(to,tokens);
        while(tokens.get(to).getType() == tokentype.PLUS || tokens.get(to).getType() == tokentype.MINU){
            pUnit.addUnit(new pUnit("AddExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));
            to++;
            to = ParseAnalysis.MulExp(to,tokens);
        }
        pUnit.addUnit(new pUnit("AddExp",tokens.subList(from,to)));
        return to;
    }
/*语句块 Block → '{' { BlockItem } '}' // 1.花括号内重复0次 2.花括号内重复多次*/
    public static int Block(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));
        to++;
        while(tokens.get(to).getType() != tokentype.RBRACE){
            to = ParseAnalysis.BlockItem(to, tokens);
        }
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit("Block",tokens.subList(from,to)));
        return to;
    }
/*语句块项 BlockItem → Decl | Stmt // 覆盖两种语句块项*/
    public static int BlockItem(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.CONSTTK || tokens.get(to).getType() == tokentype.INTTK){
            to = ParseAnalysis.Decl(to, tokens);
        }else{
            to = ParseAnalysis.Stmt(to, tokens);
        }
        return to;
    }
/*基本类型 BType → 'int' // 存在即可*/
    public static int BType(int from, ArrayList<token> tokens){
        int to = from;
        pUnit punit = new pUnit(tokens.get(to));to ++;
        pUnit.addUnit(punit);
        return to;
    }
/*条件表达式 Cond → LOrExp // 存在即可*/
    public static int Cond(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.LOrExp(to, tokens);
        pUnit.addUnit(new pUnit("Cond", tokens.subList(from,to)));
        return to;
    }
/*常量声明 ConstDecl → 'const' BType ConstDef { ',' ConstDef } ';' // 1.花括号内重
    复0次 2.花括号内重复多次*/
    public static int ConstDecl(int from, ArrayList<token> tokens){
        int to = from;
        pUnit punit = new pUnit(tokens.get(from));
        pUnit.addUnit(punit);to++;
        to = ParseAnalysis.BType(to, tokens);
        to = ParseAnalysis.ConstDef(to, tokens);
        while(tokens.get(to).getType() == tokentype.COMMA){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.ConstDef(to, tokens);
        }
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit("ConstDecl",tokens.subList(from,to)));
        return to;
    }
/*常数定义 ConstDef → Ident { '[' ConstExp ']' } '=' ConstInitVal // 包含普通变
量、一维数组、二维数组共三种情况*/
    public static int ConstDef(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.IDENFR){pUnit.addUnit(new pUnit(tokens.get(to)));to++;}
        while(tokens.get(to).getType() == tokentype.LBRACK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to ++;
            to = ParseAnalysis.ConstExp(to,tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        to = ParseAnalysis.ConstInitVal(to, tokens);
        pUnit.addUnit(new pUnit("ConstDef",tokens.subList(from,to)));
        return to;
    }
/*常量表达式 ConstExp → AddExp 注：使用的Ident 必须是常量 // 存在即可*/
    public static int ConstExp(int from, ArrayList<token> tokens){
        int to = from;
        to = ParseAnalysis.AddExp(to, tokens);
        pUnit.addUnit(new pUnit("ConstExp",tokens.subList(from,to)));
        return to;
    }
/*常量初值 ConstInitVal → ConstExp
| '{' [ ConstInitVal { ',' ConstInitVal } ] '}' // 1.常表达式初值 2.一维数组初值
3.二维数组初值*/
    public static int ConstInitVal(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.LBRACE){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            if(tokens.get(to).getType() != tokentype.RBRACE){
                to = ParseAnalysis.ConstInitVal(to, tokens);
            }
            while(tokens.get(to).getType() == tokentype.COMMA){
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                to = ParseAnalysis.ConstInitVal(to, tokens);
            }
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else{
            to = ParseAnalysis.ConstExp(to, tokens);
        }
        pUnit.addUnit(new pUnit("ConstInitVal",tokens.subList(from,to)));
        return to;
    }
/*声明 Decl → ConstDecl | VarDecl // 覆盖两种声明*/
    public static int Decl(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.CONSTTK){to = ParseAnalysis.ConstDecl(to, tokens);}
        else if(tokens.get(to).getType() == tokentype.INTTK){to = ParseAnalysis.ValDecl(to, tokens);}
        return to;
    }
/*相等性表达式 EqExp → RelExp | EqExp ('==' | '!=') RelExp // 1.RelExp 2.== 3.!= 均
需覆盖*/
    public static int EqExp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.RelExp(to, tokens);
        while(tokens.get(to).getType() == tokentype.EQL || tokens.get(to).getType() == tokentype.NEQ){
            pUnit.addUnit(new pUnit("EqExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.RelExp(to, tokens);
        }
        pUnit.addUnit(new pUnit("EqExp",tokens.subList(from,to)));
        return to;
    }
/*表达式 Exp → AddExp 注：SysY 表达式是int 型表达式 // 存在即可*/
    public static int Exp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.AddExp(to,tokens);
        pUnit.addUnit(new pUnit("Exp",tokens.subList(from,to)));
        return to;
    }
/*函数定义 FuncDef → FuncType Ident '(' [FuncFParams] ')' Block // 1.无形参 2.有形
参*/
    public static int FuncDef(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.FuncType(to,tokens);
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        if(tokens.get(to).getType() != tokentype.RPARENT)to = ParseAnalysis.FuncFParams(to, tokens);
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        to = ParseAnalysis.Block(to, tokens);
        pUnit.addUnit(new pUnit("FuncDef", tokens.subList(from,to)));
        return to;
    }
/*函数形参 FuncFParam → BType Ident ['[' ']' { '[' ConstExp ']' }] // 1.普通变量
2.一维数组变量 3.二维数组变量*/
    public static int FuncFParam(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.BType(to,tokens);
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        if(tokens.get(to).getType() == tokentype.LBRACK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            while(tokens.get(to).getType() == tokentype.LBRACK){
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                to = ParseAnalysis.ConstExp(to, tokens);
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            }
        }
        pUnit.addUnit(new pUnit("FuncFParam",tokens.subList(from,to)));
        return to;
    }
/*函数形参表 FuncFParams → FuncFParam { ',' FuncFParam } // 1.花括号内重复0次 2.花括号
内重复多次*/
    public static int FuncFParams(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.FuncFParam(to,tokens);
        while(tokens.get(to).getType() == tokentype.COMMA){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.FuncFParam(to, tokens);
        }
        pUnit.addUnit(new pUnit("FuncFParams", tokens.subList(from,to)));
        return to;
    }
/*函数实参表 FuncRParams → Exp { ',' Exp } // 1.花括号内重复0次 2.花括号内重复多次 3.
Exp需要覆盖数组传参和部分数组传参*/
    public static int FuncRParams(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.Exp(to,tokens);
        while(tokens.get(to).getType() == tokentype.COMMA){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Exp(to, tokens);
        }
        pUnit.addUnit(new pUnit("FuncRParams", tokens.subList(from,to)));
        return to;
    }
/*函数类型 FuncType → 'void' | 'int' // 覆盖两种类型的函数*/
    public static int FuncType(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit("FuncType",tokens.subList(from,to)));
        return to;
    }
/*变量初值 InitVal → Exp | '{' [ InitVal { ',' InitVal } ] '}'// 1.表达式初值 2.一
维数组初值 3.二维数组初值*/
    public static int InitVal(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.LBRACE){
            pUnit.addUnit(new pUnit(tokens.get(to)));to ++;
            if(tokens.get(to).getType() != tokentype.RBRACE){to = ParseAnalysis.InitVal(to, tokens);}
            while(tokens.get(to).getType() == tokentype.COMMA){
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                to = ParseAnalysis.InitVal(to, tokens);
            }
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else{
            to = ParseAnalysis.Exp(to, tokens);
        }
        pUnit.addUnit(new pUnit("InitVal",tokens.subList(from,to)));
        return to;
    }
/*integer-const → decimal-const | 0
decimal-const → nonzero-digit | decimal-const digit*/
    public static int IntConst(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        return to;
    }
/*逻辑与表达式 LAndExp → EqExp | LAndExp '&&' EqExp // 1.EqExp 2.&& 均需覆盖*/
    public static int LAndExp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.EqExp(to,tokens);
        while(tokens.get(to).getType() == tokentype.AND){
            pUnit.addUnit(new pUnit("LAndExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.EqExp(to, tokens);
        }
        pUnit.addUnit(new pUnit("LAndExp",tokens.subList(from,to)));
        return to;
    }
/*逻辑或表达式 LOrExp → LAndExp | LOrExp '||' LAndExp // 1.LAndExp 2.|| 均需覆盖*/
    public static int LOrExp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.LAndExp(to,tokens);
        while(tokens.get(to).getType() == tokentype.OR){
            pUnit.addUnit(new pUnit("LOrExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.LAndExp(to, tokens);
        }
        pUnit.addUnit(new pUnit("LOrExp",tokens.subList(from,to)));
        return to;
    }
/*左值表达式 LVal → Ident {'[' Exp ']'} //1.普通变量 2.一维数组 3.二维数组*/
    public static int LVal(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        while(tokens.get(to).getType() == tokentype.LBRACK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Exp(to, tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }
        pUnit.addUnit(new pUnit("LVal",tokens.subList(from,to)));
        return to;
    }
/*主函数定义 MainFuncDef → 'int' 'main' '(' ')' Block // 存在main函数*/
    public static int MainFuncDef(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        to = ParseAnalysis.Block(to, tokens);
        pUnit.addUnit(new pUnit("MainFuncDef",tokens.subList(from,to)));
        return to;
    }
/*乘除模表达式 MulExp → UnaryExp | MulExp ('*' | '/' | '%') UnaryExp // 1.UnaryExp
2.* 3./ 4.% 均需覆盖*/
    public static int MulExp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.UnaryExp(to,tokens);
        while(tokens.get(to).getType() == tokentype.MULT || tokens.get(to).getType() == tokentype.DIV || tokens.get(to).getType() == tokentype.MOD){
            pUnit.addUnit(new pUnit("MulExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.UnaryExp(to, tokens);
        }
        pUnit.addUnit(new pUnit("MulExp",tokens.subList(from,to)));
        return to;
    }
/*数值 Number → IntConst // 存在即可*/
    public static int Number(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.IntConst(to,tokens);
        pUnit.addUnit(new pUnit("Number",tokens.subList(from,to)));
        return to;
    }
/*基本表达式 PrimaryExp → '(' Exp ')' | LVal | Number // 三种情况均需覆盖*/
    public static int PrimaryExp(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.LPARENT){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Exp(to, tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else if(tokens.get(to).getType() == tokentype.INTCON){to = ParseAnalysis.Number(to, tokens);}
        else{to = ParseAnalysis.LVal(to, tokens);}
        pUnit.addUnit(new pUnit("PrimaryExp",tokens.subList(from,to)));
        return to;
    }
/*关系表达式 RelExp → AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp // 1.AddExp
2.< 3.> 4.<= 5.>= 均需覆盖*/
    public static int RelExp(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.AddExp(to,tokens);
        while(tokens.get(to).getType() == tokentype.GRE || tokens.get(to).getType() == tokentype.GEQ || tokens.get(to).getType() == tokentype.LEQ || tokens.get(to).getType() == tokentype.LSS){
            pUnit.addUnit(new pUnit("RelExp",tokens.subList(from,to)));
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.AddExp(to,tokens);
        }
        pUnit.addUnit(new pUnit("RelExp",tokens.subList(from,to)));
        return to;
    }
/*语句 Stmt → LVal '=' Exp ';' // 每种类型的语句都要覆盖
| [Exp] ';' //有无Exp两种情况
| Block
| 'if' '(' Cond ')' Stmt [ 'else' Stmt ] // 1.有else 2.无else
| 'while' '(' Cond ')' Stmt
| 'break' ';' | 'continue' ';'
| 'return' [Exp] ';' // 1.有Exp 2.无Exp
| LVal '=' 'getint''('')'';'
| 'printf''('FormatString{','Exp}')'';' // 1.有Exp 2.无Exp*/
    public static int Stmt(int from, ArrayList<token> tokens){
        int to = from;
        token tk = tokens.get(to);
        if(tk.getType() == tokentype.IFTK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Cond(to, tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Stmt(to, tokens);
            if(tokens.get(to).getType() == tokentype.ELSETK){
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                to = ParseAnalysis.Stmt(to, tokens);
            }
        }else if(tk.getType() == tokentype.WHILETK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Cond(to, tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.Stmt(to, tokens);
        }else if(tk.getType() == tokentype.BREAKTK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else if(tk.getType() == tokentype.CONTINUETK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else if(tk.getType() == tokentype.RETURNTK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            if(tokens.get(to).getType() != tokentype.SEMICN){to = ParseAnalysis.Exp(to, tokens);}
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else if(tk.getType() == tokentype.PRINTFTK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            while(tokens.get(to).getType() == tokentype.COMMA){
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                to = ParseAnalysis.Exp(to, tokens);
            }
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else if(tokens.get(to).getType() == tokentype.LBRACE){
            to = ParseAnalysis.Block(to, tokens);
        }else if(tokens.get(to).getType() == tokentype.IDENFR || tokens.get(to).getType() == tokentype.INTCON){
            int cur = to;
            boolean flag = false;
            while(tokens.get(cur).getType() != tokentype.SEMICN){
                if(tokens.get(cur).getType() == tokentype.ASSIGN){
                    flag = true;break;
                }cur++;
            }
            if(flag && tokens.get(to).getType() == tokentype.IDENFR){
                to = ParseAnalysis.LVal(to ,tokens);
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                if(tokens.get(to).getType() == tokentype.GETINTTK){
                    pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                    pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                    pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                    pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                }else{
                    to = ParseAnalysis.Exp(to, tokens);
                    pUnit.addUnit(new pUnit(tokens.get(to)));to++;
                }
            }else{
                if(tokens.get(to).getType() != tokentype.SEMICN){
                    to = ParseAnalysis.Exp(to, tokens);
                }
                pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            }
        }else if(tokens.get(to).getType() == tokentype.SEMICN){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }else{
            if(tokens.get(to).getType() != tokentype.SEMICN){
                to = ParseAnalysis.Exp(to, tokens);
            }
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }
        pUnit.addUnit(new pUnit("Stmt",tokens.subList(from,to)));
        return to;
    }
/*一元表达式 UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' // 3种情况均需覆盖,
函数调用也需要覆盖FuncRParams的不同情况
| UnaryOp UnaryExp // 存在即可*/
    public static int UnaryExp(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.IDENFR && tokens.get(to+1).getType() == tokentype.LPARENT){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            if(tokens.get(to).getType() != tokentype.RPARENT){
                to = ParseAnalysis.FuncRParams(to, tokens);
            }
            pUnit.addUnit(new pUnit(tokens.get(to)));to ++;
        }else if(tokens.get(to).getType() == tokentype.PLUS || tokens.get(to).getType() == tokentype.NOT || tokens.get(to).getType() == tokentype.MINU){
            to = ParseAnalysis.UnaryOp(to,tokens);
            to = ParseAnalysis.UnaryExp(to,tokens);
        }else{to = ParseAnalysis.PrimaryExp(to, tokens);}
        pUnit.addUnit(new pUnit("UnaryExp",tokens.subList(from,to)));
        return to;
    }
/*单目运算符 UnaryOp → '+' | '−' | '!' 注：'!'仅出现在条件表达式中 // 三种均需覆盖*/
    public static int UnaryOp(int from, ArrayList<token> tokens){
        int to = from;
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit("UnaryOp",tokens.subList(from,to)));
        return to;
    }
/*变量声明 VarDecl → BType VarDef { ',' VarDef } ';' // 1.花括号内重复0次 2.花括号内
重复多次*/
    public static int ValDecl(int from, ArrayList<token> tokens){
        int to = from;to = ParseAnalysis.BType(to,tokens);
        to = ParseAnalysis.VarDef(to, tokens);
        while(tokens.get(to).getType() == tokentype.COMMA){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.VarDef(to,tokens);
        }
        pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        pUnit.addUnit(new pUnit("VarDecl",tokens.subList(from,to)));
        return to;

    }
/*变量定义 VarDef → Ident { '[' ConstExp ']' } // 包含普通变量、一维数组、二维数组定义
| Ident { '[' ConstExp ']' } '=' InitVal*/
    public static int VarDef(int from, ArrayList<token> tokens){
        int to = from;
        if(tokens.get(to).getType() == tokentype.IDENFR){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }
        while(tokens.get(to).getType() == tokentype.LBRACK){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.ConstExp(to, tokens);
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
        }
        if(tokens.get(to).getType() == tokentype.ASSIGN){
            pUnit.addUnit(new pUnit(tokens.get(to)));to++;
            to = ParseAnalysis.InitVal(to, tokens);
        }
        pUnit.addUnit(new pUnit("VarDef",tokens.subList(from,to)));
        return to;
    }
}
