package org.seasar.remoting.xmlrpc.examples.ex03;

import org.seasar.remoting.xmlrpc.examples.common.dto.Employee;

public interface NestedBeanService {

    Employee find(Integer empNo);

    Employee[] findAll();

}
