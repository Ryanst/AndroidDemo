# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/kevin/software/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontpreverify
-verbose

-optimizations !code/simplification/arithmetic,!field/\*,!class/merging/\*

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

#开启keep支持
-dontskipnonpubliclibraryclassmembers
-printconfiguration
-keep,allowobfuscation @interface android.support.annotation.Keep

-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}

-keepattributes Signature
-keepattributes *Annotation*
-keep interface android.support.v4.app.** { *; }

-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.v8.**

-keep public class * extends android.support.v4.widget
-keep public class * extends com.sqlcrypt.database
-keep public class * extends com.sqlcrypt.database.sqlite
-keep public class * extends com.treecore.**

-keepclasseswithmembers class * {	# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class com.tal.app.seaside.R$*{
public static final int *;
}

-keep class com.tal.app.core.R$*{
public static final int *;
}
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keep class com.tal.app.seaside.bean.**{*;}

-keepclassmembers class **.R$* {
    public static <fields>;
}
-dontwarn android.support.**
-keep class com.tal.app.seaside.widget.**{*;}

#==================jpush==========================
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

#==================retrofit2======================
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class com.tal.app.seaside.viewmodel.**

#umeng
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

-keep class com.marshalchen.**
-keepclassmembernames class com.marshalchen.**{*;}
-keep class okio.**

-dontwarn fi.foyt.foursquare.**

-dontnote libcore.icu.ICU
-dontnote sun.misc.Unsafe

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}

-dontwarn org.mortbay.**
-dontwarn org.slf4j.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.commons.codec.binary.**

-dontwarn javax.persistence.**
-keep class javax.persistence.**
-dontwarn java.nio.file.**
-keep class org.codehaus.mojo.animal_sniffer.**
-dontwarn org.codehaus.mojo.**
-dontwarn com.marshalchen.ultimaterecyclerview.**

-keepattributes *Annotation*
-keepattributes InnerClasses

-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-keep class android.net.http.** { *; }
-dontwarn android.net.http.**

-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
    public void xxxxxx(**);
}
-keep class org.greenrobot.event.**
-keep class org.greenrobot.event.** { *; }
-keep class org.greenrobot.event.util { *; }

-dontnote org.greenrobot.**
-keepclassmembers class ** {
    public void onEvent*(**);
}

-keepclassmembers class * {
   private native <methods>;
   public native <methods>;
   protected native <methods>;
   public static native <methods>;
   private static native <methods>;
   static native <methods>;
   native <methods>;
}
-keep class com.tal.app.seaside.activity.**
-keep interface org.greenrobot.event.**{*;}
-keepclassmembers class com.tal.app.seaside.activity.**{
    public void onEvent();
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keepclassmembers class ** {
    public void onEvent*(**);
}

-keepclassmembers class com.tal.app.seaside.activity.*{
    public void onEvent*(**);
}

-keep class org.greenrobot.eventbus.** { *;}

-keep class uk.co.senab.photoview.**

#-keepclassmembers class {
#    public void onEvent*();
#
#}

#-keepclassmembers class {
#
#public void xxxxxx(); //所有监听的方法都要列在这里
#
#}

