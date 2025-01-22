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
        String elderId = elderMapper.getMemberByMemberId(elderDTO);
        if (elderId == null) {
            throw new ElderNotFoundException("Elder ID not found for account: " + elderDTO.getElderAccount());
        }
        return elderId;
    }

    @Override
    public boolean validateMember(ElderDTO elderDTO) {
        String storedPassword = elderMapper.getPasswordByAccount(elderDTO.getElderAccount());
        return storedPassword != null && storedPassword.equals(elderDTO.getElderPwd());
    }
}
