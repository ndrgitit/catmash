package org.ndrianja.catmash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatmashApplicationTests {

    @Autowired
    CatmashService dataSource;
    private CatmashRepository catImagesSet;
    private List<CatImage> cats;
    private TreeMap<Integer, List<CatImage>> scoresMapFromTest;

    @Before
    public void init() throws JsonParseException, JsonMappingException, IOException {
        catImagesSet = dataSource.getCats();
        cats = catImagesSet.getImages();
        scoresMapFromTest = new TreeMap<>();
        for (int i = 0; i < cats.size(); i++) {
            CatImage catImage = cats.get(i);
            int score = (int) (Math.random() * 100);
            catImage.setScore(score);
            if (scoresMapFromTest.get(score) == null) {
                scoresMapFromTest.put(score, new ArrayList<CatImage>());
            }
            scoresMapFromTest.get(score).add(catImage);
        }
    }

    @Test
    public void contextLoads() throws JsonParseException, JsonMappingException, IOException {
        assertNotNull(dataSource);
        CatmashRepository cats = dataSource.getCats();
        assertNotNull(cats);
    }

    @Test
    public void selectTwoCatsTest() {
        // set random score to cats, and remember the two with lowest score for vote suggestion
        int minScore = Integer.MAX_VALUE;
        int lessCuttestCat = Integer.MIN_VALUE, oldLessCuttestCat = Integer.MIN_VALUE;
        for (int i = 0; i < cats.size(); i++) {
            CatImage catImage = cats.get(i);
            int score = catImage.getScore();
            if (score < minScore) {
                minScore = score;
                int tmp = lessCuttestCat;
                lessCuttestCat = i;
                oldLessCuttestCat = tmp;
            }
        }

        CatImage[] lessCuttest = catImagesSet.getTheTwoLessCuttestCat();
        assertTrue(cats.get(lessCuttestCat).getId().equals(lessCuttest[0].getId()));
        assertTrue(cats.get(oldLessCuttestCat).getId().equals(lessCuttest[1].getId()));
    }

    @Test
    public void getOrderdCatScoresTest() {
        TreeMap<Integer, List<CatImage>> scoresMapFromBusiness = catImagesSet.getOrderedCatScores();
        NavigableSet<Integer> scoresFromBusiness = scoresMapFromBusiness.descendingKeySet();
        NavigableSet<Integer> scoresFromTest = scoresMapFromTest.descendingKeySet();
        assertEquals(scoresFromTest, scoresFromBusiness);
        for (Integer score : scoresFromTest) {
            assertEquals(scoresMapFromBusiness.get(score), scoresMapFromTest.get(score));
        }
    }

    @Test
    public void addVoteTest() {
        CatImage cat = cats.get(0);
        int score = cat.getScore();
        int expected = score + 1;
        cat.incrementScore();
        assertEquals(expected, expected);
    }

}
