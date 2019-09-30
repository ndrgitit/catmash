package org.ndrianja.catmash;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public CatImage[] selectCats(int count) {
        return images.stream()
                .sorted(Comparator.comparingInt(CatImage::getQuota)
                        .thenComparing(Comparator.comparingInt(CatImage::getScore)))
                .limit(count).collect(Collectors.toList()).toArray(CatImage[]::new);
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
