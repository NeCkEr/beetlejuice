(ns beetlejuice.core-test
  (:require-macros [cljs.test :refer (is deftest testing async)]
                   [cljs.core.async.macros :as am :refer [go]])
  (:require [cljs.test]
            [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.casperjs :as casperjs :refer [*casper* getElementInfo]]
            [beetlejuice.todos :refer [clean-todos add-todos mark-as-done]]))

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
    (go
      (casperjs/echo "BeetleJuice tests starting...")
      (let [app (<! (beetlejuice/get-element-hiccup "#app"))]
        (beetlejuice/mouse-move "#todo-list li:first-child")
        (let [list1-hiccup (<! (beetlejuice/get-element-hiccup "#todo-list li:nth-child(1)"))
              list2-hiccup (<! (beetlejuice/get-element-hiccup "#todo-list li:nth-child(2)"))]
          (println list1-hiccup)
          (println list2-hiccup)
          (beetlejuice/sreen-shot "1-index")
          (clean-todos)
          (beetlejuice/sreen-shot "2-cleaned-todos")
          (add-todos)
          (beetlejuice/sreen-shot "3-added-todos")
          (mark-as-done)
          (beetlejuice/sreen-shot "4-marked-done"))))))


(casperjs/run)