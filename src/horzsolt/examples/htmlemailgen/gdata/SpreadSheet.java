package horzsolt.examples.htmlemailgen.gdata;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import horzsolt.examples.htmlemailgen.article.Article;
import horzsolt.examples.htmlemailgen.article.ArticleCategory;
import horzsolt.examples.htmlemailgen.common.Logger;


public class SpreadSheet {

	private static String TECH_LINKS_WORKSSHEET_TITLE = "TECH LINKS";
	private static String TECH_LINKS_SHEET_TITLE = "LINKS";	
	
	@SuppressWarnings("unused")
	public static List<Article> list(Credential oauthCredential) throws IOException, ServiceException {

		SpreadsheetService service = new SpreadsheetService("techlinks_emailgen");
		URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		service.setOAuth2Credentials(oauthCredential);
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			throw new IllegalStateException("No sheets available.");
		}

		SpreadsheetEntry spreadsheet = null;
		
		for (int i = 0; i < spreadsheets.size(); ++i) {
			SpreadsheetEntry _spreadsheet = spreadsheets.get(i);
			
			if (_spreadsheet.getTitle().getPlainText().toUpperCase().equals(TECH_LINKS_WORKSSHEET_TITLE)) {
				spreadsheet = _spreadsheet;
				break;
			}
		}
		
		if (spreadsheet == null) {
			throw new IllegalStateException("No Tech links worksheet found.");
		}				
		
		WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		
		WorksheetEntry worksheet = null;
		
		for (WorksheetEntry _worksheet : worksheets) {
			
			Logger.debug(_worksheet.getTitle().getPlainText());
			if (_worksheet.getTitle().getPlainText().toUpperCase().equals(TECH_LINKS_SHEET_TITLE)) {
				worksheet = _worksheet;
				break;
			}
		}
		
		if (worksheet == null) {
			throw new IllegalStateException("No Links sheet found.");
		}		
		
		URL cellFeedUrl = worksheet.getCellFeedUrl();
		URL listFeedUrl = worksheet.getListFeedUrl();
		
		ListFeed rowFeed = service.getFeed(listFeedUrl, ListFeed.class);
	    CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);
	    
	    List<ListEntry> rows = rowFeed.getEntries();
	    List<Article> articles = new ArrayList<>();
	    
	    for (ListEntry row : rows) {
	    	
	    	Article article = new Article();
	    		    	
	    	Object[] tags = (Object[]) row.getCustomElements().getTags().toArray();
	    	article.setTitle(row.getCustomElements().getValue(tags[0].toString()));
	    	
	    	String categoryInDoc = row.getCustomElements().getValue(tags[1].toString());
			for (ArticleCategory category : ArticleCategory.values()) {
				if (categoryInDoc.equalsIgnoreCase(category.getCategoryText())) {
					article.setCategory(category);
				}
			}
			
			article.setDescription(row.getCustomElements().getValue(tags[2].toString()));
			article.setUrl(row.getCustomElements().getValue(tags[3].toString()));
			
			articles.add(article);
	    }
	    
	    return articles;
	}
}
