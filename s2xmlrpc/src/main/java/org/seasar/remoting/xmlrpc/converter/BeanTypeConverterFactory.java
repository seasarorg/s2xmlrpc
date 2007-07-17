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

import org.apache.xmlrpc.common.TypeConverter;
import org.apache.xmlrpc.common.TypeConverterFactoryImpl;

/**
 * TODO BeanConverterのキャッシュ化
 * @author agata
 */
public class BeanTypeConverterFactory extends TypeConverterFactoryImpl {

	private static BeanTypeConverterFactory instance = new BeanTypeConverterFactory();

    private static final TypeConverter objectArrayTypeConverter = new StringArrayConverter();

    private BeanTypeConverterFactory() {}
	
	public TypeConverter getTypeConverter(Class class1) {
		TypeConverter converter;
		try {
	        if (class1.isAssignableFrom(String[].class)) {
	            return objectArrayTypeConverter;
	        }
			converter = super.getTypeConverter(class1);
		} catch (IllegalStateException e) {
			if (class1 == void.class) {
				throw e;
			}
			return new BeanTypeConverter(class1);
		}
		if (converter.getClass().getName().equals("org.apache.xmlrpc.common.TypeConverterFactoryImpl$CastCheckingTypeConverter")) {
			return new BeanTypeConverter(class1);
		}
		return converter;
	}

	public static BeanTypeConverterFactory getFactory() {
		return instance;
	}
}
