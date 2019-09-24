package org.ndrianja.catmash;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatmashRepository {

    List<CatImage> images;

    public List<CatImage> getImages() {
        return images;
    }

    public void setImages(List<CatImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        StringBuilder resp = new StringBuilder();
        for (CatImage cat : images) {
            resp.append(cat.toString() + "\n");
        }
        return resp.toString();
    }

    /**
     * TODO To be improved: select two cats, not especially the less cuttest
     * 
     * @return
     */
    public CatImage[] getTheTwoLessCuttestCat() {
        int min = Integer.MAX_VALUE;
        CatImage[] lessCuttest = new CatImage[2];
        for (CatImage catImage : images) {
            int score = catImage.getScore();
            if (score < min) {
                min = score;
                CatImage tmp = lessCuttest[0];
                lessCuttest[0] = catImage;
                lessCuttest[1] = tmp;
            }
        }

        if (lessCuttest[1] == null) {
            lessCuttest[1] = images.get(images.size() - 1);
        }
        return lessCuttest;
    }

    public TreeMap<Integer, List<CatImage>> getOrderedCatScores() {
        TreeMap<Integer, List<CatImage>> orderedCatScores = new TreeMap<>((a, b) -> {
            if (a < b) {
                return 1;
            } else if (a > b) {
                return -1;
            } else {
                return 0;
            }
        });
        for (CatImage catImage : images) {
            if (orderedCatScores.get(catImage.getScore()) == null) {
                orderedCatScores.put(catImage.getScore(), new ArrayList<CatImage>());
            }
            orderedCatScores.get(catImage.getScore()).add(catImage);
        }
        return orderedCatScores;
    }

    public CatImage getCatImage(String id) {
        for (CatImage cat : images) {
            if (cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }
}
