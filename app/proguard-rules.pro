-ignorewarnings
#-dontshrink
#-dontoptimize
#-dontobfuscate
-dontskipnonpubliclibraryclasses

-renamesourcefileattribute SourceFile

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View { public <init>(android.content.Context); public <init>(android.content.Context, android.util.AttributeSet); public <init>(android.content.Context, android.util.AttributeSet, int); public void set*(...); }
-keep public class * extends android.preference.Preference { *; }

-keepclassmembers class * extends android.app.Activity { public void *(android.view.View); }

# -- support-v4 --
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keepclassmembers class android.support.v4.app.Fragment { *** getActivity(); public *** onCreate(); public *** onCreateOptionsMenu(...); }
-keepclassmembers class android.support.v4.view.ViewPager { private <fields>; }

#科大讯飞
-keep class com.iflytek.**{*;}

# -- ButterKnife Start --
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# -- ButterKnife End --

#-- Litepal Start--
-dontwarn org.litepal.**
-keep enum org.litepal.**
-keep interface org.litepal.** { *; }
-keep public class * extends org.litepal.**
-keepattributes *Annotation*
-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
}

-keepclassmembers class * extends org.litepal.crud.DataSupport{*;}
#-- Litepal End--
