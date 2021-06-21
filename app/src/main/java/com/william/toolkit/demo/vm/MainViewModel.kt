/*
 * Copyright WeiLianYang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.william.toolkit.demo.vm

import androidx.lifecycle.MutableLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.demo.service.Api
import com.william.toolkit.ext.request


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：
 */
class MainViewModel : BaseViewModel() {

    val bannerMsg = MutableLiveData<String>()

    fun testApi() {
        request({
            Api.apiService.getBanners()
        }, {
            bannerMsg.value =
                if (it.data.isNullOrEmpty()) "获取网络数据失败，请重试" else "成功获取网络数据，请前往Toolkit面板查看"
        })
    }

}