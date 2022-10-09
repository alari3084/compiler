package parserUnit;

import token.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ParserUnit {
    public static List<ParserUnit> units = new LinkedList<>();
    public static String filename = "output.txt";
    public String name;

    // type表示是token或者是词法单元
    public String type;

    public List<Token> token;

    public Token singleToken;

    // 词法单元
    public ParserUnit(String name, List<Token> token) {
        this.type = "CompUnit";
        this.name = name;
        this.token = token;
    }

    // 单词
    public ParserUnit(Token token){
        this.singleToken = token;
        this.type = "Token";
    }

    public static void saveUnit(ParserUnit parserUnit){
        units.add(parserUnit);
        if(Objects.equals(parserUnit.type, "CompUnit")){
            System.out.println("<" + parserUnit.name + ">");
        } else if(Objects.equals(parserUnit.type, "Token")){
            System.out.println(parserUnit.singleToken.getTokenType() + " " + parserUnit.singleToken.getContent());
        }
    }

    public static void writeUnit() throws FileNotFoundException {
        PrintStream ps = new PrintStream(filename);
        //可能会出现异常，直接throws就行了
        System.setOut(ps);
        //把创建的打印输出流赋给系统。即系统下次向 ps输出
        for(ParserUnit unit : units){
            if(Objects.equals(unit.type, "CompUnit")){
                System.out.println("<" + unit.name + ">");
            } else if(Objects.equals(unit.type, "Token")){
                System.out.println(unit.singleToken.getTokenType() + " " + unit.singleToken.getContent());
            }

        }
        ps.close();
    }
}
