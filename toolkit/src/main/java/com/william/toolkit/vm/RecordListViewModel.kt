package com.william.toolkit.vm

import androidx.lifecycle.MutableLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.ext.launch
import com.william.toolkit.manager.DataManager


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：
 */
class RecordListViewModel : BaseViewModel() {

    val clearFlag = MutableLiveData<Boolean>()

    val recordListData = MutableLiveData<List<ApiRecordBean>>()

    var startId: Long = 0

    fun clearRecord() {
        launch({
            DataManager.deleteAllRecord()
        }, {
            clearFlag.value = it
        })
    }

    fun getRecordList() {
        launch({
            DataManager.getRecordList(startId)
        }, {
            /*if (it.isNotEmpty()) {
                startId = it.last().id
                "最后一行记录的id:$startId".logd()
            }*/
            recordListData.value = it
        })
    }
}