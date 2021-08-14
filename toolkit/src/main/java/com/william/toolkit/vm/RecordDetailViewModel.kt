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

package com.william.toolkit.vm

import android.text.SpannableString
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.util.coloringToJson
import kotlinx.coroutines.flow.flow


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：
 */
class RecordDetailViewModel : BaseViewModel() {

    fun getRecordData(bean: ApiRecordBean?): LiveData<SpannableString?> {
        return flow {
            val result = if (bean != null) coloringToJson(bean.toString()) else null
            emit(result)
        }.asLiveData()
    }

}