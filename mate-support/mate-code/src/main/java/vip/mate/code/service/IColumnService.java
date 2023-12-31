/*
 * Copyright 2020-2030, MateCloud, DAOTIANDI Technology Inc All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: pangu(7333791@qq.com)
 */
package vip.mate.code.service;

import vip.mate.code.entity.Column;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import vip.mate.core.database.entity.Search;

/**
 * <p>
 * 代码生成字段表 服务类
 * </p>
 *
 * @author pangu
 * @since 2022-03-21
 */
public interface IColumnService extends IService<Column> {

	/**
     * 分页业务方法
     * @param search　搜索参数
     * @return IPage
     */
	IPage<Column> listPage(Search search);

}
