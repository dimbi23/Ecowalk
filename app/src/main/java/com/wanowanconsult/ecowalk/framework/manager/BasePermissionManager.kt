package com.wanowanconsult.ecowalk.framework.manager

import com.wanowanconsult.ecowalk.framework.manager.PermissionStatus

interface BasePermissionManager {
    var permissionStatuses: MutableList<MutableMap<String,PermissionStatus>>
    fun setPermissionStatus(permissionName: String, newPermissionStatus: PermissionStatus){
        permissionStatuses.map { permission ->
            if(permission.containsKey(permissionName)){
                permission[permissionName] = newPermissionStatus
            }
        }
    }
}