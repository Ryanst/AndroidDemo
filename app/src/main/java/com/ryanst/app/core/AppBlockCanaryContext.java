/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryanst.app.core;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.ryanst.app.BuildConfig;
import com.ryanst.app.util.ChannelUtil;
import com.ryanst.app.util.NetWorkUtil;
import com.ryanst.app.util.VersionUtil;

/**
 * 监控环境的上下文实现
 * <p/>
 * Created by markzhai on 15/9/25.
 */
public class AppBlockCanaryContext extends BlockCanaryContext {
    private static final String TAG = "AppBlockCanaryContext";

    /**
     * 标示符，可以唯一标示该安装版本号，如版本+渠道名+编译平台
     *
     * @return apk唯一标示符
     */
    @Override
    public String getQualifier() {
        String qualifier = VersionUtil.versionCode(RyanstApp.getApplication()) + "_" + VersionUtil.versionName(RyanstApp.getApplication()) + ChannelUtil.getChannel(RyanstApp.getApplication());
        return qualifier;
    }

    @Override
    public String getUid() {
        return "87224330";
    }

    @Override
    public String getNetworkType() {
        return NetWorkUtil.getNetTypeString(RyanstApp.getApplication());
    }

    @Override
    public int getConfigDuration() {
        return 9999;
    }

    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    @Override
    public boolean isNeedDisplay() {
        return BuildConfig.DEBUG;
    }
}