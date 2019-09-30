package org.ndrianja.catmash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    CatmashService catmashService;
    private List<CatImage> cats;
    private TreeMap<Integer, List<CatImage>> scoresMapFromTest;

    @Before
    public void init() {
        cats = catmashService.getImages();
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
    public void contextLoads() {
        assertNotNull(catmashService);
        CatmashRepository cats = catmashService.getCats();
        assertNull(cats.getCatImage("Nothing"));
        assertNotNull(cats.toString());
        assertNotNull(cats);
    }

    /**
     * Set random score to cats, and remember the two with lowest score for vote suggestion
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void selectTwoCatsTest() {
        CatImage[] lessCuttest = catmashService.selectTwoCats();
        assertTrue(lessCuttest[0] instanceof CatImage);
        assertTrue(lessCuttest[1] instanceof CatImage);
    }

    /**
     * To be tested: 1) Select two cats having the greatest quota 2) From those two cats, select the two having the
     * lowest score
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void doIHaveTheRightCats() {
        CatImage[] lessCuttest = catmashService.selectTwoCats();
        assertEquals(2, lessCuttest.length);
        assertEquals(lessCuttest[0].getQuota(), lessCuttest[1].getQuota());

        lessCuttest[0].incrementScore();
        lessCuttest[1].incrementScore();

        CatImage[] newLessCuttest = catmashService.selectTwoCats();
        assertNotEquals(lessCuttest[0], newLessCuttest[0]);
        assertNotEquals(lessCuttest[1], newLessCuttest[1]);
    }

    @Test
    public void getOrderdCatScoresTest() throws JsonParseException, JsonMappingException, IOException {
        TreeMap<Integer, List<CatImage>> scoresMapFromBusiness = catmashService.getOrderedCatScores();
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
        assertEquals(expected, cat.getScore());
    }

    @Test
    public void decreaseQuotaTest() {
        CatImage cat = cats.get(0);
        int quota = cat.getQuota();
        int expected = quota - 1;
        cat.decreaseQuota();
        assertEquals(expected, cat.getQuota());

        for (int i = 0; i < 5; i++) {
            cat.decreaseQuota();
        }
        assertEquals(0, cat.getQuota());
    }

}
