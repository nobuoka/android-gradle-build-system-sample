package info.vividcode.android.app.gradlebuildsystemsample;

/** はてなハイクへの投稿を表すクラス */
class HaikuStatus {
    public final String userName;
    public final String text;
    public final String profileImageUrl;
    public HaikuStatus(String userName, String text, String profileImageUrl) {
        this.userName = userName;
        this.text = text;
        this.profileImageUrl = profileImageUrl;
    }
}
