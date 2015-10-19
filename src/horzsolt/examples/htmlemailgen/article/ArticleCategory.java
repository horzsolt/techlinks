package horzsolt.examples.htmlemailgen.article;

public enum ArticleCategory {
	
	//TODO: Use CSS in the template instead.
	DEV_DEVOPS("dev/devops", "width:11.1pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt; height:17.35pt"),
	HIGHLIGHTED("highlighted", "width:11.1pt;background:#C00000;padding:0in 5.4pt 0in 5.4pt; height:17.35pt"),
	MANAGEMENT("mgmt/business", "width:11.1pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt; height:17.35pt"),
	QA("qa", "width:11.1pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt; height:17.35pt"),
	BOOK("book", "width:11.1pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt; height:17.35pt"),
	VIDEO("prezi/video", "width:11.1pt;background:#C00000;padding:0in 5.4pt 0in 5.4pt; height:17.35pt");

	private String categoryText;
	private String style;	

	ArticleCategory(String categoryText, String style) {
		this.categoryText = categoryText;
		this.style = style;
	}

	public String getCategoryText() {
		return this.categoryText;
	}

	public String getStyle() {
		return style;
	}

}
