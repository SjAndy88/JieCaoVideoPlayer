package fm.jiecao.jcvideoplayer_lib.listener;

/**
 * Created by jun.sheng on 2017/7/25.
 */

public interface JCPlayerStateListener {
    void onNormal();

    void onPreparing();

    void onPlaying();

    void onPause();

    void onError();

    void onAutoCompletion();

    void onBuffering();

    /* 返回是否需要保留当前播放界面 */
    boolean keepCurViewAlive();
}
