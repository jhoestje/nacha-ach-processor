package org.johoco.payroll.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private FileWatcherProperties fileWatcher;
}
