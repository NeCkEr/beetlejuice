(ns beetlejuice.core
  (:require-macros [beetlejuice.macros :refer [asynchronize]]
<<<<<<< HEAD
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs :refer [*casper*]]
            [hickory.core :as hickory :refer [as-hiccup as-hickory parse parse-fragment]]
=======
                   [cljs.core.async.macros :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs]
            [hickory.core :refer [as-hiccup as-hickory parse parse-fragment]]
>>>>>>> 4b4910bfba609947b272c83d0814ddfca4ae4cf2
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
            element-html (remove-reactid (:html casper-html-info))
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
    (casperjs/capture (str "target/test/screenshots/" name ".png"))))

(defn scroll-to
  [x y]
  (asynchronize
    (casperjs/then ...)
    (casperjs/scroll-to x y)))

(defn set-viewport
  [w h]
  (asynchronize
    (casperjs/then ...)
    (casperjs/viewport w h))
  )

;(defn with-frame
;  [frame]
;  (asynchronize
;    (casperjs/with-frame frame)))

(defn fill-selectors
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-selectors sel data false)))

(defn switch-to-frame [frame]
  ;; TODO validate if the frame exists check:
  ;; https://github.com/n1k0/casperjs/blob/8d561c2774653a817f9a1b09b741eb40c5ed4c1a/modules/casper.js#L2366
  (asynchronize
    (casperjs/then ...)
    (let [page (.-page *casper*)]
      (.switchToChildFrame page frame))))


(defn switch-to-parent-frame []
  (asynchronize
    (casperjs/then ...)
    (let [page (.-page *casper*)]
      (.switchToParentFrame page))))


(defn with-frame
  [frame]
  (asynchronize
    (.withFrame *casper* frame ...)
    (println "changed to frame:" frame)
    (casperjs/click-xpath "//*[@id=\"app\"]/div/div/section/div/main/div/div/div/div/div/div[2]/div/div/a")))


(defn fill-selectors-by-order
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-selectors-by-order sel data)))