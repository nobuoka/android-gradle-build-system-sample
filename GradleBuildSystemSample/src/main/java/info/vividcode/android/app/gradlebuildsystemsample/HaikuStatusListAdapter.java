package info.vividcode.android.app.gradlebuildsystemsample;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * HaikuStatus の情報を表示するための ListView に結び付けられる ArrayAdapter
 */
class HaikuStatusListAdapter extends ArrayAdapter<HaikuStatus> {

    private final LayoutInflater mInflater;

    private final int mLimit;

    public HaikuStatusListAdapter(Activity act, int limit) {
        super(act, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) act.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mLimit = limit;
    }

    public void addAllOnHead(List<HaikuStatus> statuses) {
        // 追加しようとした項目が制限より多ければ項目を絞り込み
        if (statuses.size() > mLimit) statuses = statuses.subList(0, mLimit);
        // 既存の項目と追加しようとした項目の和と制限数の比較
        int remSize = this.getCount() + statuses.size() - mLimit;
        // 必要なら既存の項目を削除
        for (int i = 0; i < remSize; ++i) this.remove(this.getItem(this.getCount()-1));
        // 追加
        for (int i = statuses.size() - 1; i >= 0; --i) this.insert(statuses.get(i), 0);
    }

    /** ListView 内の項目が表示されるときに呼び出される (このメソッドで ListView の各項目の中身を設定) */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 再利用できるなら再利用する; できない場合は新たに生成
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.haiku_status, null);

        // HaikuStatus オブジェクト取得
        HaikuStatus status = getItem(position);
        // View にユーザー名やテキストなどを埋め込む
        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        nameView.setText(status.userName);
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(status.text);

        return convertView;
    }

}
