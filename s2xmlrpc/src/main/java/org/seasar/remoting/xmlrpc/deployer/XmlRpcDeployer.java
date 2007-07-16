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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.MetaDef;
import org.seasar.framework.container.MetaDefAware;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.log.Logger;
import org.seasar.remoting.xmlrpc.S2XmlRpcConstants;
import org.seasar.remoting.xmlrpc.util.S2XmlRpcUtil;

public class XmlRpcDeployer {

	protected static final Pattern META_NAME_PATTERN = Pattern.compile("xmlrpc");

	private static Logger logger = Logger.getLogger(XmlRpcDeployer.class);
	
    private S2Container container;

	public void deploy(PropertyHandlerMapping phm) {
		// TODO PropertyHandlerMappingを継承したクラスを使用するようにする。あるいは、別の実装
		logger.log("DXRP0002", null);
		forEach(this.container.getRoot(), phm);
		logger.log("DXRP0003", null);
	}

	protected void forEach(final S2Container container, PropertyHandlerMapping phm) {
		final int componentDefSize = container.getComponentDefSize();
		for (int i = 0; i < componentDefSize; ++i) {
			process(container.getComponentDef(i), phm);
		}

		final int childContainerSize = container.getChildSize();
		for (int i = 0; i < childContainerSize; ++i) {
			forEach(container.getChild(i), phm);
		}
	}

	protected void process(final ComponentDef componentDef, PropertyHandlerMapping phm) {
		final MetaDef serviceMetaDef = getMetaDef(componentDef,
				S2XmlRpcConstants.META_SERVICE);
		if (serviceMetaDef != null) {
			try {
				String remoteName = S2XmlRpcUtil.getRemoteName(componentDef);
				phm.addHandler(remoteName, componentDef.getConcreteClass());
				if (logger.isDebugEnabled()) {
					logger.log("DXRP0001", new Object[] {
							remoteName, 
							componentDef.getConcreteClass().getName()});
				}
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}
	}

	protected MetaDef getMetaDef(final MetaDefAware metaDefSupport,
			final String localName) {
		try {
			for (int i = 0; i < metaDefSupport.getMetaDefSize(); ++i) {
				final MetaDef metaDef = metaDefSupport.getMetaDef(i);
				if (getLocalName(metaDef)) {
					return metaDef;
				}
			}
		} catch (UnsupportedOperationException ex) {
		}
		return null;
	}

	protected boolean getLocalName(final MetaDef metaDef) {
		final Matcher matcher = META_NAME_PATTERN.matcher(metaDef.getName());
		return matcher.matches();
	}

	protected MetaDef[] getMetaDefs(final MetaDefAware metaDefSupport,
			final String localName) {
		final List result = new ArrayList();
		for (int i = 0; i < metaDefSupport.getMetaDefSize(); ++i) {
			final MetaDef metaDef = metaDefSupport.getMetaDef(i);
			if (getLocalName(metaDef)) {
				result.add(metaDef);
			}
		}
		return (MetaDef[]) result.toArray(new MetaDef[result.size()]);
	}

	public S2Container getContainer() {
		return container;
	}

	public void setContainer(S2Container container) {
		this.container = container;
	}

}
