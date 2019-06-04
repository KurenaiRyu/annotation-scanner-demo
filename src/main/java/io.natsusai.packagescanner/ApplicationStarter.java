package io.natsusai.packagescanner;

import io.natsusai.packagescanner.annotation.Consumer;
import io.natsusai.packagescanner.interfaze.IConsumer;
import java.lang.reflect.Method;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liufuhong
 * @since 2019-06-04 9:29
 */

@SpringBootApplication
public class ApplicationStarter {

  private static final String packageName = "io.natsusai.packagescanner.bean";

  public static void main(String[] args) {

    SpringApplication.run(ApplicationStarter.class, args);

    getBeanDefinition();
    System.out.println("-------------");
    get();
  }

  private static void getBeanDefinition() {

    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false); // 不使用默认的TypeFilter
    provider.addIncludeFilter(new AnnotationTypeFilter(Consumer.class));
    provider.addIncludeFilter(new AssignableTypeFilter(IConsumer.class));
    Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(packageName);
    beanDefinitionSet.stream()
                     .forEach(b -> System.out.println(b.getBeanClassName()));
  }

  private static void get() {

    Reflections   reflections = new Reflections(packageName);
    Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
    for(Class clazz:classesList){
      RequestMapping clazzRequestMapping = (RequestMapping)clazz.getAnnotation(RequestMapping.class);
      if(clazzRequestMapping!=null) {
        String[] clazzPath = clazzRequestMapping.value();
        if (clazzPath.length<=0) {
          clazzPath = clazzRequestMapping.path();
        }
        Method[] methods   = clazz.getMethods();
        for (Method method : methods) {
          RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
          GetMapping     getMapping     = method.getAnnotation(GetMapping.class);
          PostMapping    postMapping    = method.getAnnotation(PostMapping.class);
          String[]       path           = null;
          if (requestMapping != null) {
            path = requestMapping.value();
          } else if (getMapping != null) {
            path = getMapping.value();
          } else if (postMapping != null) {
            path = postMapping.value();
          }
          if(path!=null&&path.length>0)System.out.println(clazzPath[0] + path[0]);
        }
      }
    }
  }

}
