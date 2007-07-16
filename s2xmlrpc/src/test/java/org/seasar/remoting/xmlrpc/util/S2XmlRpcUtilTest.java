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
package org.seasar.remoting.xmlrpc.util;

import junit.framework.TestCase;

import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;
import org.seasar.remoting.xmlrpc.test.service.impl.EchoImpl;

public class S2XmlRpcUtilTest extends TestCase {

	public void testGetRemoteName() throws Exception {
		ComponentDefImpl componentDef = new ComponentDefImpl(EchoImpl.class, "Echo");
		assertEquals("Echo", S2XmlRpcUtil.getRemoteName(componentDef));

		componentDef = new ComponentDefImpl(EchoImpl.class);
		assertEquals("EchoImpl", S2XmlRpcUtil.getRemoteName(componentDef));

		componentDef = new ComponentDefImpl();
		try {
			S2XmlRpcUtil.getRemoteName(componentDef);
			fail();
		} catch (S2XmlRpcException ex) {
			assertEquals("EXRP2001", ex.getMessageCode());
		}
	}
}
