package xlash.bot.khux;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.btobastian.javacord.DiscordAPI;

public class TwitterHandler {
	
	public String lastTwitterUpdate;
	
	public TwitterHandler(){
		lastTwitterUpdate = getTwitterUpdateLink(0);
	}
	
	public void getTwitterUpdate(DiscordAPI api){
		ArrayList<String> links = new ArrayList<String>();
		String current = getTwitterUpdateLink(0);
		int i=0;
		while(!lastTwitterUpdate.equals(current)){
			if(current!=lastTwitterUpdate) links.add(0, current);
			i++;
			current = getTwitterUpdateLink(i);
		}
		for(String link : links){
			if(link!=null) api.getChannelById(KHUxBot.config.updateChannel).sendMessage(link);
		}
		if(links.size()>0) lastTwitterUpdate = links.get(links.size()-1);
	}
	
	public String getTwitterUpdateLink(int recent){
		try {
			Document doc = Jsoup.connect("https://twitter.com/kh_ux_na").get();
			String shortUrl = doc.getElementsByClass("js-tweet-text-container").get(recent).getElementsByTag("a").get(1).attr("href");
			Document doc2 = Jsoup.connect(shortUrl).get();
			return doc2.getElementsByTag("title").text();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get update from Twitter, but that won't stop me!");
		}
		return null;
	}

}
