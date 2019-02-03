(ns web-search.controllers.search-controller
  (:require [cljhelpers.dborm :as dborm]
            [cljhelpers.dborm :refer :all]
            [web-search.dbconn.conn :refer :all]
            [web-search.models.entity.web_list :refer :all]
            [web-search.models.entity.web_keyword :refer :all]
            [web-search.models.entity.web_searchlog :refer :all]
            [web-search.models.definition.web_searchlog_definition :refer :all]
            )
  (:import
    (web_search.models.entity.web_keyword web_keyword)
    (web_search.models.entity.web_list web_list)
    (web_search.models.entity.web_searchlog web_searchlog)
    (cljhelpers.dborm Where-Cl)
    (cljhelpers.dborm Limit-Cl Param-Cl)
    (java.sql Types))
  )

(defn- add-search-log
  [search-str]
  (def get-id (db-proc (db-spec) false "inter_serverGetKeyVal" [(Param-Cl. "in" "web_searchlog_id" nil)
                                                                         (Param-Cl. "out" "key" (Types/INTEGER))]))
  (def buff-web-searchlog (web_searchlog. (:out (first get-id) ) (str search-str) 1 1 nil nil))
  (dborm/db-insert (db-spec) web_searchlog_definition buff-web-searchlog)
  )

(defn- update-search-log
  [search-str]
  (let [buff-web-searchlog (web_searchlog. nil nil nil nil nil nil)
        web-searchlog-data (dborm/db-get (db-spec) buff-web-searchlog [(Where-Cl. "word_data" "=" (str search-str) nil nil)] nil)]
    (when (= (count web-searchlog-data) 1)
          (let [_ (dborm/db-update (db-spec)
                                   web_searchlog_definition
                                   (web_searchlog. (-> web-searchlog-data :id)
                                                   (-> web-searchlog-data :word_data)
                                                   (inc (-> web-searchlog-data :count))
                                                   (-> web-searchlog-data :status)
                                                   (-> web-searchlog-data :modified_date)
                                                   (-> web-searchlog-data :created_date))
                                   [(Where-Cl. "id" "=" (-> web-searchlog-data :id)  nil nil)])]))
    )
  )

(defn- get-kw-web-list
  [web_keyword_id pagination]
  (let [buff-web-list (web_list. nil nil nil nil nil nil nil nil nil nil)
        limit (:limit pagination)
        calc-paging (* (:curr pagination) (:limit pagination))
        offset (cond (> calc-paging (:total pagination)) (:total pagination) :else calc-paging)
        ]
    (dborm/db-get (db-spec) buff-web-list [(Where-Cl. "web_keyword_id" "=" web_keyword_id nil nil)] (Limit-Cl. limit offset))
    )
  )

(defn query-search-total [search-str]
  (let [kw-str (web_keyword. nil nil nil nil nil)
        kw-data (dborm/db-get (db-spec) kw-str [(Where-Cl. "keyword_data" "=" (str search-str) nil nil)] nil)]
    (cond (= (count kw-data) 1)
      (let [totalcnt (dborm/db-count (db-spec) (web_list. nil nil nil nil nil nil nil nil nil nil) "id" [(Where-Cl. "web_keyword_id" "="  (-> (first kw-data) :id) nil nil)])]
        (cond (> (count totalcnt) 0 ) (:count (first totalcnt)) :else 0)
        )
          :else 0
      )
    )
  )

(defn query-search [search-str pagination]
  (let [kw-str (web_keyword. nil nil nil nil nil)
        kw-data (dborm/db-get (db-spec) kw-str [(Where-Cl. "keyword_data" "=" (str search-str) nil nil)] nil)]
    (cond (= (count kw-data) 1)
          (let [_ (update-search-log search-str)]
            (get-kw-web-list (-> (first kw-data) :id) pagination))
          :else (let [_ (add-search-log search-str)] ""))
    )
  )