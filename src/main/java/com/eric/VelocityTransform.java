package com.eric;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class VelocityTransform extends Command {

	@Parameter(description = "template-path")
	private List<String> templates = new ArrayList<>();

	@Parameter(names = { "-p", "--params" }, description = "Parameters to be passed in to the transformation process.  This needs to be in valid JSON format.")
	private String params;

	@Parameter(names = { "-e", "--encoding" }, description = "The template encoding.")
	private String encoding = "UTF-8";

	@Parameter(names = { "--in-name" }, description = "The name of the model value for sysin.  This is the template variable name you would use to reference any sysin values.")
	private String sysInName = "in";

	@Parameter(names = { "--params-name" }, description = "The name of the model value for any values supplied by the 'params' parameter.  This is the template variable name you would use to reference these values.")
	private String paramsName = "params";

	@Override
	protected String getProgramName() {
		return "velocity";
	}

	@Override
	protected void validate(Collection<String> messages) {
		if (templates.size() != 1) {
			messages.add("Exactly one template file name is required.");
		}
	}

	@Override
	protected void run() throws Exception {
		Properties properties = new Properties();
		properties.put("file.resource.loader.path", "/");
		Velocity.init(properties);

		VelocityContext context = new VelocityContext();
		context.put("date", new DateTool());
		context.put("collection", new CollectionTool());
		context.put("escape", new StringEscapeUtils());
		context.put("url", new UrlTool());

		Gson gson = new Gson();
		context.put(sysInName, gson.fromJson(systemIn(), Object.class));

		if (params != null) {
			context.put(paramsName, gson.fromJson(params, Object.class));
		}

		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate(templates.get(0), encoding, context, sw);
		out(sw.toString());
	}

	private String systemIn() {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();

		while (sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}

		sc.close();
		return sb.toString();
	}

	public static void main(String[] args) {
		Command.main(new VelocityTransform(), args);
	}
}
