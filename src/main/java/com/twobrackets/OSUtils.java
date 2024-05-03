package com.twobrackets;

public class OSUtils {
    private static String osName;

    public static String getOsName(){
        if(osName == null){
            osName = System.getProperty("os.name");
        }
        return osName;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().startsWith("Linux");
    }

    public static boolean isMac() {
        return getOsName().startsWith("Mac");
    }

}
