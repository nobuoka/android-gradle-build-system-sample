package info.vividcode.android.app.gradlebuildsystemsample;

/** はてなハイクへの投稿を表すクラス */
class HaikuStatus {
    public final String userName;
    public final String text;
    public HaikuStatus(String userName, String text) {
        this.userName = userName;
        this.text = text;
    }
}
