(ns beetlejuice.test-utils
  (:require [clojure.string :as str]))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; HACK: will work only if you don't use advanced optimization
;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn ->js [var-name]
  (-> var-name
      (str/replace #"/" ".")
      (str/replace #"-" "_")))

(defn invoke [function-name & args]
  (let [fun (js/eval (->js function-name))]
    (apply fun args)))
