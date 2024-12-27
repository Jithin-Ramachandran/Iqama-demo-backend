package com.iqama.demo.service;

import com.iqama.demo.entity.EmployeeRole;
import com.iqama.demo.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRoleService {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    //crud operations for EmployeeRole

    public EmployeeRole createRole(EmployeeRole role) {
        return employeeRoleRepository.save(role);
    }

    public List<EmployeeRole> getAllRoles() {
        return employeeRoleRepository.findAll();
    }

    public Optional<EmployeeRole> getRoleById(Long id) {
        return employeeRoleRepository.findById(id);
    }

    public  EmployeeRole updateRole(Long id, EmployeeRole updatedRole) {
        updatedRole.setId(id);
        return employeeRoleRepository.save(updatedRole);
    }

    public void deleteRoleById(Long id) {
        employeeRoleRepository.deleteById(id);
    }
}
