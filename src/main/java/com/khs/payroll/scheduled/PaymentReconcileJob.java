package com.khs.payroll.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaymentReconcileJob {
    private Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * Not implemented in this example.
     */
    @Scheduled(cron = "0 0 2 * * ?")  // Runs every day at 2 AM
    public void processReconcile() {
        LOG.info("Looking for payments to reconcile");
        
    }

}
