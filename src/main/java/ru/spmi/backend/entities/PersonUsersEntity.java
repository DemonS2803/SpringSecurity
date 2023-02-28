package ru.spmi.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "person_users", schema = "public", catalog = "postgres")
public class PersonUsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "person_user_id")
    private long personUserId;
    @Basic
    @Column(name = "person_id")
    private long personId;
    @Basic
    @Column(name = "role_id")
    private Long roleId;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "active")
    private int active;

    public long getPersonUserId() {
        return personUserId;
    }

    public void setPersonUserId(long personUserId) {
        this.personUserId = personUserId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        PersonUsersEntity that = (PersonUsersEntity) o;
        return personUserId == that.personUserId && personId == that.personId && active == that.active && Objects.equals(roleId, that.roleId) && Objects.equals(userName, that.userName) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUserId, personId, roleId, userName, userId, active);
    }
}
