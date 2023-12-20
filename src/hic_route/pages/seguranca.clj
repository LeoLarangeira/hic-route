(ns hic-route.pages.seguranca
  (:require [hiccup.page :as page]
            [hiccup.core :as h]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(:import java.util.Date)

(def date (.format (java.text.SimpleDateFormat. "dd/MM/yyyy") (new java.util.Date)))





(defn row [db]
  ;função get para pegar os dados 
  (for [values db]
    [:div {:class "row"}
     [:div {:class "print"}
      [:p.code (values :grupo)]
      [:p.animal (str "AVE Nº " (str/join " " (:aves values)) " de " (values :tipo))]
      [:p.date (last (values :colheita)) [:span.day (take 2 date)]]]]))



(defn col-first [db]
  (let [overall-index (* (count (:tipo db)) (* (count (:grupo db)) (count (:colheita db))))]
    (cond
      (= (rem overall-index 77) 0)
      [:div {:class "col-first"}
       (row db)]
      (= (rem (+ overall-index 11) 21) 0)
      [:div {:class "col-last"}
       (row db)]
      :else
      [:div {:class "col-last"}
       (row db)])))

;(def style-x "@font-face  {\n  font-family: \"Arial Narrow\";\n  font-weight: normal;\n  src: url('../fonts/arialn.ttf') format('truetype');\n}\n@font-face  {\n  font-family: \"Arial Narrow\";\n  font-weight: bold;\n  src: url('../fonts/arialnb.ttf') format('truetype');\n}\nbody  {\n  margin: 0;\n  padding: 0;\n  display: flex;\n  font-family: \"Arial Narrow\";\n  text-transform: uppercase;\n}\n.col  {\n  padding: 0;\n  display: inline-block;\n  width: 26mm;\n  height: 132mm;\n  margin: 0 3.33mm 0 0;\n}\n.row  {\n  position: relative;\n  text-align: center;\n  float: left;\n  padding: 0;\n  display: block;\n  width: 100%;\n  height: 12mm;\n  margin: 0;\n  border: 1px solid rgba(255,255,255,0);\n}\n.print  {\n  position: relative;\n  top: 6mm;\n  margin-top: -4.5mm;\n}\n.code  {\n  font-size: 9px;\n}\n.animal  {\n  font-size: 10px;\n  font-weight: bold;\n}\n.date  {\n  font-size: 9px;\n}\n.day  {\n  display: inline;\n  margin-left: 3px;\n}\np  {\n  margin: 0;\n  padding: 0;\n  line-height: 1.3;\n}\n.last  {\n  margin: 0;\n}\n.first  {\n  margin-left: 0;\n}")
(def style (-> "public/seguranca.edn"
               (io/resource)
               (slurp)
               (edn/read-string)
               (first)))



(defn html-page [db]
  (str (page/html5 {:dir "ltr"}
                   [:head
                    [:meta {:charset "utf-8"}]
                    ;(page/include-css "../css/seguranca.css")
                    [:style
                     style]]
                   [:body (col-first db)])))