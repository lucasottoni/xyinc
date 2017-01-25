package br.com.zup.teste.xyinc.business.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * ContextConfig
 * Spring Configuration for business subproject.
 *
 * @author lucasottoni
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(value = {br.com.zup.teste.xyinc.model.config.ContextConfig.class})
@ComponentScan("br.com.zup.teste.xyinc.business")
public class ContextConfig {

}