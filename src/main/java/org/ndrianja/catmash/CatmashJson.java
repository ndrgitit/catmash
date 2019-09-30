package org.ndrianja.catmash;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatmashJson {
    private List<CatImage> images;

    public List<CatImage> getImages() {
        return images;
    }

    public void setImages(List<CatImage> images) {
        this.images = images;
    }
}
