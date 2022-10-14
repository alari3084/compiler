public class token {
    public tokentype type;

    public tokentype getType(){return type;}

    public String getContent(){return content;}

    public void setType(tokentype type){this.type = type;}

    public void setContent(String content) {
        this.content = content;
    }
    public void setLine(int line){this.line = line;}
    public String content;
    public int line;

    public static tokentype getTokenType(String content){
        switch (content) {
            case "main":
                return tokentype.MAINTK;
            case "const":
                return tokentype.CONSTTK;
            case "int":
                return tokentype.INTTK;
            case "break":
                return tokentype.BREAKTK;
            case "continue":
                return tokentype.CONTINUETK;
            case "if":
                return tokentype.IFTK;
            case "else":
                return tokentype.ELSETK;
            case "!":
                return tokentype.NOT;
            case "&&":
                return tokentype.AND;
            case "||":
                return tokentype.OR;
            case "while":
                return tokentype.WHILETK;
            case "getint":
                return tokentype.GETINTTK;
            case "printf":
                return tokentype.PRINTFTK;
            case "return":
                return tokentype.RETURNTK;
            case "+":
                return tokentype.PLUS;
            case "-":
                return tokentype.MINU;
            case "void":
                return tokentype.VOIDTK;
            case "*":
                return tokentype.MULT;
            case "/":
                return tokentype.DIV;
            case "%":
                return tokentype.MOD;
            case "<":
                return tokentype.LSS;
            case "<=":
                return tokentype.LEQ;
            case ">":
                return tokentype.GRE;
            case ">=":
                return tokentype.GEQ;
            case "==":
                return tokentype.EQL;
            case "!=":
                return tokentype.NEQ;
            case "=":
                return tokentype.ASSIGN;
            case ";":
                return tokentype.SEMICN;
            case ",":
                return tokentype.COMMA;
            case "(":
                return tokentype.LPARENT;
            case ")":
                return tokentype.RPARENT;
            case "[":
                return tokentype.LBRACK;
            case "]":
                return tokentype.RBRACK;
            case "{":
                return tokentype.LBRACE;
            case "}":
                return tokentype.RBRACE;
            default:
                return tokentype.IDENFR;
        }
    }

}
