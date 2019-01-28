(ns web-search.views.layout
  (:require [hiccup.page :as h])
  (:use [hiccup.page :only (html5 include-css include-js)]
        [hiccup.element :only (link-to)])
  )

(defn common [title & body]
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
     [:meta {:name "viewport" :content
                   "width=device-width, initial-scale=1, maximum-scale=1"}]
     [:title title]
     (h/include-css
       "/stylesheets/base.css"
       "/stylesheets/skeleton.css"
       "/stylesheets/screen.css"
                    )
     (h/include-css "http://fonts.googleapis.com/css?family=Sigmar+One&v1")

     ]
    [:body
     [:div {:id "header"}
      [:h1 {:class "container"} "Soyula"]]
     [:div {:id "content" :class "container"} body]]))

(defn not-found []
  [:br
   [:center
    [:div [:font {:size "9" :color "orange"} "Page Not Found"]]
    [:div [:font {:size "4" :color "red"} (link-to {:class "btn btn-primary"} "/" "Take me to Home")] ]
    ]
   ]
 )