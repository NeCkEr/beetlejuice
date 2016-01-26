(ns beetlejuice.core-test
  (:require-macros [cljs.test :refer (is deftest testing async)])
  (:require [cljs.test]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper*]]))

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
      (casperjs/echo "Casper tests starting...")
      (beetlejuice/sreen-shot "index")
      (beetlejuice/wait-for-xpath "//*[@id='todoapp']")
      (beetlejuice/click-xpath "//*[@id='todoapp']")
      (beetlejuice/sreen-shot "index")))

 (casperjs/run)


