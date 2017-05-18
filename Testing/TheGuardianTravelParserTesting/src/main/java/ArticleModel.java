import java.util.ArrayList;
import java.util.List;


public class ArticleModel {
    private String id;

    private String title;

    private String content;

    private String imageUrl;

    private List<String> tags;

    private SourceModel source;

    private String videoUrl;

    public ArticleModel() {
        this.title = " ";
        this.content = " ";
        this.imageUrl = " ";
        this.tags = new ArrayList<String>();
        this.source = new SourceModel();
        this.videoUrl = " ";
    }

    public ArticleModel(String title, String content, String imageUrl, List<String> tags, SourceModel source, String videoUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.source = source;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public SourceModel getSource() {
        return source;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String toString(){
        String ret =  "Title: " + title + "\nContent: " + content + "\nImageUrl: " + imageUrl + "\nTags: ";
        for(int i = 0; i < tags.size(); i ++) {
            ret += tags.get(i)+ " ";
        }
        ret += "\nSource: " + source + "\nVideoUrl: " + videoUrl;
        return ret;
    }

}
