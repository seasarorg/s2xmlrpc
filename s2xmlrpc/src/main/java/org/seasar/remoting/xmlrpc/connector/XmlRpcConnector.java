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
import org.seasar.remoting.common.connector.Connector;
import org.seasar.remoting.common.connector.impl.URLBasedConnector;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;
import org.seasar.remoting.xmlrpc.factory.BeanTypeFactory;

/**
 * XML-RPCを呼び出すS2Remotingのコネクタです。
 * @author agata
 * @see Connector
 */
public class XmlRpcConnector extends URLBasedConnector {

	/**
	 * TypeConverterFactory
	 */
	private TypeConverterFactory typeConverterFactory = BeanTypeConverterFactory.getFactory();

	/**
	 * XmlRpcClient
	 */
	private XmlRpcClient clientCache = null;
	
    /**
     * デフォルトのコンストラクタ。
     */
    public XmlRpcConnector() {}

    /**
     * コンストラクタ。
     * @param typeConverterFactory TypeConverterFactory
     */
    public void setTypeConverterFactory(final TypeConverterFactory typeConverterFactory) {
    	this.typeConverterFactory = typeConverterFactory;
    }
    
    /**
     * リモートメソッドを実行します。
     * @see Connector#invoke(String, Method, Object[])
     * @param remoteName リモートコンポーネント名（ex:EmployeeService）
     * @param method メソッド
     * @param args 引数
     */
	public Object invoke(final String remoteName, final Method method, final Object[] args)
			throws Throwable {
		AssertionUtil.assertNotEmpty("remoteName", remoteName);
		
		final String methodName = remoteName + "." + method.getName();
        Object result = null;
		try {
			result = getClient().execute(methodName, args);
		} catch (XmlRpcException ex) {
			throw new S2XmlRpcException("EXRP1001", new Object[] {methodName}, ex);
		}
        final TypeConverter typeConverter = typeConverterFactory.getTypeConverter(method.getReturnType());
        return typeConverter.convert(result);
	}

	/**
	 * XmlRpcClientを生成・取得します。
	 * @return XmlRpcClient
	 */
	private synchronized XmlRpcClient getClient() {
		if(clientCache == null) {
	        clientCache = new XmlRpcClient();
	        final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		    config.setServerURL(getBaseURL());
		    config.setEnabledForExtensions(true);
		    clientCache.setConfig(config);
			clientCache.setTypeFactory(new BeanTypeFactory(clientCache));
		}
		return clientCache;
	}
}
