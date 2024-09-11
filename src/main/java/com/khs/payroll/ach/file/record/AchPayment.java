package com.khs.payroll.ach.file.record;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @NotNull(message = "ACH File Header Record is required")
    @Valid
    private AchFileHeaderRecord fileHeader;

    @NotNull(message = "Batch Records list is required")
    @Size(min = 1, message = "At least one Batch Record is required")
    @Valid
    private List<AchBatch> batchRecords;

    @NotNull(message = "ACH File Control Record is required")
    @Valid
    private AchFileControlRecord fileControl;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public AchPayment addAchBatch(final AchBatch achBatch) {
        if (batchRecords == null) {
            batchRecords = new ArrayList<>();
        }
        batchRecords.add(achBatch);
        return this;
    }
}
