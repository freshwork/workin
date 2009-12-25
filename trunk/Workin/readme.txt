
/**=====================================
 *	About source codes in workin-test
 **==========================================/
 
# classes will be add to Junit auto testing group.do test in every build.
	*Test.java
	      
# classes no need do Junit auto test, which is like this pattern.
	*Tested.java
	

# 2009-11-17	
------------------------------------------------------------------
added 	org.workin.util.EncodeUtils.java
added   org.workin.log.MockAppender.java
added  	org.workin.log.TraceUtils.java
added 	org.workin.web.struts2.Struts2Utils.java
added 	org.workin.util.WebUtils.java
added	org.workin.web.filter.UnitiveResponseHeaderFilter.java

added	org.workin.test.utils.WebUtilsTest.java
added	org.workin.test.spring.SpringContextHolderTest.java
added   org.workin.test.utils.ReflectionUtilsTest.java
added 	org.workin.test.log.JulToSlf4jHandlerTest.java
added 	org.workin.test.web.filter.RequestHeaderFilterTest.java
------------------------------------------------------------------
updated commons-codec-1.4.jar	
updated commons-beanutils-1.8.2.jar
updated org.workin.util.ReflectionUtils.java
updated org.workin.web.struts2.DefaultActionSupport.java
updated org.workin.exception.ThrowableHandle.java
------------------------------------------------------------------
remove commons-collections,commons-logging,commons-beanutils
move checkstyle-all-5.0.jar to tools/checkstyle
------------------------------------------------------------------

# 2009-11-18	
------------------------------------------------------------------
added : /Source/Workin/lib/activemq-all-5.3.0.jar  
added : /Source/Workin/lib/activemq-pool-5.3.0.jar  
added : /Source/Workin/lib/commons-pool-1.4.jar  
added : /Source/Workin/lib/xbean-spring-2.8.jar  
added : /Source/Workin/resource/jms/activemq.xml  
added : /Source/Workin/resource/jms/applicationContext-consumer.xml  
added : /Source/Workin/resource/jms/applicationContext-jms.xml  
added : /Source/Workin/resource/jms/applicationContext-producer.xml  
added : /Source/Workin/resource/jms/jms.properties  
added : /Source/Workin/workin-test/org/workin/test/notify  
added : /Source/Workin/workin-test/org/workin/test/notify/JMSQueueNotifyTested.java  
added : /Source/Workin/workin-test/org/workin/test/notify/JMSTopicNotifyTested.java  
added : /Source/Workin/workin/org/workin/jms/JMSUser.java  
added : /Source/Workin/workin/org/workin/jms/producer/MessageProducer.java  
added : /Source/Workin/workin/org/workin/jms/producer/MessageProducerTemplate.java  
added : /Source/Workin/workin/org/workin/jms/queue/QueueConsumer.java  
added : /Source/Workin/workin/org/workin/jms/queue/QueueConsumerTemplate.java  
added : /Source/Workin/workin/org/workin/jms/topic/TopicListenerTemplate.java  
added : /Source/Workin/workin/org/workin/notify/MailNotificationHolder.java  
added : /Source/Workin/workin/org/workin/notify/Notification.java  
added : /Source/Workin/workin/org/workin/notify/producer/NotifyMessageProducer.java  
added : /Source/Workin/workin/org/workin/notify/queue/NotifyQueueConsumer.java  
added : /Source/Workin/workin/org/workin/notify/topic/NotifyTopicListener.java  

updated : /Source/Workin/resource/applicationContext.xml  
updated : /Source/Workin/resource/email/applicationContext-email.xml  
updated : /Source/Workin/resource/email/email.properties  
updated : /Source/Workin/workin/org/workin/fortest/spring/SpringContextTestCase.java  
updated : /Source/Workin/workin/org/workin/fortest/spring/SpringTxTestCase.java
updated : /Source/Workin/workin/org/workin/mail/support/MimeMailService.java  
updated : /Source/Workin/workin/org/workin/mail/support/SimpleMailService.java

# 2009-11-26	
------------------------------------------------------------------
updated : /Source/Workin/workin-test/org/workin/test/application/PersonServiceTest.java  
updated : /Source/Workin/workin-test/org/workin/test/application/entity/Person.java  
updated : /Source/Workin/workin-test/org/workin/test/application/service/PersonService.java  
updated : /Source/Workin/workin-test/org/workin/test/application/service/PersonServiceImpl.java  
updated : /Source/Workin/workin/org/workin/core/persistence/jpa/JpaPersistence.java  
updated : /Source/Workin/workin/org/workin/core/persistence/jpa/JpaPersistenceImpl.java  
updated : /Source/Workin/workin/org/workin/core/persistence/support/PersistenceService.java  
updated : /Source/Workin/workin/org/workin/core/persistence/support/PersistenceServiceProvider.java  
updated : /Source/Workin/build.xml  
updated : /Source/Workin/workin/org/workin/util/WebUtils.java 

# 2009-11-27	
------------------------------------------------------------------
added : /Source/Workin/lib/jaxen-1.1.jar  
added : /Source/Workin/workin/org/workin/exception/NoImplementsException.java  
added : /Source/Workin/workin-test/org/workin/test/xml/jaxb/HouseMap.java  

updated : /Source/Workin/workin-test/org/workin/test/xml/JaxbBinderTest.java  
updated : /Source/Workin/workin-test/org/workin/test/xml/jaxb/Role.java  
updated : /Source/Workin/workin-test/org/workin/test/xml/jaxb/User.java  
updated : /Source/Workin/workin/org/workin/log/MockAppender.java  
updated : /Source/Workin/workin/org/workin/log/TraceUtils.java  
updated : /Source/Workin/workin/org/workin/util/FileUtils.java  
updated : /Source/Workin/workin/org/workin/util/WebUtils.java  
updated : /Source/Workin/workin/org/workin/xml/JaxbBinder.java  
updated : /Source/Workin/workin/org/workin/xml/XStreamBinder.java  
updated : /Source/Workin/workin/org/workin/xml/XmlBinder.java  

