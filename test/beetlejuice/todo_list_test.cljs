(ns beetlejuice.todo-list-test
  (:require [beetlejuice.casperjs :as casperjs]
            [beetlejuice.core :as beetlejuice]
            [beetlejuice.todos :as testscripts]))


(defn tests []
  (casperjs/start
    "resources/reagent-todo/index.html"
    (fn []
      (casperjs/echo "BeetleJuice tests starting...")
      (beetlejuice/mouse-move "#todo-list li:first-child")
      (testscripts/assert-first-item)
      (testscripts/assert-title "Todo List")
      (testscripts/assert-url "reagent-todo/index.html")
      ;(assert-meta-tag "description" "Todo List app for all your todo needs.")
      (beetlejuice/screen-shot "todo-01-index")
      (testscripts/clean-todos)
      (beetlejuice/screen-shot "todo-02-cleaned-todos")
      (testscripts/add-todos)
      (beetlejuice/screen-shot "todo-03-added-todos")
      (testscripts/mark-as-done)
      (beetlejuice/lets-wait 300)
      (beetlejuice/screen-shot "todo-04-marked-done"))))
