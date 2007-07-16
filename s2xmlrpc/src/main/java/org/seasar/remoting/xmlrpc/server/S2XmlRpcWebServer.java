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

import java.net.InetAddress;

import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.webserver.WebServer;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.deployer.XmlRpcDeployer;
import org.seasar.remoting.xmlrpc.factory.BeanTypeFactory;

public class S2XmlRpcWebServer extends WebServer {

	private XmlRpcDeployer deployer;
	
	public S2XmlRpcWebServer(int pPort, InetAddress pAddr) {
		super(pPort, pAddr);
	}

	public S2XmlRpcWebServer(int pPort) {
		super(pPort);
	}
	
	public void deploy() {
		PropertyHandlerMapping mapping = new PropertyHandlerMapping();
		mapping.setTypeConverterFactory(BeanTypeConverterFactory.getFactory());
        deployer.deploy(mapping);
        getXmlRpcServer().setHandlerMapping(mapping);
	}

	protected XmlRpcStreamServer newXmlRpcStreamServer() {
		XmlRpcStreamServer server = super.newXmlRpcStreamServer();
		XmlRpcController controller = new XmlRpcServer();
		server.setTypeFactory(new BeanTypeFactory(controller));
		server.setTypeConverterFactory(BeanTypeConverterFactory.getFactory());
		return server;
	}

	public void setDeployer(XmlRpcDeployer deployer) {
		this.deployer = deployer;
	}
}