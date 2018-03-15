package ma.najeh.ibnouzouhr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RegisterConfig extends WebMvcConfigurerAdapter {
	
	private final CustomHandlerIntercepter customHandlerIntercepter;

	@Autowired
	public RegisterConfig(CustomHandlerIntercepter customHandlerIntercepter) {
		this.customHandlerIntercepter = customHandlerIntercepter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customHandlerIntercepter).addPathPatterns("/rest/**");
	}
}