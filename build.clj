(ns _ (:require [".github/vendor/make/0.3.0/main" :as m]))

(m/build
 {:deps ["android_app:0.1.0"
         "chat_ui:0.2.0"
         "effects:0.2.0"
         "effects_tools:0.1.0"
         "random:0.1.0"]
  :compile [{:target "eval"
             :src "build/manifest.clj"
             :out ".github/android/app/src/main/AndroidManifest.xml"}
            {:target "java"
             :root "src"
             :namespace "app"
             :out-dir ".github/android/app/src/main/java/app"}
            {:target "java"
             :root "test"
             :namespace "app"
             :out-dir ".github/android/app/src/test/java/app"}]})
