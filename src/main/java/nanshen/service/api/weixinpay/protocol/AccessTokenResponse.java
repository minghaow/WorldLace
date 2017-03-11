package nanshen.service.api.weixinpay.protocol;

/**
 * wechat pay access token response data
 *
 * @Author WANG Minghao
 */
public class AccessTokenResponse {

    private String access_token;

    private long expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

    public AccessTokenResponse(String access_token, long expires_in, String openid, String refresh_token, String scope) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.openid = openid;
        this.refresh_token = refresh_token;
        this.scope = scope;
    }

    public AccessTokenResponse() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
