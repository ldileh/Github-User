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

# ----- General Android Rules -----
# Keep class names for serialization
-keepattributes *Annotation*

# Prevent obfuscation of classes with @Keep annotation
-keep @androidx.annotation.Keep class *

# Keep Parcelable classes and their CREATOR field
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep all View Binding classes
-keep class **Binding { *; }

# ----- Dagger Hilt -----
# Keep Hilt-generated components
-keep class dagger.hilt.** { *; }
-keep class *Injector { *; }

# Keep all Hilt-related classes
-keep class * extends dagger.hilt.EntryPoint { *; }
-keep class * extends dagger.hilt.InstallIn { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel { *; }

# ----- Room Database -----
# Keep Room entities and DAO interfaces
-keep class androidx.room.Entity { *; }
-keep class androidx.room.Dao { *; }
-keep class androidx.room.Database { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

# Keep Room schema location (useful for debugging)
-keepattributes *Annotation*
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Dao class * { *; }

# ----- Paging 3 -----
# Keep Paging 3 models and database access objects
-keep class androidx.paging.PagingSource { *; }
-keep class androidx.paging.PagedList { *; }

# ----- Coil Image Loading -----
# Keep Coil's generated Glide classes
-keep class coil.** { *; }
-keep class coil.request.** { *; }
-keep class coil.decode.** { *; }

# ----- Retrofit & OkHttp -----
# Keep Retrofit API interfaces
-keep interface retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep Retrofit models (if using Gson/Moshi serialization)
-keep class com.ldileh.githubuser.models.** { *; }

# If using Gson for JSON parsing, keep fields with @SerializedName annotation
-keep class * { @com.google.gson.annotations.SerializedName <fields>; }

# ----- ViewModel & LiveData -----
# Keep ViewModels and LiveData classes
-keep class androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * {
    @androidx.lifecycle.LiveData <fields>;
}

# ----- Kotlin Coroutines -----
# Keep Coroutines internal APIs
-keep class kotlinx.coroutines.** { *; }

# ----- Prevent Reflection Issues -----
# Keep classes with @Keep annotation
-keep @androidx.annotation.Keep class *
-keep @kotlin.Metadata class *

# ----- Logging (Remove Debug Logs in Release) -----
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

