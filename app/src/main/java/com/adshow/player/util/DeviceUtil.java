package com.adshow.player.util;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.adshow.player.bean.DeviceInfo;
import com.adshow.player.bean.UserLogin;
import com.adshow.player.bean.UserToken;
import com.adshow.player.service.ADPlayerBackendService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

public class DeviceUtil {

    private static final String TAG = "DeviceUtils";

    static DecimalFormat df = new DecimalFormat("0.00");

    static TelephonyManager tm;

    /**
     * 获取屏幕信息
     *
     * @param windowManager
     * @return
     */
    public static String getScreenInfo(WindowManager windowManager) {

        DisplayMetrics metrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getRealMetrics(metrics);

        int screenHeight = metrics.heightPixels;

        int screenWidth = metrics.widthPixels;

        String resolution = screenWidth + "*" + screenHeight;

        //float density = metrics.density;        // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = metrics.densityDpi;    // 屏幕DPI（120 / 160 / 240）
        //screenInfo = "分辨率:" + resolution + " 屏幕密度:" + density + " DPI:" + densityDpi;

        return resolution;
    }


    /**
     * 获取移动网络运营商的名称
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {

        String networkOperatorName;

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        networkOperatorName = tm.getNetworkOperatorName();

        if ("".equals(networkOperatorName) || "null".equals(networkOperatorName)) {
            networkOperatorName = "未获取到运营商名称";
        }

        return networkOperatorName;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getOsVersion() {
        return "Android " + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /***
     * 使用WIFI时，获取本机IP地址
     * @param mContext
     * @return
     */
    public static String[] getWiFiInfo(Context mContext) {

        String[] WifiInfo = new String[3];

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            WifiInfo[0] = "Wifi未开启"; // Wifi名称
            WifiInfo[1] = "Wifi未开启"; // Wifi IP地址
        } else {

            WifiInfo[0] = wifiInfo.getSSID().substring(1, (wifiInfo.getSSID().length() - 1));
            int ipAddress = wifiInfo.getIpAddress();

            WifiInfo[1] = formatIpAddress(ipAddress);

            if (WifiInfo[1].equals("0.0.0.0")) {
                WifiInfo[0] = "Wifi未连接";
                WifiInfo[1] = "Wifi未连接";
            }
        }
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "Device don't have mac address or Wi-Fi is disabled";
        }
        WifiInfo[2] = macAddress;

        return WifiInfo;
    }

    /**
     * 格式化IP地址
     *
     * @param ipAdress
     * @return
     */
    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    /**
     * 获取rom版本号
     *
     * @return
     */
    public static String getRomVersion() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 获取总内存
     *
     * @return
     */
    public static String getTotalMemory() {//GB

        String path = "/proc/meminfo";
        String firstLine = null;
        String totalMemory = null;

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {
            totalMemory = df.format(Float.valueOf(firstLine) / (1024 * 1024));
        }

        return totalMemory + "GB";

    }

    /**
     * 获取剩余内存
     *
     * @param context
     * @return
     */
    public static String getFreeMemory(Context context) {

        String freeMemory;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(info);

        freeMemory = df.format(Float.valueOf(info.availMem) / (1024 * 1024 * 1024));

        return freeMemory + "GB";

    }


    /**
     * 获取权限对应的中文名称
     *
     * @param permission
     * @return
     */
    public static String getPermissionContent(String permission) {

        String permissionContent = "";

        switch (permission) {
            case Manifest.permission.READ_PHONE_STATE:
                permissionContent = "电话";
                break;

            default:
                break;
        }

        return permissionContent;
    }

    /**
     * 获取TSnackbar高度
     *
     * @return
     */
    public static int getTSnackbarHeight(Context context) {
        return (getActionBarSize(context));
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取ActionBar的高度
     *
     * @param context
     * @return
     */
    protected static int getActionBarSize(Context context) {
        int[] attrs = {android.R.attr.actionBarSize};
        TypedArray values = context.getTheme().obtainStyledAttributes(attrs);
        try {
            return values.getDimensionPixelSize(0, 0);//第一个参数数组索引，第二个参数 默认值
        } finally {
            values.recycle();
        }
    }

    /**
     * 获取cpu核数
     *
     * @return
     */
    public static String getNumberOfCPUCores() {

        int cores;

        String coresStr = "未知";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return "1";
        }

        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
            coresStr = String.valueOf(cores);
        } catch (Exception e) {
        }

        return coresStr;
    }

    /**
     * 获取CPU_FILTER
     */
    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 获取设备序列号
     *
     * @return
     */
    public static String getSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * 获取Api级别
     *
     * @return
     */
    public static String getApiLevel() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获取Build时间
     *
     * @return
     */
    public static String getBuildTime() {
        Long buildTime = Build.TIME;
        String date = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new java.util.Date(buildTime));
        return date;
    }


    public static DeviceInfo getDeviceInfo() {
        //SharedUtil.deleteItem(ADPlayerBackendService.getInstance().getApplicationContext(), "deviceInfo");
        Gson gson = new Gson();
        Context context = ADPlayerBackendService.getInstance().getApplicationContext();
        DeviceInfo info = new DeviceInfo();
        if (SharedUtil.getString(context, "deviceInfo", null) != null) {
            info = new Gson().fromJson(SharedUtil.getString(context, "deviceInfo", null), DeviceInfo.class);
        } else {
            info = new DeviceInfo();

            info.setId(DeviceUtil.getUniqueDeviceId(context));

            info.setIp(DeviceUtil.getWiFiInfo(context)[1]);
            info.setMac(DeviceUtil.getWiFiInfo(context)[2]);
            info.setResolution(getScreenInfo(ADPlayerBackendService.getInstance().getWindowManager()));

            info.setVersion(getOsVersion());
            info.setStartDate(new Date());
            info.setOnline(true);
            SharedUtil.putString(context, "deviceInfo", gson.toJson(info));
        }

        return info;
    }

    public static void setUserToken(UserToken userToken) {
        if (userToken == null) {
            SharedUtil.deleteItem(ADPlayerBackendService.getInstance().getApplicationContext(), "userToken");
        }
        Gson gson = new Gson();
        SharedUtil.putString(ADPlayerBackendService.getInstance().getApplicationContext(), "userToken", gson.toJson(userToken));
    }

    public static UserToken getUserToken() {
        Gson gson = new Gson();
        String userTokenString = SharedUtil.getString(ADPlayerBackendService.getInstance().getApplicationContext(), "userToken", null);
        if (TextUtils.isEmpty(userTokenString)) {
            return null;
        }
        return gson.fromJson(userTokenString, UserToken.class);
    }

    public static void setUserLogin(UserLogin userLogin) {
        Gson gson = new Gson();
        SharedUtil.putString(ADPlayerBackendService.getInstance().getApplicationContext(), "userLogin", gson.toJson(userLogin));
    }

    public static UserLogin getUserLogin() {
        Gson gson = new Gson();
        String userLoginString = SharedUtil.getString(ADPlayerBackendService.getInstance().getApplicationContext(), "userLogin", null);
        if (TextUtils.isEmpty(userLoginString)) {
            return null;
        }
        return gson.fromJson(userLoginString, UserLogin.class);
    }

    @SuppressLint("MissingPermission")
    public static String getUniqueDeviceId(Context context) {
        Context ctx = context.getApplicationContext();
        if (ctx == null) {
            ctx = context;
        } else {
            ctx = ((Application) ctx).getBaseContext();
        }

        String uniqueId = SharedUtil.getString(context, "devUniqueId", null);
        if (!TextUtils.isEmpty(uniqueId)) {
            Log.i(TAG, "读取到唯一设备ID：" + uniqueId);
            return uniqueId;
        }
        StringBuilder sb = new StringBuilder();

        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        if (tm != null) {
            deviceId = tm.getDeviceId();
            if (!TextUtils.isEmpty(deviceId)) {
                sb.append(deviceId);
            }
        }
        String androidId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            sb.append(androidId);
        }

        if ((TextUtils.isEmpty(androidId) || androidId.equals("9774d56d682e549c") || androidId.equals("0000000000000"))
                && TextUtils.isEmpty(deviceId)) {
            sb.append(UUID.randomUUID().toString());//随机生成UUID
        }

        sb.append(android.os.Build.BOARD);//获取设备基板名称
        sb.append(android.os.Build.BOOTLOADER);//获取设备引导程序版本号
        sb.append(android.os.Build.BRAND);//获取设备品牌
        sb.append(android.os.Build.CPU_ABI);//获取设备指令集名称（CPU的类型）
        sb.append(android.os.Build.CPU_ABI2);//获取第二个指令集名称
        sb.append(android.os.Build.DEVICE);//获取设备驱动名称
        sb.append(android.os.Build.DISPLAY);//获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
        sb.append(android.os.Build.FINGERPRINT);//设备的唯一标识。由设备的多个信息拼接合成。
        sb.append(android.os.Build.HARDWARE);//设备硬件名称,一般和基板名称一样（BOARD）
        sb.append(android.os.Build.ID);//设备版本号。
        sb.append(android.os.Build.MODEL);//获取手机的型号 设备名称。
        sb.append(android.os.Build.MANUFACTURER);//获取设备制造商
        sb.append(android.os.Build.PRODUCT);//整个产品的名称
        sb.append(android.os.Build.TAGS);//设备标签。如release-keys 或测试的 test-keys
        sb.append(android.os.Build.TYPE);//设备版本类型 主要为 "user" 或 "eng".
        sb.append(android.os.Build.USER);//设备用户名 基本上都为android -build
        sb.append(android.os.Build.VERSION.RELEASE);//获取系统版本字符串。如4.1.2 或2.2 或2.3等
        sb.append(android.os.Build.VERSION.CODENAME);//设备当前的系统开发代号，一般使用REL代替
        sb.append(android.os.Build.VERSION.INCREMENTAL);//系统源代码控制值，一个数字或者git hash值
        sb.append(android.os.Build.VERSION.SDK_INT);//系统的API级别 数字表示
        sb.append(android.os.Build.SERIAL);

        uniqueId = md5(sb.toString().trim().toUpperCase());
        Log.i(TAG, "生成唯一设备ID：" + uniqueId);
        SharedUtil.putString(context, "devUniqueId", uniqueId);
        return uniqueId;
    }


    private static String md5(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }

}