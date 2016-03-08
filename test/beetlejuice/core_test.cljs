(ns beetlejuice.core-test
  (:require-macros [cljs.test :refer (is deftest testing async)]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper*]]
            [beetlejuice.todos  :as testscripts]
            [clojure.walk :refer [postwalk]]))

(enable-console-print!)

(casperjs/set-casper-options!
  {:logLevel     "debug"
   :verbose      true
   :viewportSize {:width 400 :height 768}
   :pageSetting  {:loadImages true
                  :userAgent  "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Lambdarat/23.0.1062.57"}})


;; Totally optional, otherwise a vanilla Casper instance is create()ed for you

(.on *casper* "page.initialized" (fn [page] (.injectJs page "test/beetlejuice/phantomjs-shims.js")))

(.on *casper* "page.error" (fn [error trace] (.log js/console "error " error)))

(.on *casper* "remote.message" (fn [msg] (.log js/console "remote message: " msg)))

(set! (.-waitTimeout (.-options *casper*)) 10000)

(casperjs/start
  "resources/reagent-todo/index.html"
  (fn []
    (casperjs/echo "BeetleJuice tests starting...")
    (beetlejuice/mouse-move "#todo-list li:first-child")
    (testscripts/assert-first-item)
    (testscripts/assert-title "Todo List")
    (testscripts/assert-url "reagent-todo/index.html")
    ;(assert-meta-tag "description" "Todo List app for all your todo needs.")
    (beetlejuice/screen-shot "1-index")
    (testscripts/clean-todos)
    (beetlejuice/screen-shot "2-cleaned-todos")
    (testscripts/add-todos)
    (beetlejuice/screen-shot "3-added-todos")
    (testscripts/mark-as-done)
    (beetlejuice/screen-shot "4-marked-done")))


(casperjs/run)