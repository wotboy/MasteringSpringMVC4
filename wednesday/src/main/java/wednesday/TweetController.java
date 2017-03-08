package wednesday;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TweetController {
	
	@Autowired
	private Twitter twitter;
	
	@RequestMapping("/")
	public String home() {
		return "searchPage";
	}
	
	@RequestMapping("/result")
	public String hello( @RequestParam(required=true) String search, Model model) {
		SearchResults sr = twitter.searchOperations().search(search);
		List<Tweet> tweets = sr.getTweets();
		model.addAttribute("tweets", tweets);
		model.addAttribute("search", search);
		return "resultPage";
	}
	
	@RequestMapping(value="/postSearch", method=RequestMethod.POST)
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		if(search==null || search=="") {
			redirectAttributes.addFlashAttribute("error", "You must enter a search term.");
			return "redirect:/";
		}
		redirectAttributes.addAttribute("search", search);
		return "redirect:result";
	}
	
}
