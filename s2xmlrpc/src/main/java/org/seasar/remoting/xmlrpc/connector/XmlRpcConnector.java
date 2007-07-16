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

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeConverter;
import org.apache.xmlrpc.common.TypeConverterFactory;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.remoting.common.connector.impl.URLBasedConnector;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.factory.BeanTypeFactory;

/**
 * XML-RPCを呼び出すコネクタです。
 * 
 * @author agata
 */
public class XmlRpcConnector extends URLBasedConnector {

	private TypeConverterFactory typeConverterFactory = BeanTypeConverterFactory.getFactory();

	private XmlRpcClient clientChache = null;
	
    /**
     * デフォルトのコンストラクタ。
     */
    public XmlRpcConnector() {
    }

    public void setTypeConverterFactory(TypeConverterFactory typeConverterFactory) {
    	this.typeConverterFactory = typeConverterFactory;
    }
    
	public Object invoke(String remoteName, Method method, Object[] args)
			throws Throwable {
		AssertionUtil.assertNotEmpty("remoteName", remoteName);
		
		String methodName = remoteName + "." + method.getName();
        Object result = null;
		try {
			XmlRpcClient client = getClient();
	        result = client.execute(methodName, args);
		} catch (XmlRpcException ex) {
			throw new S2XmlRpcException("EXRP1001", new Object[] {methodName}, ex);
		}
        TypeConverter typeConverter = typeConverterFactory.getTypeConverter(method.getReturnType());
        return typeConverter.convert(result);
	}

	private synchronized XmlRpcClient getClient() {
		if(clientChache == null) {
	        clientChache = new XmlRpcClient();
	        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		    config.setServerURL(getBaseURL());
		    config.setEnabledForExtensions(true);
		    clientChache.setConfig(config);
			clientChache.setTypeFactory(new BeanTypeFactory(clientChache));
		}
		return clientChache;
	}
}
