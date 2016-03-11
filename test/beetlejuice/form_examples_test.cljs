(ns beetlejuice.form-examples-test
  (:require-macros [cljs.test :refer (is deftest testing async)]
                   [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper* getElementInfo]]
            [clojure.walk :refer [postwalk]]))

(defn fill-xpath-test
  []
  (casperjs/start
    "resources/form-examples/form.html"
    (fn []
      (casperjs/echo "BeetleJuice tests starting...")
      (beetlejuice/screen-shot "forms-01-index")
      (beetlejuice/fill-xpath "table#table-1" {"//select[@id='dropdown-1']" "2"})
      (beetlejuice/lets-wait 300)
      (beetlejuice/screen-shot "forms-02-approved"))))

(defn get-element-hiccup-by-xpath-test
  []
  (casperjs/start
    "resources/form-examples/form.html"
    (fn []
      (casperjs/echo "BeetleJuice tests starting...")
      (beetlejuice/screen-shot "hiccup-01-index")
      (go
        ;(let [e (<! (beetlejuice/get-element-hiccup {:type "xpath" :path "//select[@id='dropdown-1']"}))]
        (let [e (<! (beetlejuice/get-element-hiccup "#label-1"))]
        (print (str "LABEL1: " e))
        (is e)))
      ;(is (not (<! (beetlejuice/get-element-hiccup {:type "xpath" :path "//select[@id='not-existing-element']"}))))
      (beetlejuice/lets-wait 300))))
