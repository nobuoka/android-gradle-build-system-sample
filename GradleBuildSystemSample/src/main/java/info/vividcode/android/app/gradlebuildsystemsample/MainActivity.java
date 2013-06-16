package info.vividcode.android.app.gradlebuildsystemsample;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    /** この Activity の running 中 (onStart から onStop まで) ずっと動いている RequestQueue */
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Activity 作成時に RequestQueue オブジェクトを生成
        // (`newRequestQueue` メソッドで作られた RequestQueue のバックグラウンドスレッドは開始されている)
        mRequestQueue = Volley.newRequestQueue(this);
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
