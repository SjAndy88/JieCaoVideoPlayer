package fm.jiecao.jcvideoplayer_lib.listener;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by jun.sheng on 2017/7/25.
 */

public interface JCPlayerStateListener {
    void onStateChange(JCVideoPlayer videoPlayer, int state);

    /* 返回是否需要保留当前播放界面 */
    boolean keepCurViewAlive();
}
