package org.seasar.remoting.xmlrpc.converter;

import org.apache.xmlrpc.common.TypeConverter;

class StringArrayConverter implements TypeConverter {

	public boolean isConvertable(Object pObject) {
		if (pObject == null) {
			return true;
		} else if (!pObject.getClass().isArray()) {
			return false;
		} else {
			Object[] array = (Object[]) pObject;
			for (int i = 0; i < array.length; i++) {
				if (!(array[i] instanceof String)) {
					return false;
				}
			}
			return true;
		}
	}

	public Object convert(Object pObject) {
		if (pObject == null) {
			return null;
		}
		if (Object[].class.isAssignableFrom(pObject.getClass())) {
			Object[] src = (Object[]) pObject;
			String[] dest = new String[src.length];
			for (int i = 0; i < src.length; i++) {
				if (!(src[i] instanceof String)) {
					throw new IllegalArgumentException("type is mismatch : array index=" + i);
				}
				dest[i] = (String) src[i];
			}
			return dest;
		} else if (String[].class.isAssignableFrom(pObject.getClass())) {
			return pObject;
		}
		throw new IllegalArgumentException("type is mismatch : "
				+ pObject.getClass());
	}

	public Object backConvert(Object pObject) {
		throw new UnsupportedOperationException();
	}
}