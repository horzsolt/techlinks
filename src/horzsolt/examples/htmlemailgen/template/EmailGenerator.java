package horzsolt.examples.htmlemailgen.template;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import horzsolt.examples.htmlemailgen.article.Article;
import horzsolt.examples.htmlemailgen.common.TransformObjectToMap;

public class EmailGenerator {

	private static final String OUTPUT_FILE_PATH = "d:\\techlinks.html";
	private static final String EMAIL_TITLE = "Epam tech links Vol. 03, No. 14";
	private static final String WELCOME_MESSAGE = "After the Summer Holiday Season the newsletter is back!!!";

	public static void generate(List<Article> articles) throws Exception {

		List<Map> list = new ArrayList<Map>();
		StringBuilder contributors = new StringBuilder();

		for (Article article : articles) {
			list.add(TransformObjectToMap.transform(article));
			contributors.append(article.getAuthor() + " ");
		}

		VelocityEngine ve = new VelocityEngine();
		ve.init();

		VelocityContext context = new VelocityContext();
		context.put("articles", list);
		
		context.put("title", EMAIL_TITLE);
		context.put("welcome_message", WELCOME_MESSAGE);
		context.put("contributors", contributors);
		context.put("publish_date", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
		
		Template t = ve.getTemplate("email_template.vm");
		
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		String filecontent = writer.toString();

		File htmlfile = new File(OUTPUT_FILE_PATH);
		
		if (!htmlfile.exists()) {
			htmlfile.createNewFile();
		}
		
		FileWriter fw = new FileWriter(htmlfile);
		fw.write(filecontent);
		fw.close();

	}

}
