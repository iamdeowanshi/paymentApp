package com.tecsol.batua.user.module.common.callback;

/**
 * @author Farhan Ali
 */
public interface PermissionCallback {

    void onPermissionGranted(String[] grantedPermissions);

    void onPermissionDenied(String[] deniedPermissions);

    void onPermissionBlocked(String[] blockedPermissions);

}
