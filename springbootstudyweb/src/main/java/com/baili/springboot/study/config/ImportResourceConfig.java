package com.baili.springboot.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author jiangjingming
 * @date 2017/11/25
 */
@Configuration
@ImportResource("classpath:dubbo/dubbo-all.xml")
public class ImportResourceConfig {
}
