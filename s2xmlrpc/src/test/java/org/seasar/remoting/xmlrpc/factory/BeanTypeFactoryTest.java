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
package org.seasar.remoting.xmlrpc.factory;

import junit.framework.TestCase;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.seasar.remoting.xmlrpc.serializer.BeanSerializer;
import org.seasar.remoting.xmlrpc.test.entity.Employee;

public class BeanTypeFactoryTest extends TestCase {

	private BeanTypeFactory factory;
	private XmlRpcClientConfigImpl config;

	protected void setUp() throws Exception {
		super.setUp();
		XmlRpcController controller = new XmlRpcClient();
		factory = new BeanTypeFactory(controller);
		config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
	}

	public void testGetSerializer() throws Exception {
		TypeSerializer parser = factory.getSerializer(config, new Employee());
		assertTrue(parser instanceof BeanSerializer);
	}
}
