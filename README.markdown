Gradle Build System Sample
==================================================

[Gradle](http://www.gradle.org/) による Android アプリのビルドのサンプルのためのプロジェクトです。

Android Gradle プラグイン 0.7.x (Android Studio 0.4.x 対応版) を使っています。
より新しいバージョンでは、記述方法などが変更される可能性があります。

## 参考資料

* Gradle の android プラグインの説明: [Gradle Plugin User Guide — Android Tools Project Site](http://tools.android.com/tech-docs/new-build-system/user-guide)
* Gradle の android プラグインのソースコード: [Android アプリ用のビルドツールのリポジトリ](https://android.googlesource.com/platform/tools/build)
* 今回使用しているマルチプロジェクトのビルドについてのドキュメント: [Chapter 55. Multi-project Builds — Gradle user guide](http://www.gradle.org/docs/current/userguide/multi_project_builds.html)
  * 日本語版: [第54章 マルチプロジェクトのビルド — Gradle ユーザーガイド](http://gradle.monochromeroad.com/docs/userguide/multi_project_builds.html)

## 本プロジェクトの説明

Gradle を使って Android アプリのビルドができるサンプルプロジェクトです。
アプリの内容は、単に[はてなハイク](http://h.hatena.ne.jp/)のグローバルなタイムラインの最新の投稿
(最大 10 件) を取得して表示するだけというものです。

### プロジェクト構成

本プロジェクトには、次の 2 つのサブプロジェクトが含まれています。

* GradleBuildSystemSample : アプリの本体となるサブプロジェクト
* libraries/volley : アプリ本体が依存しているネットワーク通信用のライブラリ

サブプロジェクトの [Volley](https://android.googlesource.com/platform/frameworks/volley/)
ですが、Git での管理上はサブモジュールとなっています。

## 本プロジェクトのビルド方法

```
# プロジェクトを git clone する (サブモジュールも)
git clone --recursive git@github.com:nobuoka/android-gradle-build-system-sample.git

# 移動
cd android-gradle-build-system-sample

# ビルド
ANDROIND_HOME=/path/to/android_sdk ./gradlew build
# ビルド時に Android SDK Tools を使用するので、環境変数 ANDROID_HOME で
# そのパスを指定する; もしくは local.properties ファイルで指定してもよい
```

次にあげる必要なものさえ使える環境であれば、上の 4 つのコマンドでビルドが完了するはずです。
また、[Android Studio](http://developer.android.com/sdk/installing/studio.html) ならば Gradle
に対応しているので、Android Studio にインポートしてビルドすることも可能なはずです。

## 本プロジェクトのビルドに必要なもの

* [Git](http://git-scm.com/)
* [Java SE Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (1.6 か 1.7)
* [Android SDK Tools](http://developer.android.com/sdk/index.html)
  * どのバージョン以降が必要なのかということはよくわからない; revision 22.0.1 では問題なく動いた
