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