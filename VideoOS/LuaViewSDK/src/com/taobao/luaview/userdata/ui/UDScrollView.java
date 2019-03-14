/*
 * Created by LuaView.
 * Copyright (c) 2017, Alibaba Group. All rights reserved.
 *
 * This source code is licensed under the MIT.
 * For the full copyright and license information,please view the LICENSE file in the root directory of this source tree.
 */

package com.taobao.luaview.userdata.ui;

import android.view.View;
import android.view.ViewGroup;

import com.taobao.luaview.util.LuaUtil;
import com.taobao.luaview.view.LVScrollView;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

/**
 * 容器类-ListView，模拟OC的section分区实现，Section顺序排列
 *
 * @author song
 * @date 15/8/20
 */
public class UDScrollView extends UDViewGroup<LVScrollView> {
    LuaValue mOnScrolling;
    LuaValue mOnScrollBegin;
    LuaValue mOnScrollEnd;


    @Override
    public UDScrollView setCallback(LuaValue callbacks) {
        super.setCallback(callbacks);
        if (this.mCallback != null) {
            mOnScrolling = LuaUtil.getFunction(mCallback, "Scrolling", "scrolling");
            mOnScrollBegin = LuaUtil.getFunction(mCallback, "ScrollBegin", "scrollBegin");
            mOnScrollEnd = LuaUtil.getFunction(mCallback, "ScrollEnd", "scrollEnd");
        }
        return this;
    }


    public void callOnScrolling(int x, int y, int oldX, int oldY) {
        LuaUtil.callFunction(mOnScrolling, x, y, oldX, oldY);
    }

    public void callOnScrollBegin() {
        LuaUtil.callFunction(mOnScrollBegin);

    }

    public void callOnScrollEnd() {
        LuaUtil.callFunction(mOnScrollEnd);
    }

    public UDScrollView(LVScrollView view, Globals globals, LuaValue metaTable, LuaValue initParams) {
        super(view, globals, metaTable, initParams);
    }

    /**
     * 获取容器view
     *
     * @return
     */
    public ViewGroup getContainer() {
        return getView() != null ? getView().getContainer() : null;
    }

    /**
     * 获取ScrollView 滚动方向
     *
     * @return
     */
    public UDScrollView setOrientation(int model) {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.setOrientation(model);
        }
        return this;
    }

    public LuaValue getOrientation() {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            return valueOf(scrollView.getOrientation());
        }
        return LuaValue.NIL;

    }

    /**
     * 滚动到某个位置
     *
     * @param x
     * @param y
     * @return
     */
    public UDScrollView smoothScrollTo(final int x, final int y) {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(x, y);
                }
            });
        }
        return this;
    }

    @Override
    public UDView scrollTo(final int x, final int y) {
        final View view = getView();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.scrollTo(x, y);
                }
            });
        }
        return this;
    }

    @Override
    public UDView scrollBy(final int dx, final int dy) {
        final View view = getView();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.scrollBy(dx, dy);
                }
            });
        }
        return this;
    }

    /**
     * 设置是否显示滚动条
     *
     * @param show
     * @return
     */
    public UDScrollView showScrollIndicator(boolean show) {
        LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.setVerticalScrollBarEnabled(show);
        }
        return this;
    }

    /**
     * 滚动dx and dy
     *
     * @param dx
     * @param dy
     * @return
     */
    public UDScrollView smoothScrollBy(final int dx, final int dy) {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollBy(dx, dy);
                }
            });
        }
        return this;
    }

    /**
     * 滚动一页 direction (>0, <0)
     *
     * @param direction
     * @return
     */
    public UDScrollView pageScroll(final int direction) {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (direction > 0) {
                        scrollView.pageScroll(View.FOCUS_DOWN);
                    } else if (direction < 0) {
                        scrollView.pageScroll(View.FOCUS_UP);
                    }
                }
            });
        }
        return this;
    }

    /**
     * 滚动到底
     *
     * @param direction (>0, <0)
     * @return
     */
    public UDScrollView fullScroll(final int direction) {
        final LVScrollView scrollView = getView();
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (direction > 0) {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    } else if (direction < 0) {
                        scrollView.fullScroll(View.FOCUS_UP);
                    }
                }
            });

        }
        return this;
    }
}
