(ns beetlejuice.core
  (:refer-clojure :exclude [repeat])
  (:require-macros [beetlejuice.macros :refer [asynchronize]]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs]
            [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]))

(defn click [sel]
  (asynchronize
    (casperjs/then ...)
    (println "clickk!!")
    (casperjs/click sel)))

(defn wait-for-selector
  [sel]
   (asynchronize
     (casperjs/then ...)
     (println "wait for selector")
     (casperjs/wait-for-selector sel)))

(defn sreen-shot
  [name]
  (asynchronize
    (casperjs/then ...)
    (casperjs/wait 100 ...)
    (println "ss!")
    (casperjs/capture (str "resources/e2e_test_ss/" name ".png"))))

(defn fill-selectors
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (println "fill-selectors")
    (casperjs/fill-selectors sel data)))

(defn fill-selectors-by-order
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (println "fill-selectors-by-order")
    (casperjs/fill-selectors-by-order sel data)))