package com.iti.common.util;

import java.io.Serializable;

/**
 * 创建试题的全局ID
 * @author k23
 *
 */
public class EntityUtil {
	public static String buildEntityGlobeId(Class<?> entity,String id){
		StringBuilder sb = new StringBuilder();
		sb.append(entity.getName());
		sb.append("_");
		sb.append(id);
		return sb.toString();
	}
	public static String buildEntityGlobeId(Class<?> entity, Serializable id) {
		StringBuilder sb = new StringBuilder();
		sb.append(entity.getName());
		sb.append("_");
		sb.append(id);
		return sb.toString();
	}
}
