(ns beetlejuice.core
  (:require-macros [beetlejuice.macros :refer [asynchronize]]
                   [cljs.core.async.macros :refer [go]])
  (:import goog.string)
  (:require [beetlejuice.casperjs :as casperjs :refer [*casper*]]
            [hickory.core :refer [as-hiccup as-hickory parse parse-fragment]]
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

(defn cleanHTML
  "inlines all the HTML elements removing whitespaces newlines and tabs"
  [html]
  (-> html
      (string/replace #">\s+" ">")
      (string/replace #"\s+<" "<")
      (string/replace #"\n|\r|\t" "")))

(defn remove-empty-maps
  "given a arbitriary form it checks if is a list or a vector and removes empty maps"
  [node]
  (if (or (vector? node) (list? node))
    (->> node
         (remove #(= {} %))
         vec)
    node))

(defn- element-hiccup
  [c el]
  (try
    (let [casper-html-info (first (casperjs/get-element-info (clj->js el)))
          element-html     (remove-reactid (cleanHTML (:html casper-html-info)))
          parsed-frag      (parse-fragment element-html)
          hiccup-map       (first (map as-hiccup parsed-frag))
          hiccup-map       (postwalk remove-empty-maps hiccup-map)]
      (go
        (>! c hiccup-map)))
    (catch js/Error _
      (go
        (>! c [])))))

(defn get-element-hiccup
  [el]
  (let [c (chan 1)]
    (casperjs/then #(element-hiccup c el))
    c))

(defn get-element-info
  [selector]
  (let [chan (chan 1)]
    (asynchronize
      (casperjs/then ...)
      (>! chan (casperjs/get-element-info selector)))
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
    (casperjs/capture (str "target/test/screenshots/" name "-" (.getRandomString string) ".png"))))

(defn scroll-to
  ([y]
   (asynchronize
     (casperjs/then ...)
     (casperjs/scroll-to y)))
  ([x y]
   (asynchronize
     (casperjs/then ...)
     (casperjs/scroll-to x y))))

(defn set-viewport
  [w h]
  (asynchronize
    (casperjs/then ...)
    (casperjs/viewport w h))
  )

(defn fill-selectors
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-selectors sel data false)))

(defn fill-xpath
  [sel data]
  (asynchronize
    (casperjs/then ...)
    (casperjs/fill-xpath sel data false)))

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


(defn element-exists?
  [sel]
  (let [chan (chan 1)]
    (asynchronize
      (casperjs/then ...)
      (>! chan (casperjs/element-exists? sel)))
    chan))

(defn get-title
  []
  (casperjs/get-title))

(defn get-current-url
  []
  (casperjs/get-current-url))

;(defn get-meta-tag
;  [name]
;  (asynchronize
;    (casperjs/then ...)
;    (casperjs/evaluate (.prototype.slice.call js/Array (.getElementsByTagName js/document "META")))
;    ))

(defn refresh-page []
  (asynchronize
    (casperjs/reload ...)))

(defn open-page [url]
  (asynchronize
    (casperjs/then ...)
    (casperjs/open url)))

(defn clear-local-storage []
  (asynchronize
    (casperjs/then ...)
    (casperjs/then-evaluate (fn []
                              (.clear (.-localStorage js/window))))))
