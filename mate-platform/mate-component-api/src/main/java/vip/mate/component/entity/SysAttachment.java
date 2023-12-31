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

import java.time.LocalDateTime;

/**
 * 附件表实体类
 *
 * @author pangu
 * @since 2020-08-09
 */
@Data
@TableName("mate_sys_attachment")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "SysAttachment对象", description = "附件表")
public class SysAttachment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 存储ID
	*/
	@Schema(description = "存储ID")
	private Long storageId;
	/**
	* 组ID
	*/
	@Schema(description = "组ID")
	private Integer attachmentGroupId;
	/**
	* 文件名称
	*/
	@Schema(description = "文件名称")
	private String name;
	/**
	* 文件大小
	*/
	@Schema(description = "文件大小")
	private long size;
	/**
	* 文件地址
	*/
	@Schema(description = "文件地址")
	private String url;

	/**
	 * 上传文件名
	 */
	@Schema(description = "上传文件名")
	private String fileName;

	/**
	* 缩略图地址
	*/
	@Schema(description = "缩略图地址")
	private String thumbUrl;
	/**
	* 类型
	*/
	@Schema(description = "类型")
	private Integer type;
	/**
	* 创建人
	*/
	@Schema(description = "创建人")
	private String createBy;
	/**
	* 更新人
	*/
	@Schema(description = "更新人")
	private String updateBy;
	/**
	* 创建时间
	*/
	@Schema(description = "创建时间")
	private LocalDateTime createTime;
	/**
	* 修改时间
	*/
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;
	/**
	* 删除标识
	*/
	@Schema(description = "删除标识")
	private String isDeleted;
	/**
	* 是否加入回收站 0.否|1.是
	*/
	@Schema(description = "是否加入回收站 0.否|1.是")
	private Boolean isRecycle;


}
