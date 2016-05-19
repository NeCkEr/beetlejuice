(ns beetlejuice.todos
  (:require-macros [cljs.core.async.macros :as am :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! alts! chan close! timeout]]
            [beetlejuice.casperjs :as casperjs]
            [beetlejuice.core :as beetlejuice]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(enable-console-print!)

(def new-TODOS ["finish beetlejuice"
                "write some tests"
                "do a beetlejuice presentation"
                "improve beetlejuice to allow xpath click"])

(defn clean-todos
  []
  (casperjs/then
   (fn []
     (casperjs/click-xpath "//*[@id='todoapp']//section[@id='main']//li[4]//button")
     (casperjs/click-xpath "//*[@id='todoapp']//section[@id='main']//li[3]//button")
     (casperjs/click-xpath "//*[@id='todoapp']//section[@id='main']//li[2]//button")
     (casperjs/click-xpath "//*[@id='todoapp']//section[@id='main']//li[1]//button"))))

(defn add-todos
  []
  (doseq [todo new-TODOS]
    (beetlejuice/fill-selectors "header#header" {"input[id='new-todo']" todo})))

(defn mark-as-done
  []
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li//label[text()='write some tests']/..//input[@type='checkbox']")
  (beetlejuice/click-xpath "//*[@id='todoapp']//section[@id='main']//li//label[text()='improve beetlejuice to allow xpath click']/..//input[@type='checkbox']"))

(defn assert-title
  [page-title]
  (beetlejuice/get-title (fn [title]
                           (is (= page-title title)))))

(defn assert-url
  [url]
  (beetlejuice/get-current-url (fn [page-url]
                                 (is (not= (re-find (re-pattern url) page-url) nil)))))

;(defn assert-meta-tag
;  [name description]
;  (println ">>>>" (beetlejuice/get-meta-tag name)))
;

(def old-todos ["Rename Cloact to Reagent"
                "Add undo demo"
                "Make all rendering async"
                "Allow any arguments to component functions"])

(defn assert-first-item
  []
  (beetlejuice/get-element-hiccup "#todo-list li:nth-child(1) label"
                                  (fn [label]
                                    (is (= label "Rename Cloact to Reagent")))))
