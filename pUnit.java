import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class pUnit {
/*
* This part aims to make the parse.
* There are two parts in the parse:
* the tokens(output as tktype token)
* and the parseunits(output as <CUnit>)
*
* so in this class we make both,including
* defines and save methods,we use arraylist to store Punits.
* */
    public static List<pUnit> units = new ArrayList<>();
    public static String filename = "output.txt";
    // attribute of CompUnit
    public String name;
    public boolean isCompUnit = false;
    public List<token> tokenInclude;
    //attribute of tokens
    public token tokenEnum;
    public boolean istoken = false;

    // method of set the type
    public void setCUnit(){
        isCompUnit = true;
        istoken = false;
    }

    public void setToken(){
        isCompUnit = false;
        istoken = true;
    }
    // make methods
        //make Cunit
    public pUnit(String name, List<token> tokenInclude){
        this.name = name;
        this.tokenInclude = tokenInclude;
        setCUnit();
    }
        //make tokens
    public pUnit(token tokenEnum){
        this.tokenEnum = tokenEnum;
        setToken();
    }
    public static void addUnit(pUnit punit){
        units.add(punit);
    }

    public static void writeUnit()throws FileNotFoundException{
        PrintStream a = new PrintStream(filename);
        System.setOut(a);
        for(pUnit i : units){
            if(i.isCompUnit){
                System.out.println("<"+i.name+">");
            }else if(i.istoken){
                System.out.println(i.tokenEnum.getType()+" "+i.tokenEnum.getContent());
            }
        }
        a.close();
    }
}
