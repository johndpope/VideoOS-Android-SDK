package cn.com.venvy.lua.maper;

import android.text.TextUtils;

import com.taobao.luaview.fun.mapper.LuaViewApi;
import com.taobao.luaview.fun.mapper.LuaViewLib;
import com.taobao.luaview.fun.mapper.ui.UIViewMethodMapper;
import com.taobao.luaview.global.VmVersion;
import com.taobao.luaview.util.LuaUtil;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.List;

import cn.com.venvy.lua.ud.VenvyUDWebView;


/**
 * Created by Arthur on 2017/9/4.
 */
@LuaViewLib(revisions = {"20170306已对标"})
public class LVWebViewMethodMapper<U extends VenvyUDWebView> extends UIViewMethodMapper<U> {
    private static final String TAG = "LVWebViewMethodMapper";
    private static final String[] sMethods = new String[]{
            "loadUrl",  //0
            "canGoBack", // 1
            "canGoForward", //2
            "goBack", //3
            "goForward",//4
            "reload",//5
            "title",//6
            "isLoading",//7
            "stopLoading",//8
            "url",//9
            "pullRefreshEnable",//10
            "callJS"//11
    };

    @Override
    public List<String> getAllFunctionNames() {
        return mergeFunctionNames(TAG, super.getAllFunctionNames(), sMethods);
    }

    @Override
    public Varargs invoke(int code, U target, Varargs varargs) {
        final int optcode = code - super.getAllFunctionNames().size();
        switch (optcode) {
            case 0:
                return loadUrl(target, varargs);
            case 1:
                return canGoBack(target, varargs);
            case 2:
                return canGoForward(target, varargs);
            case 3:
                return goBack(target, varargs);
            case 4:
                return goForward(target, varargs);
            case 5:
                return reload(target, varargs);
            case 6:
                return title(target, varargs);
            case 7:
                return isLoading(target, varargs);
            case 8:
                return stopLoading(target, varargs);
            case 9:
                return url(target, varargs);
            case 10:
                return pullRefreshEnable(target, varargs);
            case 11:
                return callJS(target, varargs);
        }
        return super.invoke(code, target, varargs);
    }

    //--------------------------------------- API --------------------------------------------------
    // 使用反射的方式调用的时候,需要public关键字声明

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue loadUrl(U view, Varargs varargs) {
        String url = LuaUtil.getString(varargs, 2);
        if (TextUtils.isEmpty(url)) {
            return LuaValue.NIL;
        }
        return view.loadUrl(url);
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue canGoBack(U view, Varargs varargs) {
        return LuaValue.valueOf(view.canGoBack());
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue canGoForward(U view, Varargs varargs) {
        return LuaValue.valueOf(view.canGoForward());
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue goBack(U view, Varargs varargs) {
        return view.goBack();
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue goForward(U view, Varargs varargs) {
        return view.goForward();
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue reload(U view, Varargs varargs) {
        return view.reload();
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue title(U view, Varargs varargs) {
        return LuaValue.valueOf(view.title());
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue isLoading(U view, Varargs varargs) {
        return LuaValue.valueOf(view.isLoading());
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue stopLoading(U view, Varargs varargs) {
        return view.stopLoading();
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue url(U view, Varargs varargs) {
        return LuaValue.valueOf(view.url());
    }

    @LuaViewApi(since = VmVersion.V_530)
    public LuaValue pullRefreshEnable(U view, Varargs varargs) {
        if (varargs.narg() > 1) {
            final boolean enable = LuaUtil.getBoolean(varargs, 2);
            return view.pullRefreshEnable(enable);
        } else {
            return LuaValue.valueOf(view.isPullRefreshEnable());
        }
    }

    public LuaValue callJS(U view, Varargs varargs) {
        String jsMethod = LuaUtil.getString(varargs, 2);
        final LuaFunction callback = LuaUtil.getFunction(varargs, 3);
        return LuaValue.valueOf(view.callJS(jsMethod, callback));
    }
}
