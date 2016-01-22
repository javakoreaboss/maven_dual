package kr.co.ecore.common.config;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableAsync
@EnableTransactionManagement
/*@PropertySource("classpath:properties/config.properties")*/
@ComponentScan(basePackages="kr.co.ecore.web")
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
	
	@Autowired
	private Environment environment;
	
/*	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(new String[] { "/assets/**" }).addResourceLocations(new String[] { "/assets/" });
	}*/

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable("");
	}

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		logger.debug("configureContentNegotiation");

		configurer.favorPathExtension(true).useJaf(false)
				.ignoreAcceptHeader(true)
				.mediaType("html", MediaType.TEXT_HTML)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.defaultContentType(MediaType.TEXT_HTML);
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		logger.debug("contentNegotiatingViewResolver");
		List<ViewResolver> resolvers	= new ArrayList<ViewResolver>();
			
		InternalResourceViewResolver resolver1 = new InternalResourceViewResolver();
		resolver1.setPrefix("/WEB-INF/views/");
		resolver1.setSuffix(".jsp");
		resolvers.add(resolver1);
		/*s	
		JsonViewResolver resolver2	= new JsonViewResolver();
		resolvers.add(resolver2);*/
		
		ContentNegotiatingViewResolver resolver	= new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		
		return resolver;
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		logger.debug("dataSource");
		/*
		String serverInfo	= null;
		if(environment != null && environment.getProperty("serverInfo") != null){
			serverInfo	= environment.getProperty("serverInfo").toString();
		}*/

		Context initContext = new InitialContext();
		//if("L".equals(serverInfo)) {
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			return (DataSource) envContext.lookup("jdbc/ecore");
		//} 
		/*
		else if("R".equals(serverInfo)) {
			return (DataSource)initContext.lookup("dbwebsp01");			
		} else {
			return (DataSource)initContext.lookup("dbtrpst01");		
		}*/
		//return (DataSource)initContext.lookup("jdbc/ecore");	
	}
	
	@Bean
	public DataSource dataSourceMysql() throws Exception {
		logger.debug("dataSourceMysql");
		/*
		String serverInfo	= null;
		if(environment != null && environment.getProperty("serverInfo") != null){
			serverInfo	= environment.getProperty("serverInfo").toString();
		}*/

		Context initContext = new InitialContext();
		//if("L".equals(serverInfo)) {
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			return (DataSource) envContext.lookup("jdbc/ecore2");
		//} 
		/*
		else if("R".equals(serverInfo)) {
			return (DataSource)initContext.lookup("dbwebsp01");			
		} else {
			return (DataSource)initContext.lookup("dbtrpst01");		
		}*/
		//return (DataSource)initContext.lookup("jdbc/ecore");	
	}
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/sql/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="sqlSessionFactoryMysql")
	public SqlSessionFactory sqlSessionFactoryMysql(DataSource dataSourceMysql, ApplicationContext applicationContext) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSourceMysql);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/Mysql/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/Mysql/sql/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}	

	@Bean(name="sqlSession")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		logger.debug("sqlSessionTemplate");

		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name="sqlSessionMysql")
	public SqlSessionTemplate sqlSessionTemplateMysql(SqlSessionFactory sqlSessionFactoryMysql) {
		logger.debug("sqlSessionTemplateMysql");

		return new SqlSessionTemplate(sqlSessionFactoryMysql);
	}	

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public PropertiesFactoryBean properties() {
		PropertiesFactoryBean propertiesFactoryBean	= new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("properties/config.properties"));
		return propertiesFactoryBean;
	}

/*	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new InterceptorHandler());
	}*/
}
