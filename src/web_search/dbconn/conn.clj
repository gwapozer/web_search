(ns web-search.dbconn.conn
  (:require [cljhelpers.plugin :as pl])
  )

(defn db-spec
  []
  (pl/add-to-classpath "/Users/tester/Applications/h2/bin/h2-1.4.197.jar")

  {:dbtype "h2"
   :dbname "/Users/tester/h2-database/my-db"
   :user "sa"
   :password ""
   })