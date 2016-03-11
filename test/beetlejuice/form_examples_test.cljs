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
      (casperjs/echo "get-element-hiccup-by-xpath-test starting...")
      (go
        (let [e (<! (beetlejuice/get-element-hiccup {:type "xpath" :path "//select[@id='dropdown-1']"}))]
          (casperjs/echo (str "Element found by XPath: " e))
          (is (= e [:option {:value "1", :selected ""} "check"])))))))

(defn get-element-hiccup-by-css-selector-test
  []
  (casperjs/start
    "resources/form-examples/form.html"
    (fn []
      (casperjs/echo "get-element-hiccup-by-css-selector-test starting...")
      (go
        (let [e (<! (beetlejuice/get-element-hiccup "#dropdown-1"))]
          (casperjs/echo (str "Element found by CSS selector: " e))
          (is (= e [:option {:value "1", :selected ""} "check"])))))))
