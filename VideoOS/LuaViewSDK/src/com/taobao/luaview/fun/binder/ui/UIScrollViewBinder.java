/*
 * Created by LuaView.
 * Copyright (c) 2017, Alibaba Group. All rights reserved.
 *
 * This source code is licensed under the MIT.
 * For the full copyright and license information,please view the LICENSE file in the root directory of this source tree.
 */

package com.taobao.luaview.fun.binder.ui;

import com.taobao.luaview.fun.base.BaseFunctionBinder;
import com.taobao.luaview.fun.base.BaseVarArgUICreator;
import com.taobao.luaview.fun.mapper.ui.UIScrollViewMethodMapper;
import com.taobao.luaview.view.LVScrollView;
import com.taobao.luaview.view.interfaces.ILVView;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

/**
 * ScrollView 可滚动容器类，放各个view
 *
 * @author song
 * @date 15/8/20
 */
public class UIScrollViewBinder extends BaseFunctionBinder {

    public UIScrollViewBinder() {
        super("ScrollView");
    }

    @Override
    public Class<? extends LibFunction> getMapperClass() {
        return UIScrollViewMethodMapper.class;
    }

    @Override
    public LuaValue createCreator(LuaValue env, LuaValue metaTable) {
        return new BaseVarArgUICreator(env.checkglobals(), metaTable, getMapperClass()) {
            @Override
            public ILVView createView(Globals globals, LuaValue metaTable, Varargs varargs) {
                return new LVScrollView(globals, metaTable, varargs);
            }
        };
    }
}
