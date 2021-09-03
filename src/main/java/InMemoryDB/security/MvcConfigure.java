package InMemoryDB.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages={"InMemoryDB.controller"})
public class MvcConfigure implements WebMvcConfigurer {

    public MvcConfigure() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/adminView");
        registry.addViewController("/loginView");
        registry.addViewController("/employee");
        registry.addViewController("/ListDepartmentsView");
        registry.addViewController("/ListView");
    }
 @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/view/");
        bean.setSuffix(".jsp");

        return bean;
    }


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
       // registry.addInterceptor(new LoggerInterceptor());
        // registry.addInterceptor(new UserInterceptor());
        //   registry.addInterceptor(new SessionTimerInterceptor());
    }
}
