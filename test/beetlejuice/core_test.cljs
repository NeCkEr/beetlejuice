(ns beetlejuice.core-test
  (:require [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper*]]))

(enable-console-print!)

;; Totally optional, otherwise a vanilla Casper instance is create()ed for you

(.on *casper* "page.initialized" (fn [page] (.injectJs page "test/beetlejuice/phantomjs-shims.js")))

(.on *casper* "page.error" (fn [error trace] (.log js/console "error " error)))

(.on *casper* "remote.message" (fn [msg] (.log js/console "remote message: " msg)))

(casperjs/set-casper-options!
  {:logLevel     "error"
   :verbose      true
   :viewportSize {:width 400 :height 768}
   :pageSetting  {:loadImages true
                  :userAgent  "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Lambdarat/23.0.1062.57"}})


(casperjs/start
  "resources/reagent-todo/index.html"
  (fn []
    (casperjs/echo "Casper tests starting...")
    (beetlejuice/sreen-shot "index")))

(casperjs/run)




