package com.winwintek.web.backend.api.config.security.service;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.system.UserRoleEntity;
import com.winwintek.web.backend.api.repository.system.ISregRepository;
import com.winwintek.web.backend.api.repository.system.IUserRepository;
import com.winwintek.web.backend.api.repository.system.IUserRoleRepository;
import com.winwintek.web.backend.api.repository.system.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.*;

@Service ("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class FormUserDetailsService implements UserDetailsService {

    private static final SecureRandom random = new SecureRandom();

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ISregRepository iSregRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IUserRoleRepository iUserRoleRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static String nextToken() {
        return new BigInteger(130, random).toString(32); // 130 bits strong
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username, String loginDiv) {
        UserDTO userDTO = new UserDTO();
        List<Map<String, Object>> userDataList = new ArrayList<>();

        /* ----- START -----
            Used for the common login form
            공통 로그인 양식에 사용됨
         */
        if (USER_DIV_ADM_CHECK.equals(loginDiv)) {
//            userDataList = staffRepository.selectByUserIdFromLINC(username.trim(), "LINC");
            userDataList = iUserRepository.selectByUserId(username, USER_DIV_ADM);
        } else if (USER_DIV_STD_CHECK.equals(loginDiv)) {
            userDataList = iSregRepository.selectByUserId(username);
        } else if (loginDiv.equals(USER_DIV_PROF_CHECK)) {
            userDataList = staffRepository.selectByUserId(username);
        } else if (loginDiv.equals(USER_DIV_OUT_CHECK)) {
            userDataList = iUserRepository.selectByUserIdCorm(username, USER_DIV_OUT);
        }
        /* ----- END -----
            Used for the common login form
            공통 로그인 양식에 사용됨
         */

        /* ----- START -----
            Used for cross-domain login with token. Specifically, it is for logging into the admin domain.
            토큰을 사용한 교차 도메인 로그인에 사용됨. 구체적으로는 관리자 도메인에 로그인하는 것입니다.
        */

        if (USER_DIV_ADM_TOKEN.equals(loginDiv)) {
//            userDataList = staffRepository.selectByUserIdFromLINC(username.trim(), "LINC");
            String tUserName = redisTemplate.opsForValue().get(username);
            redisTemplate.delete(username);
            userDataList = iUserRepository.selectByUserId(tUserName, USER_DIV_ADM);
        } else if (loginDiv.equals(USER_DIV_OUT_TOKEN)) {
            String tUserName = redisTemplate.opsForValue().get(username);
            redisTemplate.delete(username);
            userDataList = iUserRepository.selectByUserIdCorm(tUserName, USER_DIV_OUT);
        }
        /* ----- END -----
            Used for cross-domain login with token. Specifically, it is for logging into the admin domain.
            토큰을 사용한 교차 도메인 로그인에 사용됨. 구체적으로는 관리자 도메인에 로그인하는 것입니다.
        */

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm");
        String formattedNow = now.format(formatter);

        if (userDataList.isEmpty()) {
            throw new AuthenticationServiceException("save user Entity fail : userId : " + username);
        } else {
            if (null == userDataList.get(0).get("userRoles")) {
                userDTO.setUserId(String.valueOf(userDataList.get(0).get("userId")));
                userDTO.setUserName(String.valueOf(userDataList.get(0).get("userName")));
                userDTO.setUserDiv(String.valueOf(userDataList.get(0).get("userDiv")));
                userDTO.setPassword(String.valueOf(userDataList.get(0).get("password")));
                userDTO.setColgCd(String.valueOf(userDataList.get(0).get("colgCd")));
                userDTO.setColgCdNm(String.valueOf(userDataList.get(0).get("colgCdNm")));
                userDTO.setSustCd(String.valueOf(userDataList.get(0).get("sustCd")));
                userDTO.setSustCdNm(String.valueOf(userDataList.get(0).get("sustCdNm")));
                userDTO.setMjCd(String.valueOf(userDataList.get(0).get("mjCd")));
                userDTO.setMjCdNm(String.valueOf(userDataList.get(0).get("mjCdNm")));
                userDTO.setShyrCd(String.valueOf(userDataList.get(0).get("shyrCd")));
                userDTO.setShyrCdNm(String.valueOf(userDataList.get(0).get("shyrCdNm")));
                userDTO.setShregSt(String.valueOf(userDataList.get(0).get("shregSt")));
                userDTO.setShregStNm(String.valueOf(userDataList.get(0).get("shregStNm")));
                userDTO.setGenFg(String.valueOf(userDataList.get(0).get("genFg")));
                userDTO.setGenFgNm(String.valueOf(userDataList.get(0).get("genFgNm")));
                userDTO.setBirthDt(String.valueOf(userDataList.get(0).get("birthDt")));
                userDTO.setTelNo(String.valueOf(userDataList.get(0).get("telNo")));
                userDTO.setEmail(String.valueOf(userDataList.get(0).get("email")));
                userDTO.setPhone(String.valueOf(userDataList.get(0).get("phone")));
                userDTO.setFullAddress(String.valueOf(userDataList.get(0).get("fullAddress")));
                userDTO.setDivision(String.valueOf(userDataList.get(0).get("division")));
                userDTO.setPolicy(String.valueOf(userDataList.get(0).get("policy")));
                userDTO.setLastLoginDate(formattedNow);
                userDTO.setLoginDivCheck(loginDiv);
                this.genesisClientRoleSet(userDTO);
            } else {
                userDTO.setUserId(String.valueOf(userDataList.get(0).get("userId")));
                userDTO.setUserName(String.valueOf(userDataList.get(0).get("userName")));
                userDTO.setUserDiv(String.valueOf(userDataList.get(0).get("userDiv")));
                userDTO.setUserRoles(String.valueOf(userDataList.get(0).get("userRoles")));
                userDTO.setPassword(String.valueOf(userDataList.get(0).get("password")));
                userDTO.setColgCd(String.valueOf(userDataList.get(0).get("colgCd")));
                userDTO.setColgCdNm(String.valueOf(userDataList.get(0).get("colgCdNm")));
                userDTO.setSustCd(String.valueOf(userDataList.get(0).get("sustCd")));
                userDTO.setSustCdNm(String.valueOf(userDataList.get(0).get("sustCdNm")));
                userDTO.setMjCd(String.valueOf(userDataList.get(0).get("mjCd")));
                userDTO.setMjCdNm(String.valueOf(userDataList.get(0).get("mjCdNm")));
                userDTO.setShyrCd(String.valueOf(userDataList.get(0).get("shyrCd")));
                userDTO.setShyrCdNm(String.valueOf(userDataList.get(0).get("shyrCdNm")));
                userDTO.setShregSt(String.valueOf(userDataList.get(0).get("shregSt")));
                userDTO.setShregStNm(String.valueOf(userDataList.get(0).get("shregStNm")));
                userDTO.setGenFg(String.valueOf(userDataList.get(0).get("genFg")));
                userDTO.setGenFgNm(String.valueOf(userDataList.get(0).get("genFgNm")));
                userDTO.setBirthDt(String.valueOf(userDataList.get(0).get("birthDt")));
                userDTO.setTelNo(String.valueOf(userDataList.get(0).get("telNo")));
                userDTO.setEmail(String.valueOf(userDataList.get(0).get("email")));
                userDTO.setPhone(String.valueOf(userDataList.get(0).get("phone")));
                userDTO.setFullAddress(String.valueOf(userDataList.get(0).get("fullAddress")));
                userDTO.setDivision(String.valueOf(userDataList.get(0).get("division")));
                userDTO.setPublicInfo(String.valueOf(userDataList.get(0).get("publicInfo")));
                userDTO.setPolicy(String.valueOf(userDataList.get(0).get("policy")));
                userDTO.setLoginDivCheck(loginDiv);
                userDTO.setLastLoginDate(formattedNow);
            }
        }

        return new AccountContext(userDTO, setGrantedAuthority(userDTO.getUserRoles()));
    }

    public List<GrantedAuthority> setGrantedAuthority(String roleId) {

        String resetValue = String.join(",",
                Arrays.stream(roleId.split(",")).map(s -> "ROLE_" + s).toArray(String[]::new));

        List<GrantedAuthority> roles = Arrays.stream(resetValue.split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return roles;
    }

    public UserDTO genesisClientRoleSet(UserDTO userDTO) {
        try {
            //out은 제외 > 획원가입할떄 이미 넣어준다
            String roleValue = "0";

            if ("ADM".equals(userDTO.getUserDiv())) {
                roleValue = "1";
            } else if ("STD".equals(userDTO.getUserDiv())) {
                roleValue = "3";
            } else if (USER_DIV_OUT_CHECK.equals(userDTO.getUserDiv())) {
                roleValue = "4";
            } else {
                //초기에 안걸리면 무조건 제일아래인 학생으로 본다
                roleValue = "2";
            }

            userDTO.setUserRoles(roleValue);
            userDTO.setPublicInfo(STATE_N);

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userDTO.getUserId());
            userRoleEntity.setRlId(roleValue);
            userRoleEntity.setUseYn(Constants.STATE_Y);
            UserRoleEntity saveUserRoleEntity = iUserRoleRepository.save(userRoleEntity);
            if (null == saveUserRoleEntity.getRlId()) {
                throw new RuntimeException("save userRole Entity fail : userId : " + userDTO.getUserId());
            }

        } catch (Exception e) {
            userDTO.setUserRoles(null);
            log.error(e.toString());
        }

        return userDTO;
    }

    @Transactional
    public String switchToUser(String userUsername, String password, String loginDiv) throws
            AuthenticationServiceException {

        UserDetails userDetails = loadUserByUsername(userUsername, loginDiv);
        AccountContext accountContext = (AccountContext) userDetails;

        if (!passwordEncoder.matches(password, accountContext.getUserDTO().getPassword())) {
            throw new AuthenticationServiceException("Invalid password userId : " + userDetails.getUsername());
        }

        return nextToken();
    }

    @Transactional
    public Boolean loadAgreeToPolicyStudent(String username) {
        List<Map<String, Object>> userDataList = new ArrayList<>();

        userDataList = iSregRepository.selectByUserId(username);

        return StringUtils.equals(userDataList.get(0).get("publicInfo").toString(), STATE_Y);
    }

    @Transactional
    public Boolean loadAgreeCorm(String username) {
        List<Map<String, Object>> userDataList = new ArrayList<>();

        userDataList = iUserRepository.selectCormApprove(username);

        return StringUtils.equals(userDataList.get(0).get("cormStatus").toString(), CD_CORM_APPROVAL);
    }

}
