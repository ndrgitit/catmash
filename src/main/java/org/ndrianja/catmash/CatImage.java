package org.ndrianja.catmash;

public class CatImage {
    private String url;
    private String id;
    private int score;
    private int quota;

    public CatImage() {
        score = 0;
        quota = 5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + quota + "," + score + ")";
    }

    public synchronized void incrementScore() {
        setScore(score + 1);
    }

    public int getQuota() {
        return quota;
    }

    public void decreaseQuota() {
        quota = quota > 0 ? quota - 1 : quota;
    }
}
