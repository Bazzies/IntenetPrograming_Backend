package com.example.internetprograming_backend.common.path;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberEndPoint implements EndPoint  {

    // security
    public static final String MEMBER_SECURITY_WILDCARD = "/member/**";

    // Base
    public static final String BASE_MEMBER_PATH = "/member";

    // URI
    public static final String MEMBER_MY_PROFILE_PATH = "/my-profile";
    public static final String MEMBER_UPDATE_MY_PROFILE_PATH = "/update/my-profile";
    public static final String MEMBER_REQUEST_ROLE_CHANGE_PATH = "/request/role-change";
}
