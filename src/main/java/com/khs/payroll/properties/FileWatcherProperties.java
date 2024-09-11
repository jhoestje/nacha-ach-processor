package com.khs.payroll.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileWatcherProperties {
    private String directory;
    private Boolean daemon;
    private Long pollInterval;
    private Long quietPeriod;
}
