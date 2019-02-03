(ns web-search.views.index
  (:require
    [web-search.views.layout :as layout]
    [hiccup.core :refer [h html]]
    [hiccup.form :as form]
    [ring.util.anti-forgery :as anti-forgery]
    [cljhelpers.eval-obj :as oe]
    ))


(defn search-form
  [search-str]
  [:div {:id "search-form" :class "sixteen columns alpha omega"}
   (form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 (form/label "find" "")
                 (form/text-area "search-str" (str search-str))
                 (form/submit-button "Find!"))])


(defn- page [search-str start-page end-page]
  (loop [i start-page vec-table [:tr ]]
    (if (< i end-page)
      (let [buff-anchor [:td {:width (cond (>= i 10) "35" :else "25")} [:a {:href (str "/index/" (str i) "/" search-str) :target "_self" } [:font {:size "5" :color "yellow"} (h (str i))]]] ]
        (recur (inc i) (conj vec-table buff-anchor)))
      vec-table
      ))
  )

(defn- display-pagination
  [search-str pagination]
  [:center
   [:div
    [:br
     [:table
      (let [
            total (int (/ (:total pagination) (:limit pagination)))
            range (:limit pagination)
            curr-page (:curr pagination)
            ]
        (if (> total range)
          (let [start-page (cond (<= curr-page range) 0 :else (- curr-page range))
                end-page (cond (>= (- total curr-page) range) (+ curr-page range) :else total)
                ]
           (page search-str start-page end-page)
            )
          (let [start-page 0
                end-page total]
            (page search-str start-page end-page)
            )
          )
        )
      ]
     ]
    ]
   ]
  )

(defn display-search [search-str pagination results]

   (cond (not= pagination nil)
         (do
           [:div
           (map (fn [result] [:div
                              [:br [:a {:href (str "http://" (:web_link result)) :target "_blank"} [:font {:size "5" :color "white"} (h (:web_title result))]]]
                              [:br [:font {:size "5" :color "red"} (h (:web_description result))]]
                              ]
                  ) results)

           (display-pagination search-str pagination)
           ]
           )
         :else [:div [:center  [:br [:font {:size "5" :color "red"} results ]]]]
         )
  )

(defn index
  ([]
   (layout/common "SOYULA"
                  (search-form "")
                  [:div {:class "clear"}]))
  ([search-str pagination results]
   (layout/common "SOYULA"
                  (search-form search-str)
                  [:div {:class "clear"}]
                  (display-search search-str pagination results))))
