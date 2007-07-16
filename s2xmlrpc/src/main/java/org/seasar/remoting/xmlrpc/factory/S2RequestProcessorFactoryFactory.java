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

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.seasar.framework.container.S2Container;

public class S2RequestProcessorFactoryFactory implements
		RequestProcessorFactoryFactory {

	private S2Container container;
	
	public S2Container getContainer() {
		return container;
	}

	public void setContainer(S2Container container) {
		this.container = container;
	}

	public RequestProcessorFactory getRequestProcessorFactory(final Class class1)
			throws XmlRpcException {
        return new RequestProcessorFactory(){
            public Object getRequestProcessor(XmlRpcRequest pRequest) throws XmlRpcException {
                return S2RequestProcessorFactoryFactory.this.getRequestProcessor(class1, pRequest);
            }
        };
	}

	protected Object getRequestProcessor(Class pClass, XmlRpcRequest pRequest) throws XmlRpcException {
		return container.getComponent(pClass);
    }
}
