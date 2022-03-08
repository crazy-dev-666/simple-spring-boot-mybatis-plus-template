package cn.dev666.simple.template.utils;

import cn.dev666.simple.template.enums.CommonErrorInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class ResourceUtils {

    private static final Map<ResourceKey, ResourceBundle> RESOURCE_BUNDLE_MAP = new HashMap<>();

    public static String getMsg(String baseName, String name, Object... args){
        return getMsg(baseName, Locale.getDefault(), name, args);
    }

    public static String getMsg(String baseName, Locale locale, String name, Object... args){
        ResourceBundle bundle = getResourceBundle(baseName, locale);
        String msg = getMsg(baseName, name, bundle, args);
        if (msg == null) {
            msg = finallyMsg(locale, name);
        }
        return msg;
    }

    private static String finallyMsg(Locale locale, String name) {
        String msg;
        if (CommonErrorInfo.DEFAULT_ERROR.name().equals(name)){
            if (Locale.CHINA.equals(locale)){
                msg = "服务异常，请稍后重试";
            }else {
                msg = "Service unavailable, please try again later";
            }
        }else {
            msg = getMsg(CommonErrorInfo.RESOURCE_NAME, CommonErrorInfo.DEFAULT_ERROR.name());
        }
        return msg;
    }

    private static String getMsg(String baseName, String name, ResourceBundle bundle, Object... args) {
        String msg = null;
        try {
            msg = bundle.getString(name);
        }catch (Exception e){
            log.error("从资源文件 " + baseName + " 中，根据 " + name + " 获取提示信息异常", e);
        }
        if (msg != null && args != null && args.length > 0){
            try {
                msg = new String(msg.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                msg = MessageFormat.format(msg, args);
            }catch (Exception e){
                log.error("根据参数 " + Arrays.toString(args) + " 格式化提示信息 " + msg + " 异常", e);
                msg = null;
            }
        }
        return msg;
    }


    private static ResourceBundle getResourceBundle(String baseName, Locale locale) {
        ResourceBundle bundle;
        synchronized (baseName.intern()){
            bundle = RESOURCE_BUNDLE_MAP.computeIfAbsent(new ResourceKey(baseName, locale),
                    k -> ResourceBundle.getBundle(k.name, k.locale));
        }
        return bundle;
    }


    @AllArgsConstructor
    @EqualsAndHashCode
    private static class ResourceKey {
        private String name;
        private Locale locale;
    }
}
