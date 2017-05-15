
public class Article{

    private String title;
    private String link;
    private String date;
    private String content;
    private String mediaLink;

    Article(String title,String link,String date,String content,String mediaLink){
        this.title=title;
        this.link=link;
        this.date=date;
        this.content=content;
        this.mediaLink=mediaLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    @Override
    public String toString() {
        return "Title: "+title+" link: "+link+" date: "+date+" content: "+content+" Medialink: "+mediaLink +"\n";
    }
}
