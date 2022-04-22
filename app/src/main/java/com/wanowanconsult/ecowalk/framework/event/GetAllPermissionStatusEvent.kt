package com.wanowanconsult.ecowalk.framework.event

import com.wanowanconsult.ecowalk.framework.manager.PermissionStatus

class GetAllPermissionStatusEvent(val permissions: MutableMap<String, PermissionStatus>)