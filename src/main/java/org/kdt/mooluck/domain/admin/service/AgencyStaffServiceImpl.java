package org.kdt.mooluck.domain.admin.service;

import jakarta.annotation.PostConstruct;
import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;
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
import java.util.Set;

@Service
public class AgencyStaffServiceImpl implements AgencyStaffService {

    private static final Logger logger = LoggerFactory.getLogger(AgencyStaffServiceImpl.class);

    private final AgencyStaffMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AgencyTableMapper agencyTableMapper;

    private final JwtTokenProvider jwtTokenProvider;

    // 토큰 블랙리스트 관리
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
        // 입력값 검증
        staff.validate();

        // 비밀번호 암호화
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        mapper.insertStaff(staff);

        logger.info("회원가입 성공: {}", staff.getStaff_email());
    }

    @Override
    public AgencyTableDTO getMemberById(Long elderId) {
        // DTO 생성 및 매퍼 호출
        AgencyTableDTO queryDto = new AgencyTableDTO();
        queryDto.setElderId(elderId);

        AgencyTableDTO result = agencyTableMapper.getMemberByMemberId(queryDto); // DTO를 전달
        if (result == null) {
            throw new IllegalArgumentException("No data found for Interaction ID: " + elderId);
        }
        return result;
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

        String token = jwtTokenProvider.generateToken(email);
        logger.info("로그인 성공: {}", email);

        return token;
    }

    @Override
    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            throw new CustomException("토큰이 유효하지 않습니다.");
        }

        // 토큰을 블랙리스트에 추가
        tokenBlacklist.add(token);
        logger.info("로그아웃 처리됨. 토큰 블랙리스트에 추가: {}", token);
    }

    public boolean isTokenValid(String token) {
        if (tokenBlacklist.contains(token)) {
            logger.warn("유효하지 않은 토큰입니다 (블랙리스트 포함): {}", token);
            return false;
        }
        return jwtTokenProvider.validateToken(token);
    }

    @PostConstruct
    public void initializeBlacklist() {
        // 서버 시작 시 초기화 작업
        logger.info("토큰 블랙리스트 초기화 완료.");
    }
}
