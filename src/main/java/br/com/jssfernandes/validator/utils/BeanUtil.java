package br.com.jssfernandes.validator.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;


public class BeanUtil {
    private static final Set<Class<?>> primitiveTypes = new HashSet<Class<?>>() {
        {
            add(Boolean.class);
            add(Character.class);
            add(Byte.class);
            add(Short.class);
            add(Integer.class);
            add(Long.class);
            add(Float.class);
            add(Double.class);
            add(String.class);
            add(BigDecimal.class);
            add(List.class);
            add(ArrayList.class);
            add(Set.class);
        }
    };

    public static void copyPropertiesNotNull(Object orig, Object dest) throws InvocationTargetException, IllegalAccessException {
        NullAwareBeanUtilsBean.getInstance().copyProperties(dest, orig);
    }

    private static class NullAwareBeanUtilsBean extends BeanUtilsBean {

        private static NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

        NullAwareBeanUtilsBean() {
            super(new ConvertUtilsBean(), new PropertyUtilsBean() {
                @Override
                public PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) {
                    return BeanUtils.getPropertyDescriptors(beanClass);
                }

                @Override
                public PropertyDescriptor getPropertyDescriptor(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
                    return BeanUtils.getPropertyDescriptor(bean.getClass(), name);
                }
            });
        }

        public static NullAwareBeanUtilsBean getInstance() {
            if (nullAwareBeanUtilsBean == null) {
                nullAwareBeanUtilsBean = new NullAwareBeanUtilsBean();
            }
            return nullAwareBeanUtilsBean;
        }

        @Override
        public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {

            if (value == null) {
                return;
            }

            try {
                final BeanWrapper target = new BeanWrapperImpl(bean);
                Object providedObjectTarget = target.getPropertyValue(name);
                if (providedObjectTarget == null 
                        || primitiveTypes.contains(value.getClass())
                        || Enum.class.isAssignableFrom(value.getClass())) {
                    super.copyProperty(bean, name, value);
                } else {
                    copyPropertiesIgnoreNull(value, providedObjectTarget);
                    super.copyProperty(bean, name, providedObjectTarget);
                }

            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | BeansException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public static void copyPropertiesIgnoreNull(Object source, Object dest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

            PropertyUtils.describe(source).entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .filter(e -> !e.getKey().equals("class"))
                    .forEach(e -> {
                        try {
                            PropertyUtils.setProperty(dest, e.getKey(), e.getValue());
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                            System.out.println(ex.getMessage());
                        }
                    });
        }
    }
}
