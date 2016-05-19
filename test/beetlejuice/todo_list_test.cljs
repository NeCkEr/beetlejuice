(ns beetlejuice.todo-list-test
  (:require-macros [cljs.test :refer (is deftest testing async use-fixtures)]
                   [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.casperjs :as casperjs]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.todos :as testscripts]))

(defn start-todo []
  (async done
    (println (casperjs/start "./resources/reagent-todo/index.html" done))
    (casperjs/run)))

(defn close-todo []
  ;; no-op
  )

(use-fixtures :each
  {:before start-todo
   :after  close-todo})

(deftest todo-app
  (casperjs/echo "BeetleJuice tests starting...")
  (async done
    (let [first-item (testscripts/get-first-item)
          page-title (beetlejuice/get-title)
          page-url   (beetlejuice/get-current-url)]
      (is (= first-item "Rename Cloact to Reagent"))
      (is (= page-title "Todo List"))
      (is (re-find #"reagent-todo/index\.html" page-url))
      (casperjs/mouse-move "#todo-list li:first-child")
      (beetlejuice/screen-shot "todo-01-index")
      (testscripts/clean-todos)
      (let [clicked (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li//label[text()='Rename Cloact to Reagent']/..//input[@type='checkbox']")]
        (println clicked test)
        (casperjs/wait 100 (fn []
                             (println clicked)
                             (casperjs/mouse-move "#todo-list li:last-child")
                             (beetlejuice/screen-shot "todo-02-cleaned-todos")
                             (done)
                             )))

      ))

  ;(beetlejuice/mouse-move "#todo-list li:first-child")
  ;(async done
  ;  (go
  ;
  ;    (casperjs/run (fn []
  ;                    (println "something")
  ;                    (done)
  ;                    ))))

  ;; TODO: implement the get-meta-tags somehow
  ;;(assert-meta-tag "description" "Todo List app for all your todo needs.")




  ;(testscripts/add-todos)
  ;(beetlejuice/screen-shot "todo-03-added-todos")
  ;(testscripts/mark-as-done)
  ;(beetlejuice/lets-wait 300)
  ;(beetlejuice/screen-shot "todo-04-marked-done")
  )

;(defn tests []
;  (casperjs/start
;    "resources/reagent-todo/index.html"
;    (fn []
;      (casperjs/echo "BeetleJuice tests starting...")
;      (beetlejuice/mouse-move "#todo-list li:first-child")
;      (testscripts/assert-first-item)
;      (testscripts/assert-title "Todo List")
;      (testscripts/assert-url "reagent-todo/index.html")
;      ;(assert-meta-tag "description" "Todo List app for all your todo needs.")
;      (beetlejuice/screen-shot "todo-01-index")
;      (testscripts/clean-todos)
;      (beetlejuice/screen-shot "todo-02-cleaned-todos")
;      (testscripts/add-todos)
;      (beetlejuice/screen-shot "todo-03-added-todos")
;      (testscripts/mark-as-done)
;      (beetlejuice/lets-wait 300)
;      (beetlejuice/screen-shot "todo-04-marked-done"))))
