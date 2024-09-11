package com.khs.payroll.config;

import java.nio.file.Path;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.khs.payroll.ach.file.listener.AchFileChangeListener;
import com.khs.payroll.properties.ApplicationProperties;
import com.khs.payroll.properties.FileWatcherProperties;

import jakarta.annotation.PreDestroy;

@Configuration
public class FileWatcherConfig {

    private ApplicationProperties properties;
    private AchFileChangeListener fileChangeListener;
    private static Logger LOG = LoggerFactory.getLogger(FileWatcherConfig.class); 

    public FileWatcherConfig(final ApplicationProperties properties, final AchFileChangeListener fileChangeListener) {
        this.properties = properties;
        this.fileChangeListener = fileChangeListener;
    }

    @Bean
    FileSystemWatcher fileSystemWatcher() {
        FileWatcherProperties watcherProperties = properties.getFileWatcher();

        FileSystemWatcher watcher = new FileSystemWatcher(watcherProperties.getDaemon(), Duration.ofSeconds(watcherProperties.getPollInterval()),
                Duration.ofSeconds(watcherProperties.getQuietPeriod()));
        watcher.addListener(fileChangeListener);
        watcher.addSourceDirectory(Path.of(watcherProperties.getDirectory()).toFile());
        watcher.start();
        LOG.info(String.format("FileSystemWatcher start: %s", watcherProperties.getDirectory()));

        return watcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        LOG.info("FileSystemWatcher Stop");
        fileSystemWatcher().stop();
    }
}
