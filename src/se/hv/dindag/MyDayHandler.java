package se.hv.dindag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

/**
 * This class manages the loading of external data. It uses the ASynkTask class
 * for this, wich in this case takes 3 arguments. Theese are (in order): String,
 * which is the input (URL of the feed), Void, which is the datatype of the
 * progress report (but I dont use any progress report, and finally an
 * ArrayList, which is what is being returned from the class.
 * 
 * This is a good place to mention why the app is using this ASynkTask: since
 * Android version 3, the only Thread allowed to draw GUI-stuff is the
 * main-thread and secondly, the main thread cannot use the network. Since
 * getting an XML-feed is very much networking this has to be done someplace
 * else than in the main thread. ASynkTask accomplishes this by setting aside a
 * separate thread doing networking stuff.
 * 
 * This class needs to have a method called doInBackground, which is responsible
 * for doing the legwork of the class.
 * 
 * @author imcoh
 * 
 */
public class MyDayHandler extends AsyncTask<String, Void, ArrayList> {

	/**
	 * Define the keys in the XML-feed we're interested in
	 */
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_LINK = "link";
	static final String KEY_TITLE = "title";
	static final String KEY_DESC = "description";
	static final String KEY_DATE = "pubDate";
	static final String KEY_TAG = "tag";

	/**
	 * Since the desciption-element in the XML-feed look pretty shitty since
	 * teachers are allowed to use HTML-formatting in systems like Kronox, this
	 * method uses the fromHtml-method to strip tags and convert HTML-encoded
	 * characters to UTF-8, eg. #244 => 
	 * 
	 * @param description
	 * @return A nice and clean string without HTML
	 */
	private String deUglify(String description) {
		Spanned temp;
		temp = Html.fromHtml(description);

		return temp.toString();
	}

	/**
	 * Preserves the important part of the publishing time of an element in the
	 * feed. Counting the days since the notice has been published.
	 * 
	 * @param theDate
	 * @return Polished date
	 */
	private String prettyfyDate(String theDate) {
		String temp = theDate.substring(0, 10);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());

		// TODO: Fixa till datumet i denna
		// Förra versionen nvänder dc:date som ser ut som:
		// 2013-02-08T10:49:00+01:00
		//
		// medan vi nu använder pubDate, som ser ut som:
		// Thu, 24 Jan 2013 18:04:25 +0100

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z", Locale.getDefault());
		Date date = null;
		try {
			date = sdf.parse(theDate);
			theDate = new SimpleDateFormat("dd MMM @ HH:mm").format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return theDate;
	}

	/**
	 * Does all the magic in getting an XML-file from the network, parses it, an
	 * filling an ArrayList containing an HashMap of KEY-VALUE-pairs
	 * 
	 * @param URL
	 *            of the feed
	 * @return The ArrayList called menuItems containing the feed
	 */
	protected ArrayList<HashMap<String, String>> doInBackground(
			String... theURL) {
		String theFeed = theURL[0];

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(theFeed); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element
		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_TAG, parser.getValue(e, KEY_TAG));
			map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
			map.put(KEY_DESC, deUglify(parser.getValue(e, KEY_DESC)));
			map.put(KEY_DATE, prettyfyDate(parser.getValue(e, KEY_DATE)));

			// adding HashList to ArrayList
			menuItems.add(map);
		}
		return menuItems;
	}

	/**
	 * Dummy-method just called after the ASynkTask is done
	 * 
	 * @param result
	 */
	protected void onPostExecute(Integer result) {
		Log.i("MyDayHandler", "AsyncTask done. Returned : " + result);
	}
}