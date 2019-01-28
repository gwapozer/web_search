(ns web-search.core
  (:require [compojure.core :refer [defroutes]]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [web-search.models.search :as search-model]
            [web-search.views.layout :as layout])
  (:gen-class))

(defroutes routes
           search-model/routes
           (route/resources "/")
           (route/not-found  (layout/common "SOYULA"
                                            (layout/not-found)
                                            [:div {:class "clear"}])))

(def application (wrap-defaults routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
