package cn.com.videopls.pub;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.HashMap;

import cn.com.venvy.common.router.IRouterCallback;

/**
 * Created by yanjiangbo on 2017/5/17.
 */

public abstract class VideoPlusView<T extends VideoPlusController> extends FrameLayout {

    protected T controller;

    public VideoPlusView(Context context) {
        super(context);
        init();
    }

    public VideoPlusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (controller != null) {
            controller.destroy();
        }
    }

    private void init() {
        controller = initVideoPlusController();
    }

    public void navigation(Uri uri, HashMap<String, String> params, IRouterCallback callback) {
        if (controller != null) {
            controller.navigation(uri, params, callback);
        }
    }

    public void closeInfoView() {
        if (controller != null) {
            controller.closeInfoView();
        }
    }

    public void setVideoOSAdapter(VideoPlusAdapter adapter) {
        if (controller != null) {
            controller.setAdapter(adapter);
        }
    }

    public abstract T initVideoPlusController();

    public void start() {
        if (controller != null) {
            controller.start();
        }
    }

    public void stop() {
        if (controller != null) {
            controller.stop();
        }
    }
}
