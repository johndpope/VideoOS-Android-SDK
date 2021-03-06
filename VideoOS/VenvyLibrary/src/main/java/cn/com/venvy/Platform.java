package cn.com.venvy;

import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import cn.com.venvy.common.download.DownloadImageTask;
import cn.com.venvy.common.download.DownloadImageTaskRunner;
import cn.com.venvy.common.download.DownloadTask;
import cn.com.venvy.common.download.DownloadTaskRunner;
import cn.com.venvy.common.download.TaskListener;
import cn.com.venvy.common.interf.IMediaControlListener;
import cn.com.venvy.common.interf.IPlatformLoginInterface;
import cn.com.venvy.common.interf.IWidgetClickListener;
import cn.com.venvy.common.interf.IWidgetCloseListener;
import cn.com.venvy.common.interf.IWidgetPrepareShowListener;
import cn.com.venvy.common.interf.IWidgetShowListener;
import cn.com.venvy.common.interf.OnTagKeyListener;
import cn.com.venvy.common.interf.WedgeListener;
import cn.com.venvy.common.track.TrackHelper;
import cn.com.venvy.common.utils.VenvyAsyncTaskUtil;
import cn.com.venvy.common.utils.VenvyFileUtil;

/**
 * Created by yanjiangbo on 2017/5/2.
 */

public class Platform implements Serializable {

    private static final long serialVersionUID = 259734984506L;

    private PlatformInfo mPlatformInfo;
    //手动上报逻辑
    private IPlatformLoginInterface mPlatformLoginInterface;
    private IMediaControlListener mMediaControlListener;
    private IWidgetShowListener mWidgetShowListener;
    private IWidgetCloseListener mWidgetCloseListener;
    private IWidgetClickListener mWidgetClickListener;
    private WedgeListener mWedgeListener;
    private IWidgetPrepareShowListener mPrepareShowListener;
    private OnTagKeyListener mTagKeyListener;
    private ViewGroup mContentViewGroup;

    private static final String PRE_LOAD_IMAGE = "pre_load_images";
    private static final String PRE_LOAD_MEDIA = "pre_load_medias";

    private DownloadImageTaskRunner mDownloadImageTaskRunner;
    private DownloadTaskRunner mDownloadTaskRunner;

    public Platform(PlatformInfo platformInfo) {
        if (platformInfo != null) {
            mPlatformInfo = platformInfo;
        }
        TrackHelper.init(this);
    }

    public void setContentViewGroup(ViewGroup contentViewGroup) {
        this.mContentViewGroup = contentViewGroup;
    }

    public ViewGroup getContentViewGroup() {
        return mContentViewGroup;
    }

    public void setWedgeListener(WedgeListener mWedgeListener) {
        this.mWedgeListener = mWedgeListener;
    }

    public void setMediaControlListener(IMediaControlListener mMediaControlListener) {
        this.mMediaControlListener = mMediaControlListener;
    }

    public void setWidgetPrepareShowListener(IWidgetPrepareShowListener mPrepareShowListener) {
        this.mPrepareShowListener = mPrepareShowListener;
    }

    public void setWidgetShowListener(IWidgetShowListener mShowListener) {
        this.mWidgetShowListener = mShowListener;
    }

    public void setPlatformLoginInterface(IPlatformLoginInterface mPlatformLoginInterface) {
        this.mPlatformLoginInterface = mPlatformLoginInterface;
    }

    public void setWidgetClickListener(IWidgetClickListener mWidgetClickListener) {
        this.mWidgetClickListener = mWidgetClickListener;
    }

    public void setWidgetCloseListener(IWidgetCloseListener mCloseListener) {
        this.mWidgetCloseListener = mCloseListener;
    }

    public void setTagKeyListener(OnTagKeyListener mTagKeyListener) {
        this.mTagKeyListener = mTagKeyListener;
    }


    public OnTagKeyListener getTagKeyListener() {
        return mTagKeyListener;
    }


    public IWidgetPrepareShowListener getPrepareShowListener() {
        return mPrepareShowListener;
    }

    public WedgeListener getWedgeListener() {
        return mWedgeListener;
    }

    public IMediaControlListener getMediaControlListener() {
        return mMediaControlListener;
    }

    public IWidgetClickListener getWidgetClickListener() {
        return mWidgetClickListener;
    }

    public IWidgetCloseListener getWidgetCloseListener() {
        return mWidgetCloseListener;
    }

    public IWidgetShowListener getWidgetShowListener() {
        return mWidgetShowListener;
    }


    public IPlatformLoginInterface getPlatformLoginInterface() {
        return mPlatformLoginInterface;
    }

    public void updatePlatformInfo(PlatformInfo platformInfo) {
        this.mPlatformInfo = platformInfo;
    }

    public PlatformInfo getPlatformInfo() {
        return mPlatformInfo;
    }

    public void onDestroy() {
        TrackHelper.onDestroy();
        if (mDownloadImageTaskRunner != null) {
            mDownloadImageTaskRunner.destroy();
        }
        if (mDownloadTaskRunner != null) {
            mDownloadTaskRunner.destroy();
        }
    }

    public void preloadImage(final String[] imageUrls, final TaskListener taskListener) {

        if (mDownloadImageTaskRunner == null) {
            mDownloadImageTaskRunner = new DownloadImageTaskRunner(App.getContext());
        }
        VenvyAsyncTaskUtil.doAsyncTask(PRE_LOAD_IMAGE, new VenvyAsyncTaskUtil.IDoAsyncTask<String, Void>() {
            @Override
            public Void doAsyncTask(String... strings) throws Exception {
                if (strings == null || strings.length <= 0) {
                    return null;
                }
                ArrayList<DownloadImageTask> arrayList = new ArrayList<>();
                for (String url : strings) {
                    DownloadImageTask task = new DownloadImageTask(App.getContext(), url);
                    arrayList.add(task);
                }
                mDownloadImageTaskRunner.startTasks(arrayList, taskListener);
                return null;
            }
        }, null, imageUrls);
    }

    public void preloadMedia(final String[] mediaUrls, final TaskListener taskListener) {
        if (mDownloadTaskRunner == null) {
            mDownloadTaskRunner = new DownloadTaskRunner(this);
        }
        VenvyAsyncTaskUtil.doAsyncTask(PRE_LOAD_MEDIA, new VenvyAsyncTaskUtil.IDoAsyncTask<String, Void>() {
            @Override
            public Void doAsyncTask(String... strings) throws Exception {
                if (strings == null || strings.length <= 0) {
                    return null;
                }
                ArrayList<DownloadTask> arrayList = new ArrayList<>();
                for (String url : strings) {
                    DownloadTask task = new DownloadTask(App.getContext(), url, VenvyFileUtil
                            .getCacheDir(App.getContext()) + "/media/" + url.hashCode());
                    arrayList.add(task);
                }
                mDownloadTaskRunner.startTasks(arrayList, taskListener);
                return null;
            }
        }, null, mediaUrls);
    }

    public DownloadTaskRunner getDownloadTaskRunner() {
        return mDownloadTaskRunner;
    }

    public DownloadImageTaskRunner getDownloadImageTaskRunner() {
        return mDownloadImageTaskRunner;
    }

    public void stopBackgroundThread() {
        VenvyAsyncTaskUtil.cancel(PRE_LOAD_MEDIA);
        VenvyAsyncTaskUtil.cancel(PRE_LOAD_IMAGE);
    }
}
