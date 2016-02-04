(defproject beetlejuice "0.1.0-SNAPSHOT"

  :description "CasperJS single-page application testing tools for ClojureScript"

  :url "https://github.com/cncommerce/beetlejuice"

  :license {:name         "MIT"
            :url          "https://opensource.org/licenses/MIT"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [hickory "0.6.0"]]

  :plugins [[lein-shell "0.4.0"]
            [lein-cljsbuild "1.1.2"]]

  :profiles {:e2e-test {:cljsbuild {:test-commands {"e2e-test" ["casperjs" ".e2e-test/target/app.js"]}
                                    :builds        [{:id           "e2e-test"
                                                     :source-paths ["src" "test"]
                                                     :compiler     {:main          beetlejuice.core-test
                                                                    :output-to     ".e2e-test/target/app.js"
                                                                    :output-dir    ".e2e-test/target/"
                                                                    :source-map    ".e2e-test/target/app.js.map"
                                                                    :optimizations :whitespace
                                                                    :pretty-print  false}}]}}}

  :aliases {"e2e-test"          ["do" "clean-e2e-test" "clean-e2e-test-SS" ["with-profiles" "-dev,+e2e-test" "do" ["cljsbuild" "test"]]]
            "clean-e2e-test"    ["do" "clean-e2e-test-SS" ["shell" "rm" "-rf" ".e2e-test"]]
            "clean-e2e-test-SS" ["do" ["shell" "rm" "-rf" "./target/e2e_test_screenshots"]]})
