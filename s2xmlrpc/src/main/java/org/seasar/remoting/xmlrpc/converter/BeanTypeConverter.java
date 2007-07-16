/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.remoting.xmlrpc.converter;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

import org.apache.xmlrpc.common.TypeConverter;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;

public class BeanTypeConverter implements TypeConverter {

	private final Class target;

	BeanTypeConverter(Class target) {
		this.target = target;
	}

	public boolean isConvertable(Object pObject) {
		return pObject == null || pObject instanceof Map;
	}

	public Object convert(Object pObject) {
		if (pObject == null) {
			return null;
		}
		if (target.isArray()) {
			Object[] mapArray = pObject.getClass().isArray() ? (Object[]) pObject : new Map[] {(Map) pObject};
			Class beanClass = target.getComponentType();
			Object[] beans = (Object[]) Array.newInstance(beanClass,
					mapArray.length);
			for (int i = 0; i < mapArray.length; i++) {
				Map src = (Map) mapArray[i];
				Object bean = ClassUtil.newInstance(beanClass);
				copyProperties(src, bean);
				beans[i] = bean;
			}
			return beans;
		} else {
			Map src = null;
			if(pObject.getClass().isArray()) {
				Object[] mapArray = (Object[]) pObject;
				if (mapArray.length != 1) {
					throw new S2XmlRpcException("SXXX");// TODO
				}
				src = (Map) mapArray[0];
			} else {
				src = (Map) pObject;
			}
			Object bean = ClassUtil.newInstance(target);
			copyProperties(src, bean);			
			return bean;
		}
	}

	public Object backConvert(Object pObject) {
		return pObject;
	}
	
    public static void copyProperties(Map src, Object dest) {
        if (src == null || dest == null) {
            return;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
        for (Iterator i = src.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (!beanDesc.hasPropertyDesc(key)) {
                continue;
            }
            PropertyDesc pd = beanDesc.getPropertyDesc(key);
            if (pd.hasWriteMethod()) {
            	BeanTypeConverterFactory factory = BeanTypeConverterFactory.getFactory();
            	TypeConverter converter = factory.getTypeConverter(pd.getPropertyType());
            	if (converter instanceof BeanTypeConverter) {
                    pd.setValue(dest, converter.convert(src.get(key)));
            	}  else {
                    pd.setValue(dest, src.get(key));
            	}
            }
        }
    }

}
