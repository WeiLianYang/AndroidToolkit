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
import com.william.toolkit.ext.logd
import com.william.toolkit.ext.loge
import com.william.toolkit.manager.DataManager
import com.william.toolkit.util.coloringToJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：Record Detail ViewModel
 */
class RecordDetailViewModel : BaseViewModel() {

    fun getDetailData(id: Long): LiveData<SpannableString?> {
        return DataManager.getRecord(id).map { bean ->
            val result = if (bean != null) coloringToJson(bean.toString()) else null
            result
        }.flowOn(Dispatchers.Default)
            .onCompletion { cause -> "flow complete with $cause".logd() }
            .catch { ex -> "flow catch exception: $ex".loge() }
            .asLiveData()
    }

}