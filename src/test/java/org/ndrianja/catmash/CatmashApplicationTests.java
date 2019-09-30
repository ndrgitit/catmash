package org.ndrianja.catmash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

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

    @Test
    public void contextLoads() throws JsonParseException, JsonMappingException, IOException {
        assertNotNull(catmashService);
        assertNull(catmashService.getCatImageById("Nothing"));
    }

    /**
     * Set random score to cats, and remember the two with lowest score for vote suggestion
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void selectTwoCatsTest() throws JsonParseException, JsonMappingException, IOException {
        CatImage[] lessCuttest = catmashService.selectFiveCatImage();
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
    public void doIHaveTheRightCats() throws JsonParseException, JsonMappingException, IOException {
        CatImage[] lessCuttest = catmashService.selectFiveCatImage();
        assertEquals(2, lessCuttest.length);
        assertEquals(lessCuttest[0].getQuota(), lessCuttest[1].getQuota());

        catmashService.incrementScore(lessCuttest[0].getId());
        catmashService.incrementScore(lessCuttest[1].getId());

        CatImage[] newLessCuttest = catmashService.selectFiveCatImage();

        /*
         * System.out.println(lessCuttest[0] + " ??? " + newLessCuttest[0]); System.out.println(lessCuttest[1] + " ??? "
         * + newLessCuttest[1]);
         * 
         * assertNotEquals(lessCuttest[0], newLessCuttest[0]); assertNotEquals(lessCuttest[1], newLessCuttest[1]);
         */
    }

    @Test
    public void getOrderdCatScoresTest() throws JsonParseException, JsonMappingException, IOException {
        TreeMap<Integer, List<CatImage>> scoresMapFromBusiness = catmashService.getOrderedCatImageScores();
        NavigableSet<Integer> scoresFromBusiness = scoresMapFromBusiness.descendingKeySet();
        assertNotNull(scoresFromBusiness);
    }

    @Test
    public void addVoteTest() throws JsonParseException, JsonMappingException, IOException {
        CatImage cat = catmashService.getCatImages().get(0);
        int score = cat.getScore();
        int expected = score + 1;
        cat.incrementScore();
        assertEquals(expected, cat.getScore());
    }

    @Test
    public void decreaseQuotaTest() throws JsonParseException, JsonMappingException, IOException {
        CatImage cat = catmashService.getCatImages().get(0);
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
