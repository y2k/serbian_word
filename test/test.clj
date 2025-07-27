(ns _ (:import [org.junit Test])
    (:require ["./main" :as app]
              ["./vendor/random/index" :as r]
              ["./vendor/effects_tools/date" :as date]))

(gen-class :name Tests :prefix "test_"
           :methods [[^Test home [] void]
                     [^Test random [] void]])

(defn- ui_mock_effect_handler [w]
  (assoc w :chat_ui:update (fn [_] [nil nil])))

(defn- test_home [_]
  (let [time (date/create_mock 0.0)
        w (->> {}
               (date/mock_effect_handler time)
               (ui_mock_effect_handler))]
    ((app/home) w)))

(defn- assert_ [expected actual]
  (if (not= expected actual)
    (FIXME expected " != " actual)))

(defn- test_random [_]
  (assert_
   [0.16134452338393057
    [0.16134452338393057
     0.5248822872642811
     0.7804527133845038
     0.557499772197334
     0.47213594590878855]]
   (r/seq 0.0 5)))
