package com.alexpauv.pokemon.dto.user;

import com.alexpauv.pokemon.model.user.UserRoleChangeType;

public class UserAddOrRemoveRoleRequest {
    private String roleUuid;

    private UserRoleChangeType changeType;

    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid;
    }

    public UserRoleChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(UserRoleChangeType changeType) {
        this.changeType = changeType;
    }
}
