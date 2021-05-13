package com.canteen.util;

import com.canteen.shiro.AccountProfile;

import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
    public static AccountProfile getProfile(){ return (AccountProfile) SecurityUtils.getSubject().getPrincipal(); }
}
