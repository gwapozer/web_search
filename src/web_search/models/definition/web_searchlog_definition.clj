(ns web-search.models.definition.web_searchlog_definition
  (:require  [definition.table_struct :refer :all])
  (:import (definition.table_struct table-struct))
  )

(def web_searchlog_definition #{(table-struct. 1 "ID" "ID" "INTEGER" 10 true false true)
                                (table-struct. 2 "word_data" "keyword_data" "VARCHAR" 255 false false false)
                                (table-struct. 3 "count" "count" "INTEGER" 11 false false false)
                                (table-struct. 4 "status" "status" "INTEGER" 11 false false false)
                                (table-struct. 5 "modified_date" "modified_date" "TIMESTAMP" nil false false false)
                                (table-struct. 6 "created_date" "created_date" "DATETIME" nil false false false)
                              })