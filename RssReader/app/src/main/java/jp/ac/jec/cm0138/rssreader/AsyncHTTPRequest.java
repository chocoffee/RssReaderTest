package jp.ac.jec.cm0138.rssreader;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guest on 16/05/12.
 */
public class AsyncHTTPRequest extends AsyncTask<Uri.Builder, Void, ArrayList<RssItem>> {
    private MainActivity mainActivity;

    public AsyncHTTPRequest(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    protected ArrayList<RssItem> doInBackground(Uri.Builder... params) {
        String resStr = "取得に失敗しました";
        try {
            URL url = new URL(params[0].toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            resStr = InputStreamToString(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();

            System.out.print(e);
        }
        ArrayList<RssItem> ary = JsonHelper.parseJson(resStr);
        return ary;
    }

    private String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(ArrayList<RssItem> result) {
        for(RssItem tmp: result) {
            mainActivity.adapter.add(tmp);
        }
        ListView list = (ListView)mainActivity.findViewById(R.id.list);
        list.setAdapter(mainActivity.adapter);
    }
}