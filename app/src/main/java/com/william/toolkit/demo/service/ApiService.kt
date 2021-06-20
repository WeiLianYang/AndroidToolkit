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

package com.william.toolkit.demo.service

import com.william.toolkit.demo.data.Banner
import com.william.toolkit.demo.data.BaseResponse
import retrofit2.http.GET

/**
 * @author William
 * @date 2020/5/19 13:15
 * Class Comment：接口来自于 https://www.wanandroid.com/blog/show/2#47
 */
interface ApiService {

    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    suspend fun getBanners(): BaseResponse<List<Banner>?>

}