package com.khs.payroll.ach.file.record;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.khs.payroll.constant.AchFileState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @Valid
    @NotNull(message = "ACH File Header Record is required")
    private AchFileHeaderRecord fileHeader;

    @Valid
    @NotNull(message = "Batch Records list is required")
    @Size(min = 1, message = "At least one Batch Record is required")
    private List<AchBatch> batchRecords;

    @Valid
    @NotNull(message = "ACH File Control Record is required")
    private AchFileControlRecord fileControl;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
    
    private AchFileState state;

    public AchPayment addAchBatch(final AchBatch achBatch) {
        if (batchRecords == null) {
            batchRecords = new ArrayList<>();
        }
        batchRecords.add(achBatch);
        return this;
    }
}
