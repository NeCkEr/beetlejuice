(ns beetlejuice.form-examples-test
  (:require-macros [cljs.test :refer (is deftest testing async use-fixtures)]
                   [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper*]]
            [clojure.walk :refer [postwalk]]))

;; (defn fill-xpath-test
;;   []
;;   (casperjs/start
;;     "resources/form-examples/form.html"
;;     (fn []
;;       (casperjs/echo "BeetleJuice tests starting...")
;;       (beetlejuice/screen-shot "forms-01-index")
;;       (beetlejuice/fill-xpath "table#table-1" {"//select[@id='dropdown-1']" "2"})
;;       (beetlejuice/lets-wait 300)
;;       (beetlejuice/screen-shot "forms-02-approved"))))

(defn start-form []
  (async done
         (casperjs/start "resources/form-examples/form.html")
         (done)))

(defn close-form []
  ;; no-op
  )

(use-fixtures :each
  {:before start-form
   :after close-form})

(deftest fill-xpath-test
  (casperjs/echo "BeetleJuice tests starting...")
  (beetlejuice/screen-shot "forms-01-index")
  (beetlejuice/fill-xpath "table#table-1" {"//select[@id='dropdown-1']" "2"})
  (beetlejuice/lets-wait 300)
  (beetlejuice/screen-shot "forms-02-approved")
  (async done
         (casperjs/run done)))

(deftest get-element-hiccup-by-xpath-test
  (println "get-element-hiccup-by-xpath-test starting...")
  (async done
         (go
           (let [c (beetlejuice/get-element-hiccup {:type "xpath" :path "//select[@id='dropdown-1']"})
                 _ (casperjs/run)
                 e (<! c)]
             (casperjs/echo (str "Element found by XPath: " e))
             (is (= e [:option {:value "1", :selected ""} "check"]))
             (println "Finished asserting")
             (done)))))

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
