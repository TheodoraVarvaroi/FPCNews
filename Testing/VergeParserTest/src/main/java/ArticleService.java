

import java.util.List;

public interface ArticleService extends CrudService<ArticleModel>{
    public ArticleModel getOneByTags (List<String> tags);
}
