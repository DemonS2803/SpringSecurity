package ru.spmi.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "d_roles", schema = "public", catalog = "postgres")
public class DRolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    private long roleId;
    @Basic
    @Column(name = "role_code")
    private String roleCode;
    @Basic
    @Column(name = "role_name")
    private String roleName;
    @Basic
    @Column(name = "active")
    private int active;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DRolesEntity that = (DRolesEntity) o;
        return roleId == that.roleId && active == that.active && Objects.equals(roleCode, that.roleCode) && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleCode, roleName, active);
    }
}
