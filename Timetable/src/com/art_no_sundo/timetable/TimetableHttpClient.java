package com.art_no_sundo.timetable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ccil.cowan.tagsoup.Parser;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public final class TimetableHttpClient {
	HttpClient client;
	TransformerFactory transFac;

	public TimetableHttpClient() {
		super();
		client = new DefaultHttpClient();
		transFac = TransformerFactory.newInstance();
	}

	public Node getNodeFromUrl(URL url, String encoding) throws IOException {
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url.toURI());
		} catch (URISyntaxException e) {
			// TODO error check
			throw new RuntimeException(e);
		}
		HttpResponse response = null;
		try {
			response = client.execute(httpGet);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw e;
		}
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException("connection failed");
		}
		DOMResult result = null;
		InputStream stream = null;
		try {
			try {
				stream = response.getEntity().getContent();
			} catch (IllegalStateException e) {
				throw new RuntimeException(e);
			}
			InputStreamReader streamReader = null;
			try {
				streamReader = new InputStreamReader(stream, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}

			XMLReader reader = new Parser();
			try {
				reader.setFeature(Parser.namespacesFeature, false);
				reader.setFeature(Parser.namespacePrefixesFeature, false);
			} catch (SAXNotRecognizedException e) {
				throw new RuntimeException(e);
			} catch (SAXNotSupportedException e) {
				System.err.println(e.getMessage());
			}
			Transformer transformer = null;
			try {
				transformer = transFac.newTransformer();
			} catch (TransformerConfigurationException e) {
				throw new RuntimeException(e);
			}

			result = new DOMResult();
			try {
				transformer.transform(new SAXSource(reader, new InputSource(
						streamReader)), result);
			} catch (TransformerException e) {
				throw new RuntimeException(e);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			stream.close();
		}
		return result.getNode();
	}
}
