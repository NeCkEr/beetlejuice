(ns beetlejuice.casperjs
  (:refer-clojure :exclude [repeat]))

(def require-js js/require)

(def utils (require-js "utils"))

;;
;; Casper instance
;;
(def ^{:doc     "Default casper instance, can be overridden by using set-casper-options!"
       :dynamic true}
*casper* (.create (require-js "casper")))

;;
;; Casper mouse instance
;;
(def ^{:doc     "Default casper instance, can be overridden by using set-casper-options!"
       :dynamic true}
*mouse* (.create (require-js "mouse") *casper*))

(defn set-casper-options!
  "Redefine *casper* with specific options: http://casperjs.org/api.html#casper.options"
  [opts]
  (println opts)
  (set! *casper* (.create (require-js "casper") (clj->js opts))))


;;
;; Casper API
;;

(defn dump
  [s]
  (.dump utils (clj->js s)))

(defn then [f] (.then *casper* f))

(defn then-evaluate
  ([f] (.thenEvaluate *casper* f))
  ([f ctx] (.thenEvaluate *casper* f (clj->js ctx))))

(defn then-open
  ([url] (.thenOpen *casper* url))
  ([url f] (.thenOpen *casper* url f))
  ([url opts f] (.thenOpen *casper* url (clj->js opts) f)))

(defn then-open-and-evaluate
  ([url] (.thenOpenAndEvaluate *casper* url))
  ([url f] (.thenOpenAndEvaluate *casper* url f))
  ([url f ctx] (.thenOpenAndEvaluate *casper* url f (clj->js ctx))))

(defn back [] (.back *casper*))

(defn base64-encode
  ([s] (.base64encode *casper* s))
  ([s method data] (.base64encode *casper* s (name method) (clj->js data))))

(defn click [sel] (.click *casper* (name sel)))

(defn click-xpath
  [sel]
  (let [xpath {:type "xpath" :path sel}]
    (.click *casper* (.selectXPath (require-js "casper") (name sel)))))

(defn click-label
  ([s] (.clickLabel *casper* s))
  ([s tag] (.clickLabel *casper* s (name tag))))


(defn mouse-move
  [sel]
  (.move (.-mouse *casper*) (name sel)))


(defn capture
  ([path] (.capture *casper* path))
  ([path rect] (.capture *casper* path (clj->js rect)))
  ([path rect imgOptions] (.capture *casper* path (clj->js rect) (clj->js imgOptions))))

(defn capture-base64
  ([fmt] (.captureBase64 *casper* (name fmt)))
  ([fmt part] (.captureBase64 *casper* (name fmt) (if (string? part) part (clj->js part)))))

(defn capture-selector [out sel] (.captureSelector *casper* out (name sel)))

(defn clear [] (.clear *casper*))

(defn debug-html [] (.debugHTML *casper*))

(defn debug-page [] (.debugPage *casper*))

(defn die
  ([s] (.die *casper* s))
  ([s status] (.die *casper* s status)))

(defn download
  ([url out] (.download *casper* url out))
  ([url out method data] (.download *casper* url out (name method) (clj->js data))))

(defn each [a f] (.each *casper* a f))

(defn echo
  ([s] (.echo *casper* s))
  ([s style] (.echo *casper* s style)))

(defn evaluate
  ([f] (.evaluate *casper* f))
  ([f ctx] (.evaluate *casper* f (clj->js ctx))))

(defn evaluate-or-die
  ([f] (.evaluateOrDie *casper* f))
  ([f s] (.evaluateOrDie *casper* f s)))

(defn exit
  ([] (.exit *casper*))
  ([status] (.exit *casper* status)))

(defn element-exists? [sel] (.exists *casper* (name sel)))

(defn fetch-text [sel] (.fetchText *casper* (name sel)))

(defn forward [] (.forward *casper*))

(defn log
  ([s] (.log *casper* s))
  ([s level] (.log *casper* s (name level)))
  ([s level space] (.log *casper* s (name level) space)))

(defn fill
  ([sel data] (.fill *casper* (name sel) (clj->js data)))
  ([sel data submit?] (.fill *casper* (name sel) (clj->js data) submit?)))

(defn fill-selectors
  ([sel data] (.fillSelectors *casper* (name sel) (clj->js data)))
  ([sel data submit?] (.fillSelectors *casper* (name sel) (clj->js data) submit?)))

(defn fill-selectors-by-order
  [sel data]
  (doseq [[k v] data]
    (println (str k " " v))
    (fill-selectors sel {k v})))

(defn getElementInfo
  [el]
  (js->clj (.getElementsInfo *casper* (name el)) :keywordize-keys true))

(defn get-current-url [] (.getCurrentUrl *casper*))

(defn get-element-attribute [sel attr] (.getElementAttribute *casper* (name sel) (name attr)))

(defn get-element-bounds [sel]
  (js->clj (.getElementBounds *casper* (name sel)) :keywordize-keys true))

(defn get-global [name] (.getGlobal *casper* name))

(defn get-page-content [] (.getPageContent *casper*))

(defn get-title [] (.getTitle *casper*))

(defn mouse-event [type sel] (.mouseEvent *casper* (name type) (name sel)))

(defn open
  ([url] (.open *casper* url))
  ([url opts] (.open *casper* url (clj->js opts))))

(defn reload [f] (.reload *casper* f))

(defn repeat [n f] (.repeat *casper* n f))

(defn resource-exists? [url] (.resourceExists *casper* url))

(defn run
  ([] (.run *casper*))
  ([f] (.run *casper* f)))

(defn scroll-to
  ([y] (.scrollTo *casper* 0 y))
  ([x y] (.scrollTo *casper* x y)))

(defn set-http-auth [u p] (.setHttpAuth *casper* u p))

(defn start
  ([url] (.start *casper* url))
  ([url f] (.start *casper* url f)))


(defn user-agent [s] (.userAgent *casper* s))

(defn viewport [w h] (.viewport *casper* w h))

(defn visible? [sel] (.visible *casper* (name sel)))

(defn wait
  ([ms] (.wait *casper* ms))
  ([ms f] (.wait *casper* ms f)))

(defn wait-for
  ([pred] (.waitFor *casper* pred))
  ([pred f] (.waitFor *casper* pred f))
  ([pred f on-timeout] (.waitFor *casper* pred f on-timeout))
  ([pred f on-timeout time] (.waitFor *casper* pred f on-timeout time)))

(defn wait-for-xpath
  [path]
  (let [xpath {:type "xpath" :path path}]
    (.waitForSelector *casper* (.selectXPath (require-js "casper") (name path)))))

(defn wait-for-selector
  ([sel] (.waitForSelector *casper* (name sel)))
  ([sel f] (.waitForSelector *casper* (name sel) f))
  ([sel f on-timeout] (.waitForSelector *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitForSelector *casper* (name sel) f on-timeout time)))

(defn wait-while-selector
  ([sel] (.waitWhileSelector *casper* (name sel)))
  ([sel f] (.waitWhileSelector *casper* (name sel) f))
  ([sel f on-timeout] (.waitWhileSelector *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitWhileSelector *casper* (name sel) f on-timeout time)))

(defn wait-for-resource
  ([url] (.waitForResource *casper* url))
  ([url f] (.waitForResource *casper* url f))
  ([url f on-timeout] (.waitForResource *casper* url f on-timeout))
  ([url f on-timeout time] (.waitForResource *casper* url f on-timeout time)))

(defn wait-until-visible
  ([sel] (.waitUntilVisible *casper* (name sel)))
  ([sel f] (.waitUntilVisible *casper* (name sel) f))
  ([sel f on-timeout] (.waitUntilVisible *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitUntilVisible *casper* (name sel) f on-timeout time)))

(defn wait-while-visible
  ([sel] (.waitWhileVisible *casper* (name sel)))
  ([sel f] (.waitWhileVisible *casper* (name sel) f))
  ([sel f on-timeout] (.waitWhileVisible *casper* (name sel) f on-timeout))
  ([sel f on-timeout time] (.waitWhileVisible *casper* (name sel) f on-timeout time)))


(defn wait-for-url
  ([url] (.waitForUrl *casper* (name url)))
  ([url f] (.waitForUrl *casper* (name url) f))
  ([url f on-timeout] (.waitForUrl *casper* (name url) f on-timeout))
  ([url f on-timeout time] (.waitForUrl *casper* (name url) f on-timeout time)))

(defn warn [s] (.warn *casper* s))


(defn zoom [n] (.zoom *casper* n))
