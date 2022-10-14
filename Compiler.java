import java.io.IOException;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args)throws IOException{

        ArrayList<token> tokens = reader.readFile();
        ParseAnalysis.entrance(tokens);
        pUnit.writeUnit();

    }
}
