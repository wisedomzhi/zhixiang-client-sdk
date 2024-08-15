package com.wisewind.zhixiangclientsdk;

import com.wisewind.zhixiangclientsdk.client.ZhiXiangInterfaceClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("zhixiang.client")
@ComponentScan
@Data
public class ZhiXiangClientConfiguration {
    private String accessKey;
    private String secretKey;

    @Bean
    public ZhiXiangInterfaceClient zhiXiangInterfaceClient(){
        return new ZhiXiangInterfaceClient(accessKey, secretKey);
    }

}
