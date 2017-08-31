package fm.jiecao.jcvideoplayer_lib.listener.imp;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.listener.JCPlayerStateListener;

/**
 * Created by jun.sheng on 2017/8/31.
 */

public class SimpleJCPlayerStateListener implements JCPlayerStateListener {

    @Override
    public void onStateChange(JCVideoPlayer videoPlayer, int state) {

    }

    @Override
    public boolean keepCurViewAlive() {
        return false;
    }
}
