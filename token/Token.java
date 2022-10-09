package token;

import java.util.Objects;
public class Token {
    /**
     * token类型 tokeType
     //     */
    public TokenType tokenType;

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * token 的内容 content
     */
    public String content;

    /**
     * token所在行号 line
     */
    public int line;

    /**
     * 用来记录常数，标识符，格式字符串的值
     * symbol
     */
    public String symbol;

    public static TokenType getTokenType(String content) {
        switch (content) {
            case "main":
                return TokenType.MAINTK;
            case "const":
                return TokenType.CONSTTK;
            case "int":
                return TokenType.INTTK;
            case "break":
                return TokenType.BREAKTK;
            case "continue":
                return TokenType.CONTINUETK;
            case "if":
                return TokenType.IFTK;
            case "else":
                return TokenType.ELSETK;
            case "!":
                return TokenType.NOT;
            case "&&":
                return TokenType.AND;
            case "||":
                return TokenType.OR;
            case "while":
                return TokenType.WHILETK;
            case "getint":
                return TokenType.GETINTTK;
            case "printf":
                return TokenType.PRINTFTK;
            case "return":
                return TokenType.RETURNTK;
            case "+":
                return TokenType.PLUS;
            case "-":
                return TokenType.MINU;
            case "void":
                return TokenType.VOIDTK;
            case "*":
                return TokenType.MULT;
            case "/":
                return TokenType.DIV;
            case "%":
                return TokenType.MOD;
            case "<":
                return TokenType.LSS;
            case "<=":
                return TokenType.LEQ;
            case ">":
                return TokenType.GRE;
            case ">=":
                return TokenType.GEQ;
            case "==":
                return TokenType.EQL;
            case "!=":
                return TokenType.NEQ;
            case "=":
                return TokenType.ASSIGN;
            case ";":
                return TokenType.SEMICN;
            case ",":
                return TokenType.COMMA;
            case "(":
                return TokenType.LPARENT;
            case ")":
                return TokenType.RPARENT;
            case "[":
                return TokenType.LBRACK;
            case "]":
                return TokenType.RBRACK;
            case "{":
                return TokenType.LBRACE;
            case "}":
                return TokenType.RBRACE;
            default:
                return TokenType.IDENFR;
        }
    }

}
