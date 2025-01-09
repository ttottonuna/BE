package org.kdt.mooluck.domain.elder.service;

import org.kdt.mooluck.domain.elder.dto.ElderDTO;
import org.kdt.mooluck.domain.elder.exception.ElderNotFoundException;
import org.kdt.mooluck.domain.elder.mapper.ElderMapper;
import org.springframework.stereotype.Service;

@Service
public class ElderServiceImpl implements ElderService {

    private final ElderMapper elderMapper;

    public ElderServiceImpl(ElderMapper elderMapper) {
        this.elderMapper = elderMapper;
    }

    @Override
    public String getMemberByMemberId(ElderDTO elderDTO) {
        // MyBatis XML에 정의된 쿼리를 호출
        String elderId = elderMapper.getMemberByMemberId(elderDTO);
        if (elderId == null) {
            throw new ElderNotFoundException("Elder ID not found for account: " + elderDTO.getElderAccount());
        }
        return elderId;
    }

    @Override
    public boolean validateMember(ElderDTO elderDTO) {
        // Mapper를 통해 DB에서 저장된 비밀번호를 가져옴
        String storedPassword = elderMapper.getPasswordByAccount(elderDTO.getElderAccount());

        // 비밀번호 검증: 저장된 비밀번호와 입력된 비밀번호 비교
        return storedPassword != null && storedPassword.equals(elderDTO.getElderPwd());
    }
}
