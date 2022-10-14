import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class writer {
    public static String filename = "output.txt";

    public static void writeToken(ArrayList<token> tokens) throws FileNotFoundException {
        PrintStream ps = new PrintStream(filename);
        //可能会出现异常，直接throws就行了
        System.setOut(ps);
        //把创建的打印输出流赋给系统。即系统下次向 ps输出
        for(token token : tokens){
            System.out.println(token.getType() + " " + token.getContent());
        }
        ps.close();
    }
}
