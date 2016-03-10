(ns beetlejuice.form-examples-test
  (:require-macros [cljs.test :refer (is deftest testing async)])
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
      (beetlejuice/screen-shot "forms-02-approved"))))
