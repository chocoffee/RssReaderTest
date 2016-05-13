package jp.ac.jec.cm0138.rssreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RowModelAdapter adapter;

    class RowModelAdapter extends ArrayAdapter<RssItem> {
        RowModelAdapter(Context con) {
            super(con, R.layout.row_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RssItem item = getItem(position);
            if(convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_item, null);
            }
            if(item != null) {
                TextView txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
                if(txtTitle != null) {
                    String title = item.getTitle();
                    String sTitle = title.substring(0, (title.length() - 12));
                    txtTitle.setText(sTitle);
                }
                TextView txtDate = (TextView)convertView.findViewById(R.id.txtDate);
                if(txtDate != null) {
                    String date = item.getDate();
                    txtDate.setText(dateSet(date));
                }
            }
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RowModelAdapter(this);
        Uri.Builder uriBuilder = new Uri.Builder();

        uriBuilder.scheme("http");
        uriBuilder.authority("ajax.googleapis.com");
        uriBuilder.path("/ajax/services/feed/load");
        uriBuilder.appendQueryParameter("v", "1.0");
        uriBuilder.appendQueryParameter("q", "http://headlines.yahoo.co.jp/rss/giz-c_sci.xml");
        uriBuilder.appendQueryParameter("num", "30");

        AsyncHTTPRequest req = new AsyncHTTPRequest(MainActivity.this);
        req.execute(uriBuilder);
//        ArrayList<RssItem> ary = JsonHelper.parseJson(getData());
//        for(RssItem tmp: ary) {
//            adapter.add(tmp);
//        }
        ListView list = (ListView)findViewById(R.id.list);
        //list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(position).getLink()));
                startActivity(intent);
            }
        });
    }

    String dateSet(String date) {
        String[] dateArray = date.split(" ");
        String month = "";
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for(int i = 0; i < months.length; i++) {
            if (dateArray[2].equals(months[i])) {
                month = String.valueOf(i + 1);
            }
        }
        String[] weeks = {"Mon,", "Tue,", "Wed,", "Thu,", "Fri,", "Sat,", "Sun,"};
        String[] jaWeek = {"月", "火", "水", "木", "金", "土", "日"};
        String week = "";
        for(int i = 0; i < weeks.length; i++) {
            if (dateArray[0].equals(weeks[i])) {
                week = jaWeek[i];
            }
        }

        return dateArray[3] + "年" + month + "月" + dateArray[1] + "日" + "(" + week + ")  " + dateArray[4];
    }

//    private String getData() {
//        String json = "";
//        BufferedReader br = null;
//        try {
//            InputStream in = getAssets().open("rss.json");
//            br = new BufferedReader(new InputStreamReader(in));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            json = sb.toString();
//        }catch (Exception e) {
//            Log.e("Main", Log.getStackTraceString(e));
//        }finally {
//            try {
//                if(br != null) br.close();
//            }catch (Exception e) {
//                Log.e("Main", Log.getStackTraceString(e));
//            }
//        }
//        return json;
//    }
}
