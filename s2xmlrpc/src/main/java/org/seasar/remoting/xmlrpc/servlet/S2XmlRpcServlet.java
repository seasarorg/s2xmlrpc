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
package org.seasar.remoting.xmlrpc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.webserver.XmlRpcServlet;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.deployer.XmlRpcDeployer;
import org.seasar.remoting.xmlrpc.factory.BeanTypeFactory;
import org.seasar.remoting.xmlrpc.factory.S2RequestProcessorFactoryFactory;

public class S2XmlRpcServlet extends XmlRpcServlet {
	
	private static final long serialVersionUID = -251275230949920492L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		getXmlRpcServletServer().setTypeConverterFactory(BeanTypeConverterFactory.getFactory());
		getXmlRpcServletServer().setTypeFactory(new BeanTypeFactory(getXmlRpcServletServer()));
	}
	protected XmlRpcHandlerMapping newXmlRpcHandlerMapping() throws XmlRpcException {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		PropertyHandlerMapping mapping = new PropertyHandlerMapping();
        mapping.setTypeConverterFactory(BeanTypeConverterFactory.getFactory());
        S2RequestProcessorFactoryFactory factoryFactory = new S2RequestProcessorFactoryFactory();
		factoryFactory.setContainer(container);
		mapping.setRequestProcessorFactoryFactory(factoryFactory);
        XmlRpcDeployer deployer = 
        	(XmlRpcDeployer) container.getComponent("xmlrpc.deployer");
        deployer.deploy(mapping);
        return mapping;
	}
}
