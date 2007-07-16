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

import org.apache.xmlrpc.server.RequestProcessorFactoryFactory.RequestProcessorFactory;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.remoting.xmlrpc.test.service.Echo;

public class S2RequestProcessorFactoryFactoryTest extends S2TestCase {

	protected void setUp() throws Exception {
		include("S2RequestProcessorFactoryFactoryTest.dicon");
	}
	
	public void testGetRequestProcessorFactory() throws Exception {
		S2RequestProcessorFactoryFactory factoryFactory = new S2RequestProcessorFactoryFactory();
		factoryFactory.setContainer(getContainer());
		RequestProcessorFactory factory = factoryFactory.getRequestProcessorFactory(Echo.class);
		Echo echo = (Echo) factory.getRequestProcessor(null);
		assertNotNull(echo);
	}
}
