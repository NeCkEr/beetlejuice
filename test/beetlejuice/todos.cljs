(ns beetlejuice.todos
  (:require [beetlejuice.core :as beetlejuice]))

(enable-console-print!)

(def TODOS ["finish beetlejuice"
            "write some tests"
            "do a beetlejuice presentation"
            "improve beetlejuice"])

(defn clean-todos
      []
      ;(beetlejuice/wait-for-selector "#todoapp")
      (beetlejuice/wait-for-xpath "//*[@id='todoapp']"))

(defn add-todos
      []
      )

(defn mark-as-done
      [])
