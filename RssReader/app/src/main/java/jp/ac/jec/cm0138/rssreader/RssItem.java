package jp.ac.jec.cm0138.rssreader;

/**
 * Created by guest on 16/05/12.
 */
public class RssItem {
    private String title;
    private String link;
    private String date;

    public RssItem() {
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
