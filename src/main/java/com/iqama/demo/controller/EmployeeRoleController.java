package com.iqama.demo.controller;

import com.iqama.demo.entity.EmployeeRole;
import com.iqama.demo.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class EmployeeRoleController {

    @Autowired
    private EmployeeRoleService employeeRoleService;

    //crud endpoints for EmployeeRole

    @PostMapping
    public ResponseEntity<EmployeeRole> createRole(@RequestBody EmployeeRole role) {
        EmployeeRole savedRole = employeeRoleService.createRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeRole>> getAllRoles() {
        List<EmployeeRole> roles = employeeRoleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRole> getRoleById(@PathVariable Long id) {
        EmployeeRole role = employeeRoleService.getRoleById(id).orElse(null);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRole> updateRole(@PathVariable Long id, @RequestBody EmployeeRole role) {
        EmployeeRole updateRole = employeeRoleService.updateRole(id, role);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        employeeRoleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully.");
    }
}
