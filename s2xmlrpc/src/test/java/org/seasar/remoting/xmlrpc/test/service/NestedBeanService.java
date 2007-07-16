package org.seasar.remoting.xmlrpc.test.service;

import org.seasar.remoting.xmlrpc.test.dto.EmployeeDto;

public interface NestedBeanService {

    EmployeeDto find(Integer empNo);

    EmployeeDto[] findAll();

}
