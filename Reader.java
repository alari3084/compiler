import token.Token;
import token.TokenType;
import token.Tokens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Reader {
    public static String filename = "testfile.txt";
    public static TokenType tokenType;
    public static boolean begin = true;
    public static char ch;
    public static String token;
    public static int length = 0;
    public static int flag = 0;
    /* 1表示intcon，2表示格式化字符串，3表示多行注释 */
    public static int lines = 0;
    public static int veryImportant = 0;
    public static LinkedList<Token> tokens = new LinkedList<Token>();
    public static void readFile() throws IOException{
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        /* 创建输入流，首先从缓冲区读入然后再从文件中读入 */
        String line = bufferedReader.readLine();
        lines = lines + 1;
        while(line != null){//ch
            checkLine(line, lines);
            if(token.length()<=0){//ch
                veryImportant = 1;
            } else {
                tokens = Tokens.saveToken(lines, token, tokenType);
                tokenType = TokenType.UNDEFINED;
                token = "";
            }
            line = bufferedReader.readLine();
            lines = lines + 1;
        }
        bufferedReader.close();
        fileReader.close();
        Writer.writeToken();
    }
    public static void isDoubleDigit(int lines, char ch,char chFollow){
        if(token.length() <= 0){
            veryImportant = 2;
            if(veryImportant <= 1){
                veryImportant = 3;
            }
        }else{
            tokens = Tokens.saveToken(lines, token, tokenType);
            tokenType = TokenType.UNDEFINED;
            begin = true;
        }//ch
        token = "";
        token += ch;
        if (!Objects.equals(chFollow, ' ')) {
            token += chFollow;
        }
        if(flag == 1){
            flag = 0;
        }
        tokens = Tokens.saveToken(lines, token, tokenType.UNDEFINED);
        tokenType = TokenType.UNDEFINED;
        begin = true;
        token = "";
    }
    private static boolean isKeyWord(int lines, char ch) {//ch
        if (ch == '[' || ch == ']' || ch == '{' || ch == '}' || ch == '(' || ch == ')'
                || ch == '+' || ch == '-' || ch == '%'
                || ch == ';' || ch == ',') {
            if (token.length() <= 0) {//
                veryImportant = 3;
                if(veryImportant == flag){
                    veryImportant = 4;
                }
            }else{
                if(flag == 2){
                    flag = 0;
                }
                tokens = Tokens.saveToken(lines, token, tokenType);
                begin = true;
            }
            token = "";
            token += ch;
            tokenType = TokenType.UNDEFINED;
            tokens = Tokens.saveToken(lines, token, tokenType);
            begin = true;
            token = "";
            return true;
        }
        return false;
    }
    private static void checkLine(String line, int lines) {
        token = "";
        tokenType = TokenType.UNDEFINED;
        for (int i = 0; i < line.length(); i++) {
            ch = line.charAt(i);
            // 字符串
            if(flag == 2) {
                token += ch;

                begin = false;
                if(ch == '"'){
                    flag = 0;
                    tokens = Tokens.saveToken(lines, token, tokenType);
                    token = "";
                    tokenType = TokenType.UNDEFINED;
                    begin = true;
                }
                continue;
            }
            // 多行注释
            //
            else if (flag == 3) {
                char chf = ' ';
                if (i < line.length() - 1) {
                    chf = line.charAt(i + 1);
                }
                if(ch == '*' && chf == '/'){
                    flag = 0;
                    i++;
                    tokenType = TokenType.UNDEFINED;
                    token = "";
                }
                continue;
            }
            // 遇到分割符，直接将前面的串拿出来
            if (ch == ' ' || ch == '\t') {

                if (token.length() > 0) {
                    if(flag == 3){
                        flag = 0;
                    }
                    tokens = Tokens.saveToken(lines, token, tokenType);
                    token = "";
                    tokenType = TokenType.UNDEFINED;
                    begin = true;
                }
                continue;
            }
            // token的开始字符
            // 数字开头都是常数
            if (ch >= '0' && ch <= '9' && begin) {
                flag = 1;
                tokenType = TokenType.INTCON;
            }
            // " 开头都是字符串
            else if (ch == '"') {
                if(flag != 2) {
                    flag = 2;
                    tokenType = TokenType.STRCON;
                    begin = false;
                }
            }

            if (ch != ' ' && ch != '\t') {
                // 判断读到的是否是单符号
                if (isKeyWord(lines, ch)) {
                    tokenType = TokenType.UNDEFINED;
                    begin = true;
                }
                else {
                    char ch2 = ' ';
                    if (i < line.length() - 1) {
                        ch2 = line.charAt(i + 1);
                    }
                    // 判断是不是双符号或者是双符号前缀的单符号
                    if (ch == '&' && ch2 == '&') {
                        isDoubleDigit(lines, ch, ch2);
                        i++;
                        tokenType = TokenType.UNDEFINED;
                    }
                    else if (ch == '|' && ch2 == '|') {
                        isDoubleDigit(lines, ch, ch2);
                        i++;
                        tokenType = TokenType.UNDEFINED;
                    }
                    //isDoubleDigit(lines, ch, ch2);
                    //isDoubleDigit(lines, ch, '');
                    else if (ch == '<') {
                        if (ch2 == '=') {
                            isDoubleDigit(lines, ch, ch2);
                            i++;
                        } else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                        tokenType = TokenType.UNDEFINED;
                    }
                    else if (ch == '>') {
                        if (ch2 == '=') {
                            isDoubleDigit(lines, ch, ch2);
                            i++;
                        } else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                        tokenType = TokenType.UNDEFINED;
                    }
                    else if (ch == '=') {
                        if (ch2 == '=') {
                            isDoubleDigit(lines, ch, ch2);
                            i++;
                        } else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                        tokenType = TokenType.UNDEFINED;
                    }
                    else if (ch == '!') {
                        if (ch2 == '=') {
                            isDoubleDigit(lines, ch, ch2);
                            i++;
                        } else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                        begin = true;
                        tokenType = TokenType.UNDEFINED;
                    }
                    else if (ch == '/') {
                        // //单行注释
                        if(ch2 == '/') {
                            if (token.length() > 0) {
                                if(flag == 1){
                                    tokens = Tokens.saveToken(lines, token, tokenType.INTCON);
                                    flag = 0;
                                }
                                else tokens = Tokens.saveToken(lines, token, tokenType);
                                token = "";
                                tokenType = TokenType.UNDEFINED;
                            }
                            break;
                        }
                        // /* 多行注释
                        if(ch2 == '*') {
                            if (token.length() > 0) {
                                if(flag == 1){
                                    tokens = Tokens.saveToken(lines, token, tokenType.INTCON);
                                    flag = 0;
                                }
                                tokens = Tokens.saveToken(lines, token, tokenType);
                                token = "";
                                tokenType = TokenType.UNDEFINED;
                            }
                            i++;
                            flag = 3;
                        }
                        // 除号
                        else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                    }
                    else if (ch == '*') {
                        if (ch2 == '/') {
                            isDoubleDigit(lines, ch, ch2);
                            i++;
                        } else {
                            isDoubleDigit(lines, ch, ' ');
                        }
                        tokenType = TokenType.UNDEFINED;
                    }
                    // 说明不是符号，将字符加到token中
                    else {
                        token += ch;
                        begin = false;
                    }
                }
            }
        }
    }
}
