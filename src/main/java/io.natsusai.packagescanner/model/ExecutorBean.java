package io.natsusai.packagescanner.model;

import java.lang.reflect.Method;

/**
 * @author liufuhong
 * @since 2019-06-04 10:03
 */

public class ExecutorBean {
  private Object object;

  private Method method;

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
