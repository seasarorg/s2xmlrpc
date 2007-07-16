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
package org.seasar.remoting.xmlrpc.connector;

import java.lang.reflect.Method;
import java.net.URL;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;
import org.seasar.remoting.xmlrpc.server.S2XmlRpcWebServer;
import org.seasar.remoting.xmlrpc.test.entity.Employee;
import org.seasar.remoting.xmlrpc.test.service.Echo;
import org.seasar.remoting.xmlrpc.test.service.EmployeeService;

public class XmlRpcConnectorTest extends S2TestCase {

	private S2XmlRpcWebServer xmlRpcWebServer;
	private XmlRpcConnector connector;

    protected void setUp() throws Exception {
        super.setUp();
		include("XmlRpcConnectorTest.dicon");
		xmlRpcWebServer = (S2XmlRpcWebServer) getContainer().getComponent("xmlRpcWebServer");
		xmlRpcWebServer.start();
		connector = new XmlRpcConnector();
		connector.setBaseURL(new URL("http://127.0.0.1:8080/xmlrpc"));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
		xmlRpcWebServer.shutdown();
    }

    public void testInvoke1() throws Throwable {
		Method method = EmployeeService.class.getMethod("getEmployees", new Class[]{});
		Object[] args = new Object[] {};
		Employee[] employees = (Employee[]) connector.invoke("EmployeeService", method, args);
		assertEquals(1, employees.length);
	}

    public void testInvoke2() throws Throwable {
		Method method = Echo.class.getMethod("echo", new Class[]{int.class, String.class});
		Object[] args = new Object[] {new Integer(1), "test"};
		String result = (String) connector.invoke("Echo", method, args);
		assertEquals("[id = 1] test", result);
	}

    public void testInvoke3_RemoteMethodNotFound() throws Throwable {
		Method method = Echo.class.getMethod("echo", new Class[]{int.class, String.class});
		Object[] args = new Object[] {new Integer(1), "test"};
		try {
			connector.invoke("Dummy", method, args);
			fail();
		} catch (S2XmlRpcException ex) {
			assertEquals("EXRP1001", ex.getMessageCode());
			assertEquals("Dummy.echo", ex.getArgs()[0]);
		}
	}

    public void testInvoke4_ServiceNameNull() throws Throwable {
		Method method = Echo.class.getMethod("echo", new Class[]{int.class, String.class});
		Object[] args = new Object[] {new Integer(1), "test"};
		try {
			connector.invoke("", method, args);
			fail();
		} catch (EmptyRuntimeException ex) {
			assertEquals("ESSR0007", ex.getMessageCode());
			assertEquals("remoteName", ex.getArgs()[0]);
		}
	}
    
//    public void testInvoke5_ArrayStringArgs() throws Throwable {
//		Method method = EmployeeServiceImpl.class.getMethod("setArrayStringParam", new Class[]{String[].class});
//		Object[] args = new Object[] {"one", "two"};
//		try {
//			connector.invoke("EmployeeService", method, args);
//			fail();
//		} catch (EmptyRuntimeException ex) {
//			assertEquals("ESSR0007", ex.getMessageCode());
//			assertEquals("remoteName", ex.getArgs()[0]);
//		}
//	}
}
