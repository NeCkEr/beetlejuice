(ns beetlejuice.todos
  (:require [beetlejuice.core :as beetlejuice]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(enable-console-print!)

(def new-TODOS ["finish beetlejuice"
                "write some tests"
                "do a beetlejuice presentation"
                "improve beetlejuice to allow xpath click"])

(defn clean-todos
  []
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li[4]//button")
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li[3]//button")
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li[2]//button")
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li[1]//button"))

(defn add-todos
  []
  (doseq [todo new-TODOS]
    (beetlejuice/fill-selectors "header#header" {"input[id='new-todo']" todo})))

(defn mark-as-done
  []
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li//label[text()='write some tests']/..//input[@type='checkbox']")
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li//label[text()='improve beetlejuice to allow xpath click']/..//input[@type='checkbox']"))

(defn assert-title
  [title]
  (let [page-title (beetlejuice/get-title)]
    (is (= page-title title))))

(defn assert-url
  [url]
  (let [page-url (beetlejuice/get-current-url)]
    (is (not= (re-find (re-pattern url) page-url) nil))))


