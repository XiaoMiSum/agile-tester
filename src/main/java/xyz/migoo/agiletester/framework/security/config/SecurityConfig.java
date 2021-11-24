package xyz.migoo.agiletester.framework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import xyz.migoo.framework.web.config.WebProperties;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig {

    @Resource
    private WebProperties webProperties;

    /**
     * 项目自定义 权限规则，定义该bean主要是框架中有 使用到该对象，否则无法启动系统
     *
     * @return 权限规则配置
     */
    @Bean
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer() {
        return registry -> registry.antMatchers(api("/signup-check")).anonymous()
                .antMatchers(api("/user"), api("/signup")).authenticated();
    }

    private String api(String url) {
        return webProperties.getApiPrefix() + url;
    }

}
