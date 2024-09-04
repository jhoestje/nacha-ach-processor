package org.johoco.nacha.config;

import java.nio.file.Path;
import java.time.Duration;

import org.johoco.nacha.file.listener.AchFileChangeListener;
import org.johoco.nacha.properties.ApplicationProperties;
import org.johoco.nacha.properties.FileWatcherProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//        fileSystemWatcher.addListener(new CustomerAddFileChangeListener(fileProcessor));
//        fileSystemWatcher.setTriggerFilter(f -> f.toPath().endsWith(".csv"));
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
