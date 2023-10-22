//package com.hzk.listener;
//
//import com.ecwid.consul.SingleUrlParameters;
//import com.ecwid.consul.UrlParameters;
//import com.ecwid.consul.json.GsonFactory;
//import com.ecwid.consul.transport.HttpResponse;
//import com.ecwid.consul.v1.ConsulClient;
//import com.ecwid.consul.v1.ConsulRawClient;
//import com.ecwid.consul.v1.OperationException;
//import com.ecwid.consul.v1.Response;
//import com.ecwid.consul.v1.agent.model.NewService;
//import com.hzk.util.ApplicationContextUtil;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.consul.ConsulAutoConfiguration;
//import org.springframework.cloud.consul.ConsulProperties;
//import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
//import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.lang.reflect.Field;
//
//@Component
//public class ApplicationRereshListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
//
//    @Autowired
//    private ConsulProperties consulProperties;
//    @Autowired
//    private Environment environment;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    }
//
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        try {
//            // com.ecwid.consul.v1.agent.AgentConsulClient.agentServiceRegister
//            ConsulAutoServiceRegistration consulAutoServiceRegistration = ApplicationContextUtil.getContext()
//                    .getBean("consulAutoServiceRegistration", ConsulAutoServiceRegistration.class);
//            Class<ConsulAutoServiceRegistration> consulAutoRegistrationClass = ConsulAutoServiceRegistration.class;
//            Field field = consulAutoRegistrationClass.getDeclaredField("registration");
//            field.setAccessible(true);
//            ConsulAutoRegistration consulAutoRegistration = (ConsulAutoRegistration)field.get(consulAutoServiceRegistration);
//            NewService newService = consulAutoRegistration.getService();
//            System.out.println(newService);
//
//            // 注册实例对象
//            String port = environment.getProperty("server.port");
//            String applicationName = environment.getProperty("spring.application.name");
//            String consulHost = environment.getProperty("spring.cloud.consul.host");
//            String consulPort = environment.getProperty("spring.cloud.consul.port");
//            String userDomain = System.getenv("USERDOMAIN");
//
//            NewService newService = new NewService();
//            newService.setId(applicationName + "-" + port);
//            newService.setName(applicationName);
//
//            // httpClient,代码ConsulAutoConfiguration.createConsulClient
//            String agentPath = consulProperties.getPath();
//            String agentHost = StringUtils.hasLength(consulProperties.getScheme()) ? consulProperties.getScheme() + "://" + consulProperties.getHost() : consulProperties.getHost();
//            ConsulRawClient.Builder builder = ConsulRawClient.Builder.builder().setHost(agentHost).setPort(consulProperties.getPort());
//            if (consulProperties.getTls() != null) {
//                ConsulProperties.TLSConfig tls = consulProperties.getTls();
//                com.ecwid.consul.transport.TLSConfig tlsConfig = new com.ecwid.consul.transport.TLSConfig(tls.getKeyStoreInstanceType(), tls.getCertificatePath(), tls.getCertificatePassword(), tls.getKeyStorePath(), tls.getKeyStorePassword());
//                builder.setTlsConfig(tlsConfig);
//            }
//            if (StringUtils.hasLength(agentPath)) {
//                String normalizedAgentPath = StringUtils.trimTrailingCharacter(agentPath, '/');
//                normalizedAgentPath = StringUtils.trimLeadingCharacter(normalizedAgentPath, '/');
//                builder.setPath(normalizedAgentPath);
//            }
//            ConsulRawClient rawClient = builder.build();
//            String token = "d94e4930-eb66-9a87-f64d-0b81b362a502";
//            UrlParameters tokenParam = token != null ? new SingleUrlParameters("token", token) : null;
//            String json = GsonFactory.getGson().toJson(newService);
//            HttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/service/register", json, tokenParam);
//            if (httpResponse.getStatusCode() == 200) {
//                System.out.println(httpResponse.getStatusMessage());
//            } else {
//                throw new OperationException(httpResponse);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
