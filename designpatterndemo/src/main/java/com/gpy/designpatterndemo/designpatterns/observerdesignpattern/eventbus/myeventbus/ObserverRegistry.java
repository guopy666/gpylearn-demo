package com.gpy.designpatterndemo.designpatterns.observerdesignpattern.eventbus.myeventbus;

import com.google.common.base.Preconditions;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName ObserverRegistry 表示 Observer 注册表的类
 * @Description
 * @Author guopy
 * @Date 2021/7/27 9:53
 */
public class ObserverRegistry {
    private ConcurrentMap<Class<?>, CopyOnWriteArraySet<ObserverAction>> registry = new ConcurrentHashMap<>();

    public void register(Object observer) {
        Map<Class<?>, Collection<ObserverAction>> allObserverAction = findAllObserverAction(observer);
        for (Map.Entry<Class<?>, Collection<ObserverAction>> entry : allObserverAction.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> eventActions = entry.getValue();
            CopyOnWriteArraySet<ObserverAction> registeredActions = registry.get(eventType);
            if (null == registeredActions) {
                registry.putIfAbsent(eventType, new CopyOnWriteArraySet<>());
                registeredActions = registry.get(eventType);
            }
            registeredActions.addAll(eventActions);
        }
    }

    public List<ObserverAction> getMatchedObserverAction(Object event){
        List<ObserverAction> observerAcitons = new ArrayList<>();
        Class<?> postedEventType = event.getClass();
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : registry.entrySet()) {
            Class<?> eventType = entry.getKey();
            CopyOnWriteArraySet<ObserverAction> eventActions = entry.getValue();
            if (eventType.isAssignableFrom(postedEventType)){
                observerAcitons.addAll(eventActions);
            }
        }
        return observerAcitons;
    }

    private Map<Class<?>, Collection<ObserverAction>> findAllObserverAction(Object observer) {
        Map<Class<?>, Collection<ObserverAction>> observerActions = new HashMap<>();
        Class<?> observerClass = observer.getClass();
        for (Method method : getAnnotatedMethods(observerClass)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> eventType = parameterTypes[0];
            if (!observerActions.containsKey(eventType)) {
                observerActions.put(eventType, new ArrayList<>());
            }
            observerActions.get(eventType).add(new ObserverAction(observer, method));
        }
        return observerActions;
    }

    private List<Method> getAnnotatedMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MySubscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Preconditions.checkArgument(parameterTypes.length == 1,
                        "Method %s has @MySubscribe Annotation but has %s parameters," +
                                "MySubscriber must has exactly 1 parameter", method, parameterTypes.length);
                methods.add(method);
            }
        }
        return methods;
    }

}