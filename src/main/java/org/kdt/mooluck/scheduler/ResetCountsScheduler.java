package org.kdt.mooluck.scheduler;


import org.kdt.mooluck.domain.interaction.mapper.InteractionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class ResetCountsScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ResetCountsScheduler.class);

    private final InteractionMapper interactionMapper;

    // 생성자 주입으로 -> SqlSession 의존성 주입
    public ResetCountsScheduler(InteractionMapper interactionMapper) {
        this.interactionMapper = interactionMapper;
    }

    @Scheduled(fixedRate = 5000000) //스케쥴러 테스트 83분20초
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    @Transactional
    public void resetDailyCounts() {
        try {
            logger.info("스케줄러 실행 시작: 전날 데이터 백업 및 초기화 작업");

            interactionMapper.PreviousDayData();
            logger.info("전날 데이터 백업 완료: {}", LocalDate.now().minusDays(1));

            interactionMapper.resetCounts();
            logger.info("오늘 데이터 초기화 완료: {}", LocalDate.now());
        } catch (Exception e) {
            logger.error("스케줄러 작업 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}
