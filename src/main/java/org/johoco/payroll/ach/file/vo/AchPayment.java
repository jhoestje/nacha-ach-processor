package org.johoco.payroll.ach.file.vo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document("achPayments")
public class AchPayment {
    @Id
    private String id;
    private AchFileHeaderRecord fileHeader;
    private List<AchBatch> batchRecords;
    private AchFileControlRecord fileControl;
    
    @CreatedDate
    private Instant createdDate;
    
    @LastModifiedDate
    private Instant lastModifiedDate;
        
    public AchPayment addAchBatch(final AchBatch achBatch) {
        if(batchRecords == null) {
            batchRecords = new ArrayList<>();
        }
        batchRecords.add(achBatch);
        return this;
    }
}
