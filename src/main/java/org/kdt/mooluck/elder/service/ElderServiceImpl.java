
package org.kdt.mooluck.elder.service;

import lombok.extern.slf4j.Slf4j;
import org.kdt.mooluck.elder.mapper.ElderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kdt.mooluck.elder.dto.ElderDTO;

@Slf4j
@Service
public class ElderServiceImpl implements ElderService {

    private final ElderMapper elderMapper;

    @Autowired
    public ElderServiceImpl(ElderMapper elderMapper) {
        this.elderMapper = elderMapper;
    }

    @Override
    public boolean validateMember(ElderDTO elderDto) {
        ElderDTO elder = elderMapper.getMemberByMemberId(elderDto);
        log.info("Input Account: {}, Input Password: {}", elderDto.getElderAccount(), elderDto.getElderPwd());
        if (elder != null) {
            log.info("Database Account: {}, Database Password: {}", elder.getElderAccount(), elder.getElderPwd());
            return elder.getElderPwd().equals(elderDto.getElderPwd());
        }
        log.warn("No matching account found for elderAccount: {}", elderDto.getElderAccount());
        return false;
    }
}
