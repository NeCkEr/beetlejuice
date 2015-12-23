(ns beetlejuice.macros)


(defn- add-argument-last [form arg]
  "returns the form without `...`"
  `(~@form ~arg))


(defn- callback [sc]
  `(fn []
     (cljs.core.async.macros/go
       (~'>! ~sc "weeee"))))


(defn- close-chan [sc]
  `(let [v# (~'<! ~sc)]
     (cljs.core.async/close! ~sc)))


(defn- transform [forms]
  (if (list? forms)
    (if (= (last forms) '...)
      (let [sc (gensym)]
        `(let [~sc (cljs.core.async/chan)]
           (do
             ~(add-argument-last (map transform (butlast forms)) (callback sc))
             ~(close-chan sc))))
      (map transform forms))
    forms))


(defmacro asynchronize [& forms]
  `(cljs.core.async.macros/go
     ~@(map transform forms)))
