package com.crowdfund.projects.microservices.projectservice.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Slf4j
//@Component
//public class TransferMoneyToInnovatorJob {
//
//    @Scheduled(cron = "0 0 */1 * * *")
//    @Transactional
//    public void transferMoneyToInnovator() throws InterruptedException {
//        log.info(
//                "Transfer Money to innovator running - " + System.currentTimeMillis() / 1000);
//
//
//    }
//
//}
