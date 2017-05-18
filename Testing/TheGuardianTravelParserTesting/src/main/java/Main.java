import java.util.*;

/**
 * Created by Jitca Beniamin on 16/05/2017.
 */
public class Main {

    public static void main(String args[]) {
        TheGuardianTravelParser theGuardian = new TheGuardianTravelParser();
        ArrayList<ArticleModel> allArt = (ArrayList<ArticleModel>) (ArrayList<ArticleModel>) theGuardian.parseAllArticles();
        for(ArticleModel art : allArt)
            System.out.println(art.toString());
    }

}
