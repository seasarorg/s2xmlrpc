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

/**
 * Beanに対応したコンバータクラスです。
 * MapをBeanに変換します。
 * @author agata
 */
public class BeanTypeConverter implements TypeConverter {
	/**
	 * 対象のBeanクラス
	 */
	private final Class target;

	/**
	 * コンストラクタ
	 * @param target 対象のBeanクラス
	 */
	BeanTypeConverter(final Class target) {
		this.target = target;
	}

	/**
	 * 変換可能か？
	 * @param object 変換元のオブジェクト
	 * @return 変換可能ならtrue
	 */
	public boolean isConvertable(final Object object) {
		return object == null || object instanceof Map;
	}

	/**
	 * オブジェクトを変換します。
	 * @param object 変換元
	 * @return 変換後のオブジェクト
	 */
	public Object convert(final Object object) {
		if (object == null) {
			return null;
		}
		if (target.isArray()) {
			final Object[] mapArray = object.getClass().isArray() ? (Object[]) object : new Map[] {(Map) object};
			final Class beanClass = target.getComponentType();
			final Object[] beans = (Object[]) Array.newInstance(beanClass,
					mapArray.length);
			for (int i = 0; i < mapArray.length; i++) {
				final Map src = (Map) mapArray[i];
				final Object bean = ClassUtil.newInstance(beanClass);
				copyProperties(src, bean);
				beans[i] = bean;
			}
			return beans;
		} else {
			Map src = null;
			if(object.getClass().isArray()) {
				final Object[] mapArray = (Object[]) object;
				if (mapArray.length != 1) {
					throw new S2XmlRpcException("SXXX");// TODO
				}
				src = (Map) mapArray[0];
			} else {
				src = (Map) object;
			}
			final Object bean = ClassUtil.newInstance(target);
			copyProperties(src, bean);			
			return bean;
		}
	}

	/**
	 * 逆変換を行います。逆変換はサポートされません。
	 * @param object 変換元
	 * @return 変換後のオブジェクト
	 */
	public Object backConvert(final Object object) {
    	throw new UnsupportedOperationException();
	}

	/**
	 * プロパティのコピーを行います。
	 * @param src コピー元
	 * @param dest コピー先
	 */
    private static void copyProperties(final Map src, final Object dest) {
        if (src == null || dest == null) {
            return;
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
        for (Iterator i = src.keySet().iterator(); i.hasNext();) {
            final String key = (String) i.next();
            if (!beanDesc.hasPropertyDesc(key)) {
                continue;
            }
            final PropertyDesc pd = beanDesc.getPropertyDesc(key);
            if (pd.hasWriteMethod()) {
            	final BeanTypeConverterFactory factory = BeanTypeConverterFactory.getFactory();
            	final TypeConverter converter = factory.getTypeConverter(pd.getPropertyType());
                pd.setValue(dest, converter.convert(src.get(key)));
            }
        }
    }
}
