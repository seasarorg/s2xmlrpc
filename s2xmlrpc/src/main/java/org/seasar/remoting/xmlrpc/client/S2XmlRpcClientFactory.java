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
import org.apache.xmlrpc.common.TypeConverterFactoryImpl;


/**
 */
public class S2XmlRpcClientFactory {
    private final XmlRpcClient client;
    private final TypeConverterFactory typeConverterFactory;
    private boolean objectMethodLocal;

    public S2XmlRpcClientFactory(XmlRpcClient pClient, TypeConverterFactory pTypeConverterFactory) {
        typeConverterFactory = pTypeConverterFactory;
        client = pClient;
    }

    public S2XmlRpcClientFactory(XmlRpcClient pClient) {
        this(pClient, new TypeConverterFactoryImpl());
    }

    public XmlRpcClient getClient() {
        return client;
    }

    public boolean isObjectMethodLocal() {
        return objectMethodLocal;
    }

    public void setObjectMethodLocal(boolean pObjectMethodLocal) {
        objectMethodLocal = pObjectMethodLocal;
    }

    public Object newInstance(Class pClass, String name) {
        return newInstance(Thread.currentThread().getContextClassLoader(), pClass, name);
    }

    public Object newInstance(ClassLoader pClassLoader, final Class pClass, final String name) {
        return Proxy.newProxyInstance(pClassLoader, new Class[]{pClass}, new InvocationHandler(){
            public Object invoke(Object pProxy, Method pMethod, Object[] pArgs) throws Throwable {
                if (isObjectMethodLocal()  &&  pMethod.getDeclaringClass().equals(Object.class)) {
                    return pMethod.invoke(pProxy, pArgs);
                }
                String methodName = name + "." + pMethod.getName();
                Object result = client.execute(methodName, pArgs);
                TypeConverter typeConverter = typeConverterFactory.getTypeConverter(pMethod.getReturnType());
                return typeConverter.convert(result);
            }
        });
    }
}
