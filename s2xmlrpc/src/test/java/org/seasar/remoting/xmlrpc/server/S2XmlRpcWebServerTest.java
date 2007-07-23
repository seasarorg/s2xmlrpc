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
package org.seasar.remoting.xmlrpc.server;

import java.net.URL;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.remoting.xmlrpc.client.S2XmlRpcClientFactory;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.factory.BeanTypeFactory;
import org.seasar.remoting.xmlrpc.test.entity.Employee;
import org.seasar.remoting.xmlrpc.test.entity.Entity1;
import org.seasar.remoting.xmlrpc.test.service.Echo;
import org.seasar.remoting.xmlrpc.test.service.EmployeeService;
import org.seasar.remoting.xmlrpc.test.service.StringArrayService;

public class S2XmlRpcWebServerTest extends S2TestCase {
	private EmployeeService employeeService;
	private Echo echo;
	private S2XmlRpcWebServer xmlRpcWebServer;
	private StringArrayService stringArrayService;

	protected void setUp() throws Exception {
		include("S2XmlRpcWebServerTest.dicon");
		xmlRpcWebServer = (S2XmlRpcWebServer) getContainer().getComponent("xmlRpcWebServer");
		xmlRpcWebServer.start();
		
		// setup client
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
	    config.setEnabledForExtensions(true);
	    client.setConfig(config);
	    client.setTypeFactory(new BeanTypeFactory(xmlRpcWebServer.getXmlRpcServer()));	    	    
        S2XmlRpcClientFactory factory = new S2XmlRpcClientFactory(client, BeanTypeConverterFactory.getFactory());
        employeeService = (EmployeeService) factory.newInstance(EmployeeService.class, "EmployeeService");   
        echo = (Echo) factory.newInstance(Echo.class, "Echo");   
    	stringArrayService = (StringArrayService) factory.newInstance(StringArrayService.class, "StringArrayService");   
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		xmlRpcWebServer.shutdown();
	}

	public void testWriteDataAsBeanArray() throws Exception {
		Employee[] employees = employeeService.getEmployees();
		assertEquals(1, employees.length);
		Employee employee = employees[0];
		assertEquals("first1", employee.getFirstName());
		assertEquals("last1", employee.getLastName());
		assertEquals(1, employee.getAge());
	}

	// Java5じゃないとだめ
//	public void testWriteDataAsBeanList() throws Exception {
//		List employees = employeeService.getEmployeeList();
//		assertEquals(1, employees.size());
//		Employee employee = (Employee) employees.get(0);
//		assertEquals("first1", employee.getFirstName());
//		assertEquals("last1", employee.getLastName());
//		assertEquals(1, employee.getAge());
//	}

	public void testWriteDataAsMap() throws Exception {
		Map employees = employeeService.getEmployeeMap();
		assertEquals("first1", employees.get("firstName"));
		assertEquals("last1", employees.get("lastName"));
		assertEquals(new Integer(1), employees.get("age"));
	}
	
    public void testEcho() throws Throwable {
		assertEquals("[id = 1] test", echo.echo(1, "test"));
	}

    public void testStringArrayServiceEcho() throws Exception {
    	String[] actual = stringArrayService.echo(new String[] {"one", "two"});
    	assertEquals(2, actual.length);
    	assertEquals("one", actual[0]);
    	assertEquals("two", actual[1]);
    }

    public void testStringArrayServiceEcho2() throws Exception {
    	Entity1 entity = new Entity1();
    	entity.setWord("word1");
    	entity.setNames(new String[] {"name1", "name2"});
    	Entity1 actual = stringArrayService.echo2(entity);
    	assertEquals("word1", actual.getWord());
    	assertEquals(2, actual.getNames().length);
    	assertEquals("name1", actual.getNames()[0]);
    	assertEquals("name2", actual.getNames()[1]);
    }
}
