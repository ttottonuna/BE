package org.kdt.mooluck.scheduler;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ResetCountsScheduler {

    private final SqlSession sqlSession;

    // 생성자 주입으로 SqlSession 의존성 주입
    public ResetCountsScheduler(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 매일 자정에 실행되도록 설정
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    // @Scheduled(fixedRate = 5000) // test. 5초마다 실행
    public void resetDailyCounts() {
        sqlSession.update("org.kdt.mooluck.domain.interaction.mapper.InteractionMapper.resetCounts"); // MyBatis의 매핑된 쿼리 실행
        System.out.println("Daily counts reset at: " + new Date());
    }
}