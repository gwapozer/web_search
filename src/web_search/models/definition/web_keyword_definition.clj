(ns web-search.models.definition.web_keyword_definition
  (:require [definition.table_struct :refer :all])
  (:import (definition.table_struct table-struct))
  )

(def web_keyword_definition #{(table-struct. 1 "ID" "ID" "INTEGER" 10 true false true)
                              (table-struct. 2 "keyword_data" "keyword_data" "VARCHAR" 255 false false false)
                              (table-struct. 3 "status" "status" "INTEGER" 11 false false false)
                              (table-struct. 4 "modified_date" "modified_date" "TIMESTAMP" nil false false false)
                              (table-struct. 5 "created_date" "created_date" "DATETIME" nil false false false)
                           })