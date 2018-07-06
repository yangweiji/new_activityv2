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
public class MobileCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private static final Logger logger = LoggerFactory.getLogger(MobileCodeAuthenticationToken.class.getName());

    private final Object principal;

    @Getter
    @Setter
    private String verCode;

    public MobileCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        this.setAuthenticated(false);
        logger.info("MobileCodeAuthenticationToken setAuthenticated ->false loading ...");
    }

    public MobileCodeAuthenticationToken(String mobile, String code) {
        super(null);
        this.principal = mobile;
        this.verCode = code;
        this.setAuthenticated(false);
        logger.info("MobileCodeAuthenticationToken setAuthenticated ->false loading ...");
    }

    public MobileCodeAuthenticationToken(Object principal,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
        logger.info("MobileCodeAuthenticationToken setAuthenticated ->true loading ...");
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
