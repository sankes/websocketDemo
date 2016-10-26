package com.base.util;

import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class EasyApplicationContextUtils
{
  private static ApplicationContext ctx;
  private static final Log logger = LogFactory.getLog(EasyApplicationContextUtils.class);

  public static void setApplicationContext(ApplicationContext alctx) {
	  ctx = alctx;
  }

  public static <T> T getBeanByType(Class<T> clazz)
  {
    Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
    Map map = ctx.getBeansOfType(clazz);
    if (map.size() == 1) {
      Object obj = null;
      for (Iterator i$ = map.keySet().iterator(); i$.hasNext(); ) { Object o = i$.next();
        obj = map.get(o); }

      return (T) obj;
    }
    if (map.size() == 0)
      new IllegalAccessException("在Spring容器中没有类型为" + clazz.getName() + "的Bean");
    else {
      new IllegalAccessException("在Spring容器中有多个类型为" + clazz.getName() + "的Bean");
    }
    return null;
  }

  public static Object[] getBeansByType(Class clazz)
  {
    Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
    Map map = ctx.getBeansOfType(clazz);
    if (map.size() >= 1) {
      return map.values().toArray();
    }
    new IllegalAccessException("在Spring容器中没有类型为" + clazz.getName() + "的Bean");

    return null;
  }

  public static Object getBeanByName(String beanName)
  {
    if (StringUtils.isEmpty(beanName)) {
      return null;
    }
    Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
    try
    {
      return ctx.getBean(beanName); } catch (BeansException e) {
    }
    return null;
  }

  public static <T> T getBeanByName(String beanName, Class<T> clazz)
  {
    return (T) getBeanByName(beanName);
  }

  public static ApplicationContext getApplicationContext()
  {
    return ctx;
  }

  public static void publishEvent(ApplicationEvent event)
  {
    ctx.publishEvent(event);
  }
}