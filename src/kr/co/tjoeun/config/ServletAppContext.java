package kr.co.tjoeun.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.tjoeun.interceptor.TopMenuInterceptor;
import kr.co.tjoeun.mapper.BoardMapper;
import kr.co.tjoeun.mapper.TopMenuMapper;
import kr.co.tjoeun.mapper.UserMapper;
import kr.co.tjoeun.service.TopMenuService;


// Spring MVC project 에 관련된 설정을 하는 클래스: servlet-context.xml 역할을 하는 클래스
// ServletAppContext 의 객체는 SpringConfigClass 클래스의 onStartup 메소드에서 생성함
@Configuration
// @Controller 어노테이션이 설정된 클래스를 Controller 로 등록하는 Annotation
@EnableWebMvc
// scan 할 bean 들이 모여 있는 package 지정하는 Annotation
@ComponentScan("kr.co.tjoeun.controller")
@ComponentScan("kr.co.tjoeun.dao")
@ComponentScan("kr.co.tjoeun.service")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
  
  @Value("${db.classname}")
  private String dbClassname;
  
  @Value("${db.url}")
  private String dbUrl;
  
  @Value("${db.username}")
  private String dbUsername;
  
  @Value("${db.password}")
  private String dbPassword;
  
  @Autowired
  private TopMenuService topMenuService;
  
  
	
	// Controller 의 메소드에서 반환하는 문자열의 prefix 와 suffix 경로 정보 설정하기
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {	
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	// 정적 파일 경로 지정하기: HTML 에서 사용하는 이미지, 사운드, js, CSS 등
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
	// BasicDataSource : 데이터베이스 접속정보를 관리하는 Bean (을 return 함)
  @Bean
  public BasicDataSource dataSource() {
	BasicDataSource source = new BasicDataSource();
	source.setDriverClassName(dbClassname);
	source.setUrl(dbUrl);
	source.setUsername(dbUsername);
	source.setPassword(dbPassword);
	
	return source;
  }
  
  // Query 문과 Database 접속 정보를 관리하는 Bean (을 return 함) 
  @Bean
  public SqlSessionFactory factory(BasicDataSource source) throws Exception{
	SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	factoryBean.setDataSource(source);
	SqlSessionFactory factory = factoryBean.getObject();
	return factory;
  }
  
  // Query 문 실행을 위한 Bean(Mapper 관리) (을 return 함) 
  @Bean
  public MapperFactoryBean<BoardMapper> 
    getBoardMapper(SqlSessionFactory factory) throws Exception{
	
	MapperFactoryBean<BoardMapper> factoryBean = 
		new MapperFactoryBean<BoardMapper>(BoardMapper.class);
	factoryBean.setSqlSessionFactory(factory);
	return factoryBean;
	
  }
  
  @Bean
  public MapperFactoryBean<TopMenuMapper> 
     getTopMenuMapper1(SqlSessionFactory factory) throws Exception{
	
	MapperFactoryBean<TopMenuMapper> factoryBean = 
		new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
	factoryBean.setSqlSessionFactory(factory);
	return factoryBean;
	
  }
  
  //Mapper 등록하기
  @Bean
  public MapperFactoryBean<UserMapper> 
     getUserMapper(SqlSessionFactory factory) throws Exception{
	
	MapperFactoryBean<UserMapper> factoryBean = 
		new MapperFactoryBean<UserMapper>(UserMapper.class);
	factoryBean.setSqlSessionFactory(factory);
	return factoryBean;
	
  }
  
  // Interceptor 등록하기
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebMvcConfigurer.super.addInterceptors(registry);
    
    TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService);
    
    InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
    reg1.addPathPatterns("/**");
    
    
    
  }
  
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	  return new PropertySourcesPlaceholderConfigurer();
  }
  
  //에러 메시지를 작성한 properties 파일 등록하기
  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
	  ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
	  res.setBasename("/WEB-INF/properties/error");
	  return res;
  }
  
 
	
}





