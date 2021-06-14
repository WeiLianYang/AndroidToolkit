package com.william.toolkit.vm

import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.ext.launch
import com.william.toolkit.util.coloringToJson


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：
 */
class RecordDetailViewModel : BaseViewModel() {

    val recordData = MutableLiveData<SpannableString?>()

    fun handleData(bean: ApiRecordBean?) {
        launch({
            if (bean != null) coloringToJson(bean.toString()) else null
        }, {
            recordData.value = it
        })
    }

}