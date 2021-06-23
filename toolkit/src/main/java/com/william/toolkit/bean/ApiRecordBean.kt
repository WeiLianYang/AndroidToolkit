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

package com.william.toolkit.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.william.toolkit.util.format
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * @author William
 * @date 2020-02-17 16:51
 * Class Comment：API接口记录模型
 */
//@Entity(indices = [Index(value = ["requestTime"], unique = true)])
@Entity
@Parcelize
data class ApiRecordBean(
    var url: String? = null,
    var method: String? = null,
    var headers: String? = null,
    var request: String? = null,
    var response: String? = null,
    var requestTime: Long = 0,
    var duration: Long = 0,
    var httpCode: Int = 0
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return """${TITLE_ARRAY[0]} : $url

${TITLE_ARRAY[1]} : ${duration}ms

${TITLE_ARRAY[2]} : $httpCode

${TITLE_ARRAY[3]} : $method

${TITLE_ARRAY[4]} : ${format(headers)}

${TITLE_ARRAY[5]} : ${format(request)}

${TITLE_ARRAY[6]} : ${format(response)}

"""
    }

    companion object {

        @JvmField
        val TITLE_ARRAY =
            arrayOf(
                "URL",
                "Duration",
                "HttpCode",
                "Method",
                "Headers",
                "RequestBody",
                "ResponseBody"
            )

    }
}
