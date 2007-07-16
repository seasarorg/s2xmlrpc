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
package org.seasar.remoting.xmlrpc.util;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ClassUtil;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;

public class S2XmlRpcUtil {
    public static String getRemoteName(ComponentDef componentDef) {
        final String componentName = componentDef.getComponentName();
        if (componentName != null) {
            return componentName;
        }

        final Class componentClass = componentDef.getComponentClass();
        if (componentClass != null) {
            return ClassUtil.getShortClassName(componentClass);
        }
        throw new S2XmlRpcException("EXRP2001");
    }
}
