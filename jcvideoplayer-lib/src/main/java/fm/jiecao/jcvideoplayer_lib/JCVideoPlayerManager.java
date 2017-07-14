package fm.jiecao.jcvideoplayer_lib;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Put JCVideoPlayer into layout
 * From a JCVideoPlayer to another JCVideoPlayer
 * Created by Nathen on 16/7/26.
 */
public class JCVideoPlayerManager {

    private static WeakReference<JCMediaPlayerListener> CURRENT_SCROLL_LISTENER;
    static LinkedList<WeakReference<JCMediaPlayerListener>> LISTENER_LIST = new LinkedList<>();

    static void putScrollListener(@NonNull JCMediaPlayerListener listener) {
        if (listener.getScreenType() == JCVideoPlayer.SCREEN_WINDOW_TINY ||
                listener.getScreenType() == JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN) return;
        CURRENT_SCROLL_LISTENER = new WeakReference<>(listener);//每次setUp的时候都应该add
    }

    static JCMediaPlayerListener getScrollListener() {
        if (JCVideoPlayerManager.CURRENT_SCROLL_LISTENER != null) {
            return CURRENT_SCROLL_LISTENER.get();
        }
        return null;
    }

    static void putListener(@NonNull JCMediaPlayerListener listener) {
        LISTENER_LIST.push(new WeakReference<>(listener));
    }

    static void checkAndPutListener(@NonNull JCMediaPlayerListener listener) {
        if (listener.getScreenType() == JCVideoPlayer.SCREEN_WINDOW_TINY ||
                listener.getScreenType() == JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN) return;
        int location = -1;
        for (int i = 1; i < LISTENER_LIST.size(); i++) {
            JCMediaPlayerListener jcMediaPlayerListener = LISTENER_LIST.get(i).get();
            if (listener.getUrl().equals(jcMediaPlayerListener.getUrl())) {
                location = i;
            }
        }
        if (location != -1) {
            LISTENER_LIST.remove(location);
            if (LISTENER_LIST.size() <= location) {
                LISTENER_LIST.addLast(new WeakReference<>(listener));
            } else {
                LISTENER_LIST.set(location, new WeakReference<>(listener));

            }
        }
    }

    static JCMediaPlayerListener popListener() {
        if (LISTENER_LIST.size() == 0) {
            return null;
        }
        return LISTENER_LIST.pop().get();
    }

    public static JCMediaPlayerListener getFirst() {
        if (LISTENER_LIST.size() == 0) {
            return null;
        }
        return LISTENER_LIST.getFirst().get();
    }

    static void completeAll() {
        JCMediaPlayerListener ll = popListener();
        while (ll != null) {
            ll.onCompletion();
            ll = popListener();
        }
    }
}
