package horzsolt.examples.htmlemailgen.article;

public class Article {

	private String title;
	private ArticleCategory category;
	private String description;
	private String url;
	private String author;
	private String style;
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		
		if (title.contains("[")) {
			author = title.substring(title.lastIndexOf("[") + 1, title.lastIndexOf("]"));
		} else {
			author = "anonymous";
		}
	}

	public ArticleCategory getCategory() {
		return category;
	}

	public void setCategory(ArticleCategory category) {
		this.category = category;
		this.style = category.getStyle();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getStyle() {
		return style;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCategory() + " " + getTitle() + " by " + author + " " + style;
	}


}
