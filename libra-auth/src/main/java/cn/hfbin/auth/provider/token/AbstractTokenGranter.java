package cn.hfbin.auth.provider.token;

import cn.hfbin.auth.enums.AuthExceptionCode;
import cn.hfbin.auth.provider.TokenGranterStrategy;
import cn.hfbin.auth.constant.AuthRedisKeyConstant;
import cn.hfbin.common.core.constant.ConfigValueConstant;
import cn.hfbin.common.core.constant.SpecialCharacterPool;
import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.enums.IdentityEnum;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.common.core.utils.EnumUtil;
import cn.hfbin.common.core.utils.FeignResponseUtil;
import cn.hfbin.common.redis.util.RedisUtil;
import cn.hfbin.common.token.AuthUtil;
import cn.hfbin.common.token.model.AuthUserInfo;
import cn.hfbin.common.token.model.JwtUserInfo;
import cn.hfbin.tenant.client.TrTenantServiceClient;
import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.ucpm.client.AccountServiceClient;
import cn.hfbin.ucpm.client.ClientServiceClient;
import cn.hfbin.ucpm.entity.Client;
import cn.hfbin.ucpm.enums.AccountStatusEnum;
import cn.hfbin.ucpm.params.AccountIdentityQueryParams;
import cn.hfbin.ucpm.params.LoginParams;
import cn.hfbin.ucpm.vo.AccountVo;
import cn.hfbin.ucpm.vo.IdentityInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author: huangfubin
 * @Description: AbstractTokenGranter 类
 * @Date: 2021/7/29
 */
@Slf4j
@Component
public abstract class AbstractTokenGranter implements TokenGranterStrategy {

    /**
     * 过期时间 12h 单位：s
     * 默认：12 * 60 * 60s
     */
    @Value("${" + ConfigValueConstant.JWT_EXPIRE + ":43200}")
    private Long expire;

    @Autowired
    protected AccountServiceClient accountServiceClient;
    @Autowired
    protected ClientServiceClient clientServiceClient;
    @Autowired
    protected TrTenantServiceClient trTenantServiceClient;
    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    protected AuthUtil authUtil;

    public void publicCheck(){
        // 校验身份类型是在定义范围内
        if(!EnumUtil.isInclude(IdentityEnum.class, SpringContextUtils.getIdentityType())){
            throw new LibraException(AuthExceptionCode.IDENTITY_INEXISTENCE);
        }
        // 1、校验当前客户端请求过来是否可用
        this.clientCheck();
        // 2、校验当前租户是否可用
        this.tenantCheck();
    }

    /**
     *
     * @description 查询账号并校验账号相关信息
     * @param loginParams
     * @author huangfubin
     * @date 2021/9/30
     * @return cn.hfbin.ucpm.vo.AccountVo
     */
    protected AccountVo checkAccount(LoginParams loginParams){
        AccountIdentityQueryParams params = new AccountIdentityQueryParams();
        params.setUsername(loginParams.getUsername());
        params.setMobile(loginParams.getMobile());
        AccountVo accountIdentityVo = FeignResponseUtil.get(accountServiceClient.selectAccount(params));
        // 账号不存在
        Optional.ofNullable(accountIdentityVo).orElseThrow(()->new LibraException(AuthExceptionCode.ACCOUNT_IDENTITY_IS_NULL));
        // 账号被禁用
        if(accountIdentityVo.getAccountStatus().equals(AccountStatusEnum.DISABLE.getCode())){
            throw new LibraException(AuthExceptionCode.ACCOUNT_DISABLE);
        }
        return accountIdentityVo;
    }

    /**
     *
     * @description 根据账号信息、身份信息创建token
     * @param accountVo
     * @param identityInfoVo
     * @author huangfubin
     * @date 2021/9/30
     * @return cn.hfbin.common.core.jwt.model.AuthUserInfo
     */
    protected AuthUserInfo createToken(AccountVo accountVo, IdentityInfoVo identityInfoVo) {
        // 5、生成token
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setIdentityId(identityInfoVo.getId());
        jwtUserInfo.setDeptCode(identityInfoVo.getDeptCode());
        jwtUserInfo.setDeptId(identityInfoVo.getDeptId());
        jwtUserInfo.setDataScope(1);
        jwtUserInfo.setTenantCode(identityInfoVo.getTenantCode());
        jwtUserInfo.setAccountId(accountVo.getAccountId());
        jwtUserInfo.setUsername(accountVo.getUsername());
        jwtUserInfo.setIdentityType(SpringContextUtils.getIdentityType());
        AuthUserInfo authUserInfo = authUtil.createToken(jwtUserInfo);
        // key + client + identityId
        redisUtil.strSet(AuthRedisKeyConstant.USER_INFO_KEY + SpringContextUtils.getClientCode() + SpecialCharacterPool.S_COLON + identityInfoVo.getId(), identityInfoVo, expire);
        return authUserInfo;
    }

    /**
     * 校验当前客户端请求过来是否可用
     */
    private void clientCheck(){
        Client client = FeignResponseUtil.get(clientServiceClient.selectByCode(SpringContextUtils.getClientCode()));
        Optional.ofNullable(client).orElseThrow(()->new LibraException(AuthExceptionCode.CLIENT_CODE_ERROR));
    }

    /**
     * 校验当前租户是否可用
     */
    private void tenantCheck(){
        TrTenant trTenant = FeignResponseUtil.get(trTenantServiceClient.selectByCode(SpringContextUtils.getTenantCode()));
        Optional.ofNullable(trTenant).orElseThrow(()->new LibraException(AuthExceptionCode.TENANT_CODE_ERROR));
        LocalDateTime now = LocalDateTime.now();
        if(!(trTenant.getBeginDate() != null && trTenant.getBeginDate().isBefore(now)
                && trTenant.getEndDate() != null && trTenant.getEndDate().isAfter(now))){
            throw new LibraException(AuthExceptionCode.TENANT_EXPIRED);
        }
    }
}
