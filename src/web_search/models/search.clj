(ns web-search.models.search
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [web-search.views.index :as view]
            [web-search.controllers.search-controller :as search-controller]
            [web-search.models.entity.pagination :refer :all]
            )
  (:import
    (web_search.models.entity.pagination pagination)
    )
  )

(def page-size 4)

(defn result
  [search-str pagination result-str]
  (view/index search-str pagination result-str))

(defn index []
  (view/index))

(defn search
  [id search-str]
  (cond (false? (str/blank? search-str))
    (let [total (search-controller/query-search-total search-str)
          pagination (pagination. (Integer/parseInt id) page-size total)
          result-str (search-controller/query-search search-str pagination)]
      (result search-str pagination result-str)
      )
        :else  (result search-str nil "nothing found")
    )
  )

(defroutes routes
           (GET  "/" [] (index))
           (GET  "/index/:id/:search-str" [id search-str] (search id (str search-str)))
           (POST "/" [search-str] (search "0" search-str))
           )