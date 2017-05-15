import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Onu on 5/15/2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
    BBCHealthParser parser=new BBCHealthParser();
    ArrayList<ArticleModel> allArt = parser.parse();
        for(ArticleModel art : allArt ){
            System.out.println(art.toString());
        }

    }
}
