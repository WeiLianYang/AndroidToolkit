
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

