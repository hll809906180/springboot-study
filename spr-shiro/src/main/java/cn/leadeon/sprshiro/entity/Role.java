package cn.leadeon.sprshiro.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: he.l
 * @create: 2019-05-22 15:51
 **/
public class Role {
    private  Integer rid;
    private  String name;
    private Set<Permission> permissions = new HashSet<>();

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
