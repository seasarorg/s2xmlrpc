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

import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.seasar.remoting.xmlrpc.serializer.BeanSerializer;
import org.xml.sax.SAXException;

    public class BeanTypeFactory extends TypeFactoryImpl {

    	public BeanTypeFactory(XmlRpcController pController) {
            super(pController);
        }

        public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException {
            TypeSerializer serializer = super.getSerializer(pConfig, pObject);
            if (serializer == null) {
                return new BeanSerializer(getController().getTypeFactory(), pConfig);
            } else {
                return serializer;
            }
        }
    }