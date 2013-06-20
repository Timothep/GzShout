package com.twin.gzbox;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ShoutStore {

	private SimpleDateFormat sdf;
	
	private List<Shout> shoutList = new ArrayList<Shout>();
	private AvatarCache avatarCache;	
	
	public ShoutStore(AvatarCache avatarCache) {
		this.avatarCache = avatarCache;		
		sdf = new SimpleDateFormat("(dd / MM - HH:mm)");
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
	}
	
	
	public void updateMessages() throws IOException, ParseException {
		List<Shout> newList = new ArrayList<Shout>();

		Document doc = Jsoup.connect("http://www.geekzone.fr/ipb/shoutbox/").get();
				
		Element shoutTable = doc.select("table[id=shoutbox-shouts-table]").first();
		
		for (Element row : shoutTable.select("tr")) {
			// author			
			String author = null;
			Elements tds = row.select("td");
			if(tds != null && tds.size() > 1) {
				String authString = tds.get(1).text();
				if(authString.length() > 3) {
					author = authString.substring(3);
				}
			}			
			
			if(author == null) {
				continue;
			}
			
			// 	avatar url			
			Element eltAvatarUrl = row.select(".ipsUserPhoto").first();			
			String avatarUrl = eltAvatarUrl.attr("src");
			avatarCache.put(author, avatarUrl);						
			
			// date			
			Element eltDate = row.select(".desc").first();
			String strDate = eltDate.text();
			Date date = null;
			synchronized (sdf) {				
				 date = sdf.parse(strDate);
			}
			
			// message			
			Element eltMsgText = row.select(".shoutbox_text").first();
			String msgText = eltMsgText.text();
	
			
			
			newList.add(new Shout(author, date, msgText));
		}
		
		synchronized (shoutList) {
			shoutList = newList;			
		}
	}


	public List<Shout> getShoutList() {
		List<Shout> list;
		synchronized (shoutList) {
			list = shoutList;			
		}
		return list;
	}
}
