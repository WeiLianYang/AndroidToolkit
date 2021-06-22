# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# * 混淆日志生成目录:app.build.outputs.mapping下,打包时请拷贝此日志,用于错误日志还原分析.
#--------------------------------------------- 公共配置 start ----------------------------------------#
-optimizationpasses 5 # 代码混淆压缩比
-dontusemixedcaseclassnames   # 混合时不使用大小写混合，混合后的类名为小写
-dontskipnonpubliclibraryclasses #  指定不去忽略非公共的库类。
-dontskipnonpubliclibraryclassmembers # 指定不去忽略非公共库的类成员
-dontpreverify  # 混淆时不做预校验
-verbose # 使我们的项目混淆后产生映射文件 : 包含有类名->混淆后类名的映射关系
-ignorewarnings # 忽略所有警告
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*# 指定混淆是采用的算法，后面的参数是一个过滤器(谷歌推荐的算法，一般不做更改)

-keepattributes *Annotation*,InnerClasses #保留注解不混淆
-keepattributes Signature # 避免混淆泛型
-keepattributes SourceFile,LineNumberTable# 抛出异常时保留代码行号
#--------------------------------------------- 公共配置 end ----------------------------------------#


#--------------------------------------------- Android 系统 start ----------------------------------------#
-keep class android.net.*
-keep public class * extends android.view.View
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class **.R$* { *; }# 保留R下面的资源
-keep class android.support.** { *; }# 保留support下的所有类及其内部类
-keep public class * extends androidx.annotation.**
#保留在Activity中的方法参数是view的方法(这样以来我们在layout中写的onClick就不会被影响)
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
# 保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 对于带有回调函数的**on*Event、**On*Listener 不被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# 不混淆使用了 @Keep 注解相关的类
-keep class androidx.annotation.Keep

-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

#--------------------------------------------- Android 系统 end ----------------------------------------#


#--------------------------------------------- Java start ----------------------------------------#
# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留JsonObject不被混淆
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keep class * implements java.io.Serializable { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#--------------------------------------------- Java End ----------------------------------------#


#--------------------------------------------- Kotlin start ----------------------------------------#
# Kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}
#--------------------------------------------- Kotlin end ----------------------------------------#


#--------------------------------------------- 第三方依赖 start ----------------------------------------#

# androidx
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

#--------------------------------------------- 第三方依赖 end ----------------------------------------#



#--------------------------------------------- 项目混淆 处理 start ----------------------------------------#

# 入口
-keep class com.william.toolkit.ToolkitPanel{*;}
-keep class com.william.toolkit.manager.DataManager{*;}

# 项目中实体bean
-keep class com.william.toolkit.bean.**{*;}

#--------------------------------------------- 项目混淆 处理 end ----------------------------------------#
