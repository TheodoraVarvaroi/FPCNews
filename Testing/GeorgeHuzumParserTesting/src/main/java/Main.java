/**
 * Created by Onu on 5/15/2017.
 */
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {


        Parser parser = new Parser();

        List<Article> myArts = parser.parseElements();
            for(Article art : myArts)
                System.out.println(art.toString());

    }
}



