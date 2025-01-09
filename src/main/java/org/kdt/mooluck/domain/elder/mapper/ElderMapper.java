package org.kdt.mooluck.domain.elder.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kdt.mooluck.domain.elder.dto.ElderDTO;

@Mapper
public interface ElderMapper {

    // Elder ID 조회
    String getMemberByMemberId(ElderDTO elderDTO);

    // 비밀번호 조회
    String getPasswordByAccount(String elderAccount);

}
