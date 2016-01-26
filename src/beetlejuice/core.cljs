(ns beetlejuice.core
  (:refer-clojure :exclude [repeat])
  (:require-macros [beetlejuice.macros :refer [asynchronize]]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs]
            [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]))

(defn click [sel]
  (asynchronize
    (casperjs/then ...)
    (casperjs/click sel)))

(defn wait-for-selector
  [sel]
   (asynchronize
     (casperjs/then ...)
     (casperjs/wait-for-selector sel)))

(defn wait-for-xpath
  [path]
  (asynchronize
    (casperjs/then ...)
    (casperjs/wait-for-xpath path)))

(defn sreen-shot
  [name]
  (asynchronize
    (casperjs/then ...)
    (casperjs/wait 100 ...)
    (casperjs/capture (str "target/e2e_test_screenshots/" name ".png"))))

(defn fill-selectors
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-selectors sel data false)))

(defn fill-selectors-by-order
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-selectors-by-order sel data)))