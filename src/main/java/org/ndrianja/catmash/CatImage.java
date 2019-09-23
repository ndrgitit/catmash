package org.ndrianja.catmash;

public class CatImage {
    private String url;
    private String id;
    private int score;

    public CatImage() {
        score = 0;
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
        return "id: " + id + ", url: " + url;
    }

    public synchronized void incrementScore() {
        score++;
    }
}
