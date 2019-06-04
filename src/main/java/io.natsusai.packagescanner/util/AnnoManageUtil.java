package io.natsusai.packagescanner.util;

import io.natsusai.packagescanner.model.ExecutorBean;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liufuhong
 * @since 2019-06-04 10:02
 */

public class AnnoManageUtil {
  /**
   * 获取指定文件下面的RequestMapping方法保存在mapp中
   *
   * @param packageName
   * @return
   */
  public static Map<String, ExecutorBean> getRequestMappingMethod(String packageName) {
    Reflections   reflections = new Reflections(packageName);
    Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);

    // 存放url和ExecutorBean的对应关系
    Map<String, ExecutorBean> mapp = new HashMap<String, ExecutorBean>();
    for (Class classes : classesList) {
      //得到该类下面的所有方法
      Method[] methods = classes.getDeclaredMethods();

      for (Method method : methods) {
        //得到该类下面的RequestMapping注解
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (null != requestMapping) {
          ExecutorBean executorBean = new ExecutorBean();
          try {
            executorBean.setObject(classes.newInstance());
          } catch (InstantiationException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
          executorBean.setMethod(method);
          mapp.put(Arrays.toString(requestMapping.value()), executorBean);

        }
      }
    }
    return mapp;
  }
}
