(defproject beetlejuice "0.2.1-SNAPSHOT"

  :hooks [leiningen.cljsbuild]
  :recompile-dependents false

  :description "ClojureScript bindings for CasperJS. With added ectoplasm."

  :url "https://github.com/cncommerce/beetlejuice"

  :scm {:name "git"
        :url  "https://github.com/cncommerce/beetlejuice"}

  :license {:name         "MIT"
            :url          "https://opensource.org/licenses/MIT"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]

                 [hickory "0.6.0"]]

  :plugins [[lein-shell "0.4.0"]
            [lein-cljsbuild "1.1.2"]]

  :profiles {:test {:cljsbuild {:test-commands {"test" ["casperjs" "target/test/app.js"]}
                                :builds        [{:id           "test"
                                                 :source-paths ["src" "test"]
                                                 :compiler     {:main          beetlejuice.core-test
                                                                :output-to     "target/test/app.js"
                                                                :output-dir    "target/test"
                                                                :source-map    "target/test/app.js.map"
                                                                :optimizations :whitespace
                                                                :parallel-build true
                                                                :recompile-dependents false
                                                                :compiler-stats true
                                                                :pretty-print  false}}]}}}

  :aliases {"test"          ["do" "clean-test-SS" ["with-profiles" "-dev,+test" "do" ["cljsbuild" "test"]]]
            "clean-test"    ["do" "clean-test-SS" ["shell" "rm" "-rf" "target/test"]]
            "clean-test-SS" ["do" ["shell" "rm" "-rf" "target/test/e2e_test_screenshots"]]}

  :deploy-repositories [["releases" :clojars]])
