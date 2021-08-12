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

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.ext.launch
import com.william.toolkit.manager.DataManager


/**
 * @author William
 * @date 2021/6/13 18:36
 * Class Comment：
 */
class RecordListViewModel : BaseViewModel() {

    val clearFlag = MutableLiveData<Boolean>()

    val recordListData = DataManager.getRecordList(0).asLiveData()

    fun clearRecord() {
        launch({
            DataManager.deleteAllRecord()
        }, {
            clearFlag.value = it
        })
    }

}