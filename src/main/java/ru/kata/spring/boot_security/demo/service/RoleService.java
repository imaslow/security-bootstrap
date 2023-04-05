package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    public Role getRoleById(Long id);
    public List<Role> getRolesList();
}
