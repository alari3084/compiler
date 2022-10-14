import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class reader {
    public static String filename = "testfile.txt";

    public static tokentype tktype;

    public static char pos;

    public static String token;

    public static int length = 0;

    public static int lines = 0;

    public static ArrayList<token> tokens = new ArrayList<token>();

    public static boolean strflag = false;

    public static boolean numflag = false;

    public static boolean noteflag = false;

    public static boolean startflag = true;

    public static boolean singlenoteflag = false;

    public static ArrayList<token> readFile() throws IOException{
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();

        while(line != null){
            readLine(line, lines);
            //
            if(token.length()>0){
                tokens = Tokens.saveToken(token,tktype,lines);
                tktype = tokentype.UNDEFINED;
                token = "";
            }
            line = bufferedReader.readLine();
            lines++;
        }
        return tokens;
    }

    private static void setNumflag(int length){
        if(length > 0){
            if(numflag){
                numflag = false;
            }
        }
        startflag = true;
    }
    private static boolean isSingle(char pos,int lines){
        if (pos == '[' || pos == ']' || pos == '{' || pos == '}' || pos == '(' || pos == ')'
                || pos == '+' || pos == '-' || pos == '%'
                || pos == ';' || pos == ',') {
            setNumflag(token.length());
            if(token.length()>0)tokens = Tokens.saveToken(token,tktype,lines);
            setStartFlag(1);
            token = "";
            token += pos;
            tktype = tokentype.UNDEFINED;
            tokens = Tokens.saveToken(token,tktype,lines);
            startflag = true;
            token = "";
            return true;
        }
        return false;
    }
    private static void doubleKey(char pos, char nextpos, int lines){
        if(token.length()>0){
            tokens = Tokens.saveToken(token,tktype,lines);
            tktype = tokentype.UNDEFINED;
            startflag = true;
        }
        token = "";
        token += pos;
        if(!Objects.equals(nextpos,' ')){
            token += nextpos;
        }
        setNumflag(1);
        tokens = Tokens.saveToken(token,tktype,lines);
        tktype = tokentype.UNDEFINED;
        token = "";
    }

    private static void setStartFlag(int length){
        if(length > 0){
            startflag = true;
        }else{
            startflag = false;
        }
    }
    private static boolean unitDoubleCheck(char pos,char nextpos,int i){
        if(pos == '|' && nextpos == '|'){
            doubleKey(pos, nextpos, lines);
            i++;
            tktype = tokentype.UNDEFINED;
        } else if(pos == '&' && nextpos == '&'){
            doubleKey(pos, nextpos, lines);
            i++;
            tktype = tokentype.UNDEFINED;
        }else if(pos == '<'){
            if(nextpos == '='){
                doubleKey(pos,nextpos,lines);i++;
            } else{
                doubleKey(pos, ' ',lines);
            }
            tktype = tokentype.UNDEFINED;
        } else if(pos == '>'){
            if(nextpos == '='){
                doubleKey(pos, nextpos, lines);i++;
            } else{
                doubleKey(pos, ' ',lines);
            }
            tktype = tokentype.UNDEFINED;
        } else if(pos == '='){
            if(nextpos == '='){
                doubleKey(pos, nextpos, lines);i++;
            } else{
                doubleKey(pos, ' ',lines);
            }
            tktype = tokentype.UNDEFINED;
        }else if(pos == '!'){
            if(nextpos == '='){
                doubleKey(pos, nextpos, lines);i++;
            } else{
                doubleKey(pos, ' ',lines);
            }
            tktype = tokentype.UNDEFINED;
        } else if (pos == '/') {
            // //单行注释
            if(nextpos == '/') {
                if (token.length() > 0) {
                    if(numflag){
                        tokens = Tokens.saveToken(token, tokentype.INTCON, lines);
                        numflag = !numflag;
                    }
                    else tokens = Tokens.saveToken(token, tktype, lines);
                    token = "";
                    tktype = tokentype.UNDEFINED;
                }
                return true;
            }
            // /* 多行注释
            if(nextpos == '*') {
                if (token.length() > 0) {
                    if(numflag){
                        tokens = Tokens.saveToken(token, tokentype.INTCON, lines);
                        numflag = !numflag;
                    }
                    else tokens = Tokens.saveToken(token, tktype, lines);
                    token = "";
                    tktype = tokentype.UNDEFINED;
                }
                i++;
                noteflag = true;
            }
            // 除号
            else {
                doubleKey(pos, ' ', lines);
            }
        }else if(pos == '*'){
            if(nextpos == '/'){
                doubleKey(pos, nextpos, lines);i++;
            }else{
                doubleKey(pos, ' ', lines);
            }
            tktype = tokentype.UNDEFINED;
        }else{
            token += pos;
            setStartFlag(0);
        }
        return false;
    }
    private static void readLine(String line, int lines){
        token = "";
        tktype = tokentype.UNDEFINED;
        for(int i=0; i<line.length(); i++){
            pos = line.charAt(i);
            /* while the token current is a string, judge and store
               otherwise if the token current is in a note, judge and perdu
               otherwise estinguish the type of the token current and store.
             */
            if(strflag == true){
                token += pos;
                startflag = false;
                if(pos == '"'){
                    strflag = false;
                    //store the token
                    tokens = Tokens.saveToken(token,tktype,lines);
                    //flush the token
                    token = "";
                    tktype = tokentype.UNDEFINED;
                    //another token
                    startflag = true;
                }
                continue;
            }
            //then we judge if the token is in a note mullines
            else if(noteflag == true){
                char nextpos = ' ';
                if(i < line.length() - 1){
                    nextpos = line.charAt(i + 1);
                }else{
                    nextpos = '0';
                }
                if(pos == '*' && nextpos == '/'){
                    noteflag = false;
                    i+=1;
                    token = "";
                    tktype = tokentype.UNDEFINED;
                }
                continue;
            }
            // else if the current str is finished, store and flush
            if(pos == ' ' || pos == '\t'){
                if(token.length() > 0){
                    numflag = false;
                    //store the current token
                    tokens = Tokens.saveToken(token,tktype,lines);
                    //flush the current token
                    token = "";
                    tktype = tokentype.UNDEFINED;
                    //another token
                    startflag = true;
                }
                continue;
            }

            //then the str and note flag are all false, we can make secure that
            //the char pos is a part of current token
            if(pos == '"'){
                if(strflag == false){
                    strflag = true;
                    tktype = tokentype.STRCON;
                    startflag = false;
                }else{
                    strflag = false;
                }
            }
            else if(pos >= '0' && pos <= '9' && startflag == true){
                numflag = true;
                tktype = tokentype.INTCON;
            }

            if(pos != ' ' && pos != '\t'){
                if(isSingle(pos,lines)){
                    setStartFlag(1);
                    tktype = tokentype.UNDEFINED;
                }else{
                    char nextpos = ' ';
                    if(i < line.length() - 1){
                        nextpos = line.charAt(i + 1);
                    }
                    if(pos == '|' && nextpos == '|'){
                        doubleKey(pos, nextpos, lines);
                        i++;
                        tktype = tokentype.UNDEFINED;
                    } else if(pos == '&' && nextpos == '&'){
                        doubleKey(pos, nextpos, lines);
                        i++;
                        tktype = tokentype.UNDEFINED;
                    }else if(pos == '<'){
                        if(nextpos == '='){
                            doubleKey(pos,nextpos,lines);i++;
                        } else{
                            doubleKey(pos, ' ',lines);
                        }
                        tktype = tokentype.UNDEFINED;
                    } else if(pos == '>'){
                        if(nextpos == '='){
                            doubleKey(pos, nextpos, lines);i++;
                        } else{
                            doubleKey(pos, ' ',lines);
                        }
                        tktype = tokentype.UNDEFINED;
                    } else if(pos == '='){
                        if(nextpos == '='){
                            doubleKey(pos, nextpos, lines);i++;
                        } else{
                            doubleKey(pos, ' ',lines);
                        }
                        tktype = tokentype.UNDEFINED;
                    }else if(pos == '!'){
                        if(nextpos == '='){
                            doubleKey(pos, nextpos, lines);i++;
                        } else{
                            doubleKey(pos, ' ',lines);
                        }
                        tktype = tokentype.UNDEFINED;
                    } else if (pos == '/') {
                        // //单行注释
                        if(nextpos == '/') {
                            if (token.length() > 0) {
                                if(numflag){
                                    tokens = Tokens.saveToken(token, tokentype.INTCON, lines);
                                    numflag = !numflag;
                                }
                                else tokens = Tokens.saveToken(token, tktype, lines);
                                token = "";
                                tktype = tokentype.UNDEFINED;
                            }
                            singlenoteflag = unitDoubleCheck(pos, nextpos, i);
                            if(singlenoteflag)break;
                        }
                        // /* 多行注释
                        if(nextpos == '*') {
                            if (token.length() > 0) {
                                if(numflag){
                                    tokens = Tokens.saveToken(token, tokentype.INTCON, lines);
                                    numflag = !numflag;
                                }
                                else tokens = Tokens.saveToken(token, tktype, lines);
                                token = "";
                                tktype = tokentype.UNDEFINED;
                            }
                            i++;
                            noteflag = true;
                        }
                        // 除号
                        else {
                            doubleKey(pos, ' ', lines);
                        }
                    }else if(pos == '*'){
                        if(nextpos == '/'){
                            doubleKey(pos, nextpos, lines);i++;
                        }else{
                            doubleKey(pos, ' ', lines);
                        }
                        tktype = tokentype.UNDEFINED;
                    }else{
                        token += pos;
                        setStartFlag(0);
                    }
                }
            }
        }

    }

}
