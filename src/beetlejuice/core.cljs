(ns beetlejuice.core
  (:require-macros [beetlejuice.macros :refer [asynchronize]]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs]
            [hickory.core :as hickory :refer [as-hiccup as-hickory parse parse-fragment]]
            [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [clojure.string :as string]
            [clojure.walk :refer [postwalk]]))

(defn click [sel]
  (asynchronize
    (casperjs/then ...)
    (casperjs/click sel)))

(defn click-xpath [sel]
  (asynchronize
    (casperjs/then ...)
    (casperjs/click-xpath sel)))

(defn mouse-move
  [sel]
  (asynchronize
    (casperjs/then ...)
    (casperjs/mouse-move sel)))

(defn remove-reactid
  [html]
  (string/replace html #"data-reactid=\"[a-zA-Z0-9:;\.\s\(\)\-\,\$]*\"" ""))

(defn remove-empty-maps
  "given a arbitriary form it checks if is a list or a vector and removes empty maps"
  [node]
  (if (or (vector? node) (list? node))
    (->> node
         (remove #(= {} %))
         vec)
    node))

(defn get-element-hiccup
  [el]
  (let [chan (chan 1)]
    (asynchronize
      (casperjs/then ...)
      (let [casper-html-info (first (casperjs/getElementInfo el))
            element-html     (remove-reactid (:html casper-html-info))
            parsed-frag (parse-fragment element-html)
            hiccup-map (first (map as-hiccup parsed-frag))
            hiccup-map (postwalk remove-empty-maps hiccup-map)]
        (>! chan hiccup-map)))
    chan))

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

(defn lets-wait
  [how-much]
  (asynchronize
    (casperjs/wait how-much ...)))

(defn screen-shot
  [name]
  (asynchronize
    (casperjs/then ...)
    (casperjs/wait 100 ...)
    (casperjs/capture (str "target/e2e_test_screenshots/" name ".png"))))

(defn scroll-to
  ([y] (casperjs/scroll-to y))
  ([x y] (casperjs/scroll-to x y)))

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