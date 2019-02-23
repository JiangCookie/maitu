package com.ljc.maitu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author JYH
 * 2019/2/12 15:07
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "web",ignoreUnknownFields = false)
@PropertySource("classpath:maitu.properties")
public class MaituProperties {

}
