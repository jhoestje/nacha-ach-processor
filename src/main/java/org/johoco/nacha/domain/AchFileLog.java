package org.johoco.nacha.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("achFileLogs")
public class AchFileLog {
    
    @Id
    private String id;
    
    private String filename;
    
    @CreatedDate
    private Instant createdDate;
    
    @LastModifiedDate
    private Instant lastModifiedDate;
}
