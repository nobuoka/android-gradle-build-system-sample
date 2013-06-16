package info.vividcode.android.app.gradlebuildsystemsample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    /** この Activity の running 中 (onStart から onStop まで) ずっと動いている RequestQueue */
    private RequestQueue mRequestQueue;

    private HaikuStatusListAdapter mHaikuStatusListAdapter;

    /** はてなハイクの public_timeline に対するリクエスト成功時のコールバック */
    private final Response.Listener<JSONArray> mTimelineRequestCallback =
    new Response.Listener<JSONArray>() {
        @Override public void onResponse(JSONArray response) {
            try {
                List<HaikuStatus> statuses = createHaikuStatusListFromJson(response);
                mHaikuStatusListAdapter.addAllOnHead(statuses);
                Toast.makeText(MainActivity.this, "success" + statuses.size(), Toast.LENGTH_SHORT).show();
            } catch (JSONException err) {
                showErrorOfTimelineRequest(err);
            }
        }
    };

    /** はてなハイクのサーバーからのレスポンスを処理して HaikuStatus の List に変換する */
    private List<HaikuStatus> createHaikuStatusListFromJson(JSONArray statusesJson) throws JSONException {
        int length = statusesJson.length();
        List<HaikuStatus> statuses = new ArrayList<HaikuStatus>(length);
        for (int i = 0; i < length; ++i) {
            JSONObject statusObj = statusesJson.getJSONObject(i);
            String userName = statusObj.getJSONObject("user").getString("name");
            String text = statusObj.getString("text");
            statuses.add(new HaikuStatus(userName, text));
        }
        return statuses;
    }

    /** はてなハイクの public_timeline に対するリクエスト失敗時のコールバック */
    private final Response.ErrorListener mTimelineRequestErrorback =
    new Response.ErrorListener() {
        @Override public void onErrorResponse(VolleyError error) {
            showErrorOfTimelineRequest(error);
        }
    };

    private void showErrorOfTimelineRequest(Exception err) {
        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
        Log.d("GradleBuildSystemSample", "TimelineRequestErrorback", err);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Activity 作成時に RequestQueue オブジェクトを生成
        // (`newRequestQueue` メソッドで作られた RequestQueue のバックグラウンドスレッドは開始されている)
        mRequestQueue = Volley.newRequestQueue(this);

        // ボタンクリックのリスナを追加 (ボタンクリック時にはてなハイクの public_timeline に対するリクエストを投げる)
        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String url = "http://h.hatena.ne.jp/api/statuses/public_timeline.json";
                JsonArrayRequest req = new JsonArrayRequest(url, mTimelineRequestCallback, mTimelineRequestErrorback);
                mRequestQueue.add(req);
            }
        });

        // ListView に Adapter を結び付ける
        mHaikuStatusListAdapter = new HaikuStatusListAdapter(this, 10);
        ListView v = (ListView) findViewById(R.id.haiku_timeline_view);
        v.setAdapter(mHaikuStatusListAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Activity 再開時にバックグラウンドスレッドを開始する
        mRequestQueue.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Activity 停止時にはバックグラウンドスレッドを止める
        mRequestQueue.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 最終処理として念のためバックグラウンドスレッドを止める
        // (既に停止している場合に `stop()` しても問題ない)
        if (mRequestQueue != null) mRequestQueue.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
