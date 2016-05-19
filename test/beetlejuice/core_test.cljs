(ns beetlejuice.core-test
  (:require-macros [cljs.test :refer (is deftest testing async run-all-tests use-fixtures)]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [beetlejuice.casperjs :as casperjs :refer [*casper* get-element-info]]
            [beetlejuice.form-examples-test :as form-examples]
            [beetlejuice.todo-list-test :as todo-list]
            [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [clojure.walk :refer [postwalk]]))

(enable-console-print!)

(casperjs/set-casper-options!
 {:logLevel     "debug"
  :onRunComplete #(println "...run complete...")
  :verbose      true
  :viewportSize {:width 400 :height 768}
  :pageSetting  {:loadImages true
                 :userAgent  "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Lambdarat/23.0.1062.57"}})


;; Totally optional, otherwise a vanilla Casper instance is create()ed for you

(.on *casper* "page.initialized" (fn [page] (.injectJs page "test/beetlejuice/phantomjs-shims.js")))

(.on *casper* "page.error" (fn [error trace] (.log js/console "error " error)))

(.on *casper* "remote.message" (fn [msg] (.log js/console "remote message: " msg)))

(set! (.-waitTimeout (.-options *casper*)) 10000)

(def suites [;;todo-list/tests
             ;;form-examples/fill-xpath-test
             ;;form-examples/get-element-hiccup-by-xpath-test
             ;;form-examples/get-element-hiccup-by-css-selector-test
             ])

(defn check
  [suites]
  (if-let [tests (first suites)]
    (do
      (tests)
      (casperjs/run #(check (rest suites))))
    (do
      (casperjs/log "Finished.")
      (casperjs/exit))))

;; (defn legacy
;;   ;; Runs the pre-clojure.test tests
;;   ;; TODO - remove after transitioning all tests
;;   []
;;   (casperjs/start)
;;   (casperjs/then #(casperjs/log "Starting..."))

;;   (casperjs/run #(check suites)))

(defn start-tests []
  (println "Starting tests...")
  (run-all-tests #"beetlejuice.*-test"))

(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (println "Finishing!")
  (casperjs/exit (if (cljs.test/successful? m) 0 1)))

;; Entrypoint

;;(legacy)
(start-tests)
