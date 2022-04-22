package com.wanowanconsult.ecowalk.framework.manager

interface BasePermissionManager {
    var permissionsName: List<String>
    fun setPermissionStatus(permissions:  Map<String, @JvmSuppressWildcards Boolean>)
    fun requestPermissions()
}