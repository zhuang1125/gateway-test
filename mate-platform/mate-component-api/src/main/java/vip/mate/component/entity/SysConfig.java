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
package vip.mate.component.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.mate.core.database.entity.BaseEntity;

/**
 * 配置表实体类
 *
 * @author pangu
 * @since 2020-08-05
 */
@Data
@TableName("mate_sys_config")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "SysConfig对象", description = "配置表")
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 父主键
	*/
	@Schema(description = "父主键")
	private Long parentId;
	/**
	* 码
	*/
	@Schema(description = "码")
	private String code;
	/**
	* 值
	*/
	@Schema(description = "值")
	private String cKey;
	/**
	* 名称
	*/
	@Schema(description = "名称")
	private String value;
	/**
	* 排序
	*/
	@Schema(description = "排序")
	private Integer sort;
	/**
	* 备注
	*/
	@Schema(description = "备注")
	private String remark;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private Integer tenantId;
	/**
	* 是否已删除
	*/
	@Schema(description = "是否已删除")
	private Integer isDeleted;


}
