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
package org.seasar.remoting.xmlrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.common.TypeConverter;
import org.apache.xmlrpc.common.TypeConverterFactory;
import org.seasar.remoting.xmlrpc.converter.BeanTypeConverterFactory;

/**
 * S2Containerに対応しRPCを呼び出すプロキシイを生成するファクトリクラスです。
 * @author agata
 */
public class S2XmlRpcClientFactory {
	/**
	 * XmlRpcClient
	 */
    private final XmlRpcClient client;

    /**
     * TypeConverterFactory
     */
    private final TypeConverterFactory typeConverterFactory;
    
    /**
     * オブジェクトがローカルで実行されるかどうかどうか。trueならローカル
     */
    private boolean objectMethodLocal;
    
    /**
     * コンストラクタ
     * @param client XmlRpcClient
     * @param typeConverterFactory TypeConverterFactory
     */
    public S2XmlRpcClientFactory(final XmlRpcClient client, 
    		final TypeConverterFactory typeConverterFactory) {
        this.client = client;
        this.typeConverterFactory = typeConverterFactory;
    }
    
    /**
     * コンストラクタ。
     * @param client XmlRpcClient
     */
    public S2XmlRpcClientFactory(final XmlRpcClient client) {
        this(client, BeanTypeConverterFactory.getFactory());
    }
    
    /**
     * XmlRpcClientを取得します。
     * @return XmlRpcClient
     */
    public XmlRpcClient getClient() {
        return client;
    }
    
    /**
     * オブジェクトがローカルかどうかを取得します。
     * @return trueならローカルで実行
     */
    public boolean isObjectMethodLocal() {
        return objectMethodLocal;
    }
    
    /**
     * オブジェクトがローカルかどうかを設定します。
     * @param objectMethodLocal trueならローカルで実行
     */
    public void setObjectMethodLocal(final boolean objectMethodLocal) {
        this.objectMethodLocal = objectMethodLocal;
    }
    
    /**
     * RPCを呼び出すプロキシを生成します。
     * @param clazz コンポーネントインターフェイスのクラス
     * @param name コンポーネント名
     * @return　コンポーネントインターフェイスを実装するプロキシオブジェクト
     */
    public Object newInstance(final Class clazz, final String name) {
        return newInstance(Thread.currentThread().getContextClassLoader(), clazz, name);
    }
    
    /**
     * RPCを呼び出すプロキシを生成します。
     * @param classLoader クラスローダー
     * @param clazz コンポーネントインターフェイスのクラス
     * @param name コンポーネント名
     * @return　コンポーネントインターフェイスを実装するプロキシオブジェクト
     */
    public Object newInstance(final ClassLoader classLoader, final Class pClass, final String name) {
        return Proxy.newProxyInstance(classLoader, new Class[]{pClass}, new InvocationHandler(){
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                if (isObjectMethodLocal()  &&  method.getDeclaringClass().equals(Object.class)) {
                    return method.invoke(proxy, args);
                }
                final String methodName = name + "." + method.getName();
                final Object result = client.execute(methodName, args);
                final TypeConverter typeConverter = typeConverterFactory.getTypeConverter(method.getReturnType());
                return typeConverter.convert(result);
            }
        });
    }
}
