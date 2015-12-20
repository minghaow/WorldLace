package nanshen.service.impl;

import nanshen.utils.EncryptUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * SpringSecurity用于加密密码的服务
 *
 * @author WANG Minghao
 */
@Service
public class SpringPasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object neverUse) {
        return EncryptUtils.encodePassword(rawPass);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object neverUse) {
        return EncryptUtils.isPasswordValid(encPass, rawPass);
    }

}
