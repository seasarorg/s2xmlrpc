package org.seasar.remoting.xmlrpc.test.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.seasar.remoting.xmlrpc.test.dto.Department;
import org.seasar.remoting.xmlrpc.test.dto.EmployeeDto;
import org.seasar.remoting.xmlrpc.test.dto.Role;
import org.seasar.remoting.xmlrpc.test.service.NestedBeanService;

public class NestedBeanServiceImpl implements NestedBeanService {

    private Map empMap = new TreeMap();

    public NestedBeanServiceImpl() {
        Department dept1 = new Department();
        dept1.setDeptno(1);
        dept1.setDname("dname1");

        Department dept2 = new Department();
        dept2.setDeptno(2);
        dept2.setDname("dname2");

        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setRoleName("admin");

        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setRoleName("manager");

        Role role3 = new Role();
        role3.setRoleId(3);
        role3.setRoleName("user");

        {
            EmployeeDto emp = new EmployeeDto();
            emp.setEmpno(new Integer(1));
            emp.setEname("ename1");
            emp.setDepertment(dept1);
            emp.setRoles(new Role[] { role1, role2, role3 });
            this.empMap.put(emp.getEmpno(), emp);
        }
        {
            EmployeeDto emp = new EmployeeDto();
            emp.setEmpno(new Integer(2));
            emp.setEname("ename2");
            emp.setDepertment(dept1);
            emp.setRoles(new Role[] { role3 });
            this.empMap.put(emp.getEmpno(), emp);
        }
        {
            EmployeeDto emp = new EmployeeDto();
            emp.setEmpno(new Integer(3));
            emp.setEname("ename3");
            emp.setDepertment(dept2);
            this.empMap.put(emp.getEmpno(), emp);
        }
    }

    public EmployeeDto find(Integer empNo) {
        EmployeeDto emp = (EmployeeDto) this.empMap.get(empNo);
        return emp;
    }

    public EmployeeDto[] findAll() {
        Collection collection = this.empMap.values();
        EmployeeDto[] empArray = (EmployeeDto[]) collection.toArray(new EmployeeDto[0]);
        return empArray;
    }

}
