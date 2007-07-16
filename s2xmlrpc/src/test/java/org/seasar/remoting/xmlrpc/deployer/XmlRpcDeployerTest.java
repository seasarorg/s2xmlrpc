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
package org.seasar.remoting.xmlrpc.deployer;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcNoSuchHandlerException;
import org.seasar.extension.unit.S2TestCase;

public class XmlRpcDeployerTest extends S2TestCase {

	private XmlRpcDeployer deployer;
	protected void setUp() throws Exception {
		include("XmlRpcDeployerTest.dicon");
		deployer = new XmlRpcDeployer();
		deployer.setContainer(getContainer());
	}
	
	public void testDeploy() throws Exception {
		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		deployer.deploy(phm);
		assertNotNull(phm.getHandler("EmployeeService.getEmployees"));
		assertNotNull(phm.getHandler("EmployeeService.getEmployeeList"));
		assertNotNull(phm.getHandler("EmployeeService.getEmployeeMap"));
		assertNotNull(phm.getHandler("EmployeeService.getEmployee"));
		assertNotNull( phm.getHandler("Echo.echo"));

		// 以下、インターフェイスに定義されていないメソッドの扱いのテスト
		assertNotNull("実装クラスのpublicメソッドは全てリモートメソッドとなる", 
				phm.getHandler("EmployeeService.publicGetEmployee"));
		try {
			assertNotNull(phm.getHandler("EmployeeService.protectedGetEmployee"));
			fail();
		} catch (XmlRpcNoSuchHandlerException ex) {}
		try {
			assertNotNull(phm.getHandler("EmployeeService.packageGetEmployee"));
			fail();
		} catch (XmlRpcNoSuchHandlerException ex) {}
	}
}
