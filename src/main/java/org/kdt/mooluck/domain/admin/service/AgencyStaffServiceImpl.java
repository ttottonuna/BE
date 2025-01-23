package org.kdt.mooluck.domain.admin.service;

import jakarta.annotation.PostConstruct;
import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;
import org.kdt.mooluck.domain.admin.dto.ElderDTO;
import org.kdt.mooluck.domain.admin.dto.TableResponseDTO;
import org.kdt.mooluck.domain.admin.mapper.AgencyStaffMapper;
import org.kdt.mooluck.domain.admin.mapper.AgencyTableMapper;
import org.kdt.mooluck.security.JwtTokenProvider;
import org.kdt.mooluck.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AgencyStaffServiceImpl implements AgencyStaffService {

    private static final Logger logger = LoggerFactory.getLogger(AgencyStaffServiceImpl.class);

    private final AgencyStaffMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private  final AgencyTableMapper agencyTableMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private final Set<String> tokenBlacklist = new HashSet<>();

    public AgencyStaffServiceImpl(AgencyStaffMapper mapper, PasswordEncoder passwordEncoder, AgencyTableMapper agencyTableMapper, JwtTokenProvider jwtTokenProvider) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.agencyTableMapper = agencyTableMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    @Override
    public void register(AgencyStaffDTO staff) {
        staff.validate();

        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        mapper.insertStaff(staff);

        logger.info("회원가입 성공: {}", staff.getStaff_email());
    }


    @Override
    public List<AgencyTableDTO> getMembersByStaffId(Long staffId) {
        return agencyTableMapper.getMembersByStaffId(staffId);
    }



    @Override
    public String login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new CustomException("Email과 Password는 필수 입력값입니다.");
        }

        AgencyStaffDTO staff = mapper.findByEmail(email);
        if (staff == null || !passwordEncoder.matches(password, staff.getPassword())) {
            logger.warn("로그인 실패: 이메일 또는 비밀번호가 올바르지 않습니다. email={}", email);
            throw new CustomException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateAdminAccessToken(email, staff.getStaff_id());
        logger.info("로그인 성공: {}", email);

        return token;
    }

    @Override
    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            throw new CustomException("토큰이 유효하지 않습니다.");
        }

        tokenBlacklist.add(token);
        logger.info("로그아웃 처리됨. 토큰 블랙리스트에 추가: {}", token);
    }

    public boolean isTokenValid(String token) {
        if (tokenBlacklist.contains(token)) {
            logger.warn("유효하지 않은 토큰입니다 (블랙리스트 포함): {}", token);
            return false;
        }
        return jwtTokenProvider.validateToken(token, false); // Add `false` for AccessToken validation

    }

    @PostConstruct
    public void initializeBlacklist() {
        logger.info("토큰 블랙리스트 초기화 완료.");
    }

    @Override
    public void registerElder(ElderDTO elder) {
        elder.setElderPwd(passwordEncoder.encode(elder.getElderPwd()));
        mapper.insertElder(elder);
        logger.info("노인 등록 성공: {}", elder.getElderName());
    }

    // elder 정보 수정 (관리자 권한)
    @Override
    public void updateElder(ElderDTO elder) {
        if (elder == null || elder.getElderId() == null) {
            throw new IllegalArgumentException("수정할 Elder 정보가 유효하지 않습니다.");
        }

        try {
            mapper.updateElder(elder);
        } catch (Exception e) {
            throw new RuntimeException("Elder 정보 수정 중 오류 발생", e);
        }
    }

    // elder 삭제 (관리자 권한)
    @Override
    public void deleteElder(Long elderId) {
        if (elderId == null) {
            throw new IllegalArgumentException("삭제할 Elder ID가 유효하지 않습니다.");
        }

        try {
            mapper.deleteElder(elderId);
        } catch (Exception e) {
            throw new RuntimeException("Elder 삭제 중 오류 발생", e);
        }
    }
}
