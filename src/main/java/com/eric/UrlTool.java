package com.eric;

import java.net.URL;
import java.net.URLConnection;

public class UrlTool {

	public UrlTool() {
	}

	public URLConnection connect(String url) {
		try {
			return new URL(url).openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
