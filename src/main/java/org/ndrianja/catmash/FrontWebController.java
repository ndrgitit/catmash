package org.ndrianja.catmash;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class FrontWebController {

    @Autowired
    CatmashService catService;

    /**
     * Using templates/scores.html
     * 
     * @return score
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @GetMapping("/")
    public ModelAndView fromRootToScoresPage(Model model) throws JsonParseException, JsonMappingException, IOException {
        model.addAttribute("cats", catService.getCats().getOrderedCatScores());
        return new ModelAndView("scores");
    }

    /**
     * Using templates/scores.html
     * 
     * @return scores
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @GetMapping("/scores")
    public ModelAndView scoresPage(Model model) throws JsonParseException, JsonMappingException, IOException {
        model.addAttribute("cats", catService.getCats().getOrderedCatScores());
        return new ModelAndView("scores");
    }

    @GetMapping("/vote")
    public ModelAndView votePage(Model model) throws JsonParseException, JsonMappingException, IOException {
        CatmashRepository catImagesSet = catService.getCats();
        CatImage[] lessCuttestCats = catImagesSet.getTheTwoLessCuttestCat();
        model.addAttribute("cats", lessCuttestCats);
        return new ModelAndView("vote");
    }

    @GetMapping("/vote/{id}")
    public ModelAndView voteSubmitPage(Model model, @PathVariable String id)
            throws JsonParseException, JsonMappingException, IOException {
        CatmashRepository catImagesSet = catService.getCats();
        CatImage catImage = catImagesSet.getCatImage(id);
        catImage.incrementScore();

        // refresh
        catImagesSet = catService.getCats();
        CatImage[] lessCuttestCats = catImagesSet.getTheTwoLessCuttestCat();
        model.addAttribute("cats", lessCuttestCats);

        model.addAttribute("cats", lessCuttestCats);
        return new ModelAndView("vote");
    }

}
