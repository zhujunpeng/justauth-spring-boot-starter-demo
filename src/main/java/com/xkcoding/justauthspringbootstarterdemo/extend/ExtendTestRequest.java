package com.xkcoding.justauthspringbootstarterdemo.extend;

import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthDefaultRequest;

/**
 * <p>
 * 测试用自定义扩展的第三方request
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/10/9 14:19
 */
public class ExtendTestRequest extends AuthDefaultRequest {

    public ExtendTestRequest(AuthConfig config) {
        super(config, ExtendSource.TEST);
    }

    public ExtendTestRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, ExtendSource.TEST, authStateCache);
    }

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return token
     * @see AuthDefaultRequest#authorize()
     * @see AuthDefaultRequest#authorize(String)
     */
    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        return AuthToken.builder().openId("openId").expireIn(1000).idToken("idToken").scope("scope").refreshToken("refreshToken").accessToken("accessToken").code("code").build();
    }

    /**
     * 使用token换取用户信息
     *
     * @param authToken token信息
     * @return 用户信息
     * @see AuthDefaultRequest#getAccessToken(AuthCallback)
     */
    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        return AuthUser.builder().username("test").nickname("test").gender(AuthUserGender.MALE).token(authToken).source(this.source.toString()).build();
    }

    /**
     * 撤销授权
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse revoke(AuthToken authToken) {
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).msg(AuthResponseStatus.SUCCESS.getMsg()).build();
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse refresh(AuthToken authToken) {
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(AuthToken.builder().openId("openId").expireIn(1000).idToken("idToken").scope("scope").refreshToken("refreshToken").accessToken("accessToken").code("code").build()).build();
    }
}
