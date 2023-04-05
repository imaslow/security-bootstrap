package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {
    public List<Role> getRolesList();
    public Role getRoleById(Long id);
}
