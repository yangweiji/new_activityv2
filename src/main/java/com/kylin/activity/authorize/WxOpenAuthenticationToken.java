package com.kylin.activity.authorize;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * Created by 9kylin on 2018-07-05.
 */
public class WxOpenAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private static final Logger logger = LoggerFactory.getLogger(WxOpenAuthenticationToken.class.getName());

    private final Object principal;

    @Getter
    @Setter
    private String openId;

    public WxOpenAuthenticationToken(String unionId) {
        super(null);
        this.principal = unionId;
        this.setAuthenticated(false);
        logger.info("WxOpenAuthenticationToken setAuthenticated ->false loading ...");
    }

    public WxOpenAuthenticationToken(String unionId, String openId) {
        super(null);
        this.principal = unionId;
        this.openId = openId;
        this.setAuthenticated(false);
        logger.info("WxOpenAuthenticationToken setAuthenticated ->false loading ...");
    }

    public WxOpenAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
        logger.info("WxOpenAuthenticationToken setAuthenticated ->true loading ...");
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
