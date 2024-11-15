
package org.kdt.mooluck.elder.service;

import lombok.extern.slf4j.Slf4j;
import org.kdt.mooluck.elder.mapper.ElderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kdt.mooluck.elder.dto.ElderDTO;
import org.kdt.mooluck.elder.exception.ElderNotFoundException;


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
}
