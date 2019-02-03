(ns web-search.dbconn.conn
  (:require [cljhelpers.plugin :as pl]
            [clojure.java.io :as io]
            [cljhelpers.file :refer :all]
            [cljhelpers.eval-obj :as oe]
            [widget.evalobj-gui :as egui])
  )

(defn db-spec
  []

  (let [item (clojure.java.io/resource "prod.edn")
        my-config (readEdnFile item)
        db-driver (clojure.java.io/resource "mysql-connector-java-5.1.7-bin.jar")
        curr-driver-path (.getPath db-driver)
        hasJar? (clojure.string/index-of (str curr-driver-path) (str (:jar-name my-config)))
        driver-path (cond (nil? hasJar?) curr-driver-path
                         :else  (str (-> (java.io.File. "") .getCanonicalPath) "/db-drivers/mysql-connector-java-5.1.7-bin.jar"))]

    (pl/add-to-classpath driver-path)
    ;(pl/add-to-classpath "/Users/gwapozer/Applications/mysql-connector-java-5.1.7/mysql-connector-java-5.1.7-bin.jar")
    (:db-spec my-config)
    )

  ;{:dbtype "h2"
  ; :dbname "/Users/gwapozer/h2-database/my-db"
  ; :user "sa"
  ; :password ""
  ; }
  )