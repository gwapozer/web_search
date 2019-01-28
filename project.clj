(defproject web-search "0.1.0-SNAPSHOT"
  :description "Hiccup examples for Clojure Cookbook"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [hiccup "1.0.4"]
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [ring/ring-defaults "0.1.2"]
                 [cljhelpers "0.1.0-SNAPSHOT"]
                 ]
  :repl-options  {:init-ns web-search.core-test }
  :main web-search.core
  ;:resource-paths ["resources/libs/cljhelpers.jar"]
  )
