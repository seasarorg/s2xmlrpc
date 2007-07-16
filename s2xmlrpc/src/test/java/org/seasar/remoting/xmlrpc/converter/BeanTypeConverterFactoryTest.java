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
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverter;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.test.entity.Employee;

import junit.framework.TestCase;

public class BeanTypeConverterFactoryTest extends TestCase {

	public void testname() throws Exception {
		
		BeanTypeConverterFactory factory = BeanTypeConverterFactory.getFactory();
		TypeConverter converter = factory.getTypeConverter(int.class);
		assertEquals("org.apache.xmlrpc.common.TypeConverterFactoryImpl$PrimitiveTypeConverter", converter.getClass().getName());
		
		converter = factory.getTypeConverter(Employee.class);
		assertEquals(BeanTypeConverter.class.getName(), converter.getClass().getName());

		converter = factory.getTypeConverter(Employee[].class);
		assertEquals(BeanTypeConverter.class.getName(), converter.getClass().getName());
		
		try {
			converter = factory.getTypeConverter(void.class);
			fail();
		} catch (IllegalStateException ex) {}
	}
}
