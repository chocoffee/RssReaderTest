package jp.ac.jec.cm0138.rssreader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by guest on 16/05/12.
 */
public class JsonHelper {
    public static ArrayList<RssItem> parseJson(String strJson) {
        ArrayList<RssItem> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(strJson);
            JSONObject responceData = json.getJSONObject("responseData");
            JSONObject feed = responceData.getJSONObject("feed");
            JSONArray entries = feed.getJSONArray("entries");
            for(int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                list.add(parseToItem(entry));
            }
        }catch (Exception e) {

            e.printStackTrace();
            Log.e("JsonHelper", e.getStackTrace().toString());
        }
        return list;
    }

    public static RssItem parseToItem(JSONObject json) throws JSONException {
        RssItem tmp = new RssItem();
        tmp.setTitle(json.getString("title"));
        tmp.setLink(json.getString("link"));
        tmp.setDate(json.getString("publishedDate"));

        Log.i("Title", "hoge=" + json.getString("title"));
        return tmp;
    }
}
