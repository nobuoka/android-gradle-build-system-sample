package info.vividcode.android.app.gradlebuildsystemsample;

import org.json.JSONArray;

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
import android.widget.Toast;

public class MainActivity extends Activity {

    /** この Activity の running 中 (onStart から onStop まで) ずっと動いている RequestQueue */
    private RequestQueue mRequestQueue;

    /** はてなハイクの public_timeline に対するリクエスト成功時のコールバック */
    private final Response.Listener<JSONArray> mTimelineRequestCallback =
    new Response.Listener<JSONArray>() {
        @Override public void onResponse(JSONArray response) {
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
        }
    };

    /** はてなハイクの public_timeline に対するリクエスト失敗時のコールバック */
    private final Response.ErrorListener mTimelineRequestErrorback =
    new Response.ErrorListener() {
        @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            Log.d("GradleBuildSystemSample", "TimelineRequestErrorback", error);
        }
    };

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
                String url = "http://h.hatena.ne.jp/aapi/statuses/public_timeline.json";
                JsonArrayRequest req = new JsonArrayRequest(url, mTimelineRequestCallback, mTimelineRequestErrorback);
                mRequestQueue.add(req);
            }
        });
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
