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
package org.seasar.remoting.xmlrpc.serializer;

import junit.framework.TestCase;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.seasar.remoting.xmlrpc.test.entity.Employee;
import org.seasar.remoting.xmlrpc.test.handler.TestHandler;

public class BeanSerializerTest extends TestCase {

	public void testWriteData() throws Exception {
		XmlRpcController controller = new XmlRpcClient();
		TypeFactory factory = new TypeFactoryImpl(controller);
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
		BeanSerializer serializer = new BeanSerializer(factory, config);
		TestHandler handler = new TestHandler();
		Employee data = new Employee();
		data.setFirstName("seasar");
		data.setLastName("taro");
		data.setAge(4);
		serializer.writeData(handler, data);
		assertEquals(6, handler.charcters.size());
		assertEquals("firstName", handler.charcters.get(0));
		assertEquals("seasar", handler.charcters.get(1));
		assertEquals("lastName", handler.charcters.get(2));
		assertEquals("taro", handler.charcters.get(3));
		assertEquals("age", handler.charcters.get(4));
		assertEquals("4", handler.charcters.get(5));
	}
	
}
