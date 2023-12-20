(ns hic-route.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hic-route.pages.seguranca :as sec]
            [hic-route.pages.tecidos :as tec]
            [hiccup.core :as h]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))



(def db [{:grupo "Grupo 1"
          :tipo "Tipo 1"
          :aves [1 2 3]
          :colheita ["Colheita 1" "14/04/2023"]}
         {:grupo "Grupo 2"
          :tipo "Tipo 2"
          :aves [4 5 6]
          :colheita ["Colheita 2" "13/04/2023"]}
         {:grupo "Grupo 3"
          :tipo "Tipo 3"
          :aves [7 8 9]
          :colheita ["Colheita 1" "21/12/2023"]}])


(def data-obj
  [{:grupos ["1"]
    :aves [1]
    :matrizes {:tipo "PROVA"
               :prova "12"}
    :etiqueta "grande"
    :offset "Offset 1"
    :project "Projeto 1"
    :date "01/01/2023"}

   {:grupos ["3"]
    :aves [3]
    :matrizes {:tipo "PROVA"
               :prova "11"}
    :etiqueta "media"
    :offset "Offset 2"
    :project "Projeto 3"
    :date "02/02/2023"}

   {:grupos ["5"]
    :aves [5]
    :matrizes {:tipo "CONTRAPROVA"
               :prova "10"}
    :etiqueta "pequena"
    :offset "Offset 3"
    :project "Projeto 5"
    :date "03/03/2023"}])

;(println (html-page db))

(defroutes app-routes
  (GET "/seguranca" [] (h/html (sec/html-page db)))
  (GET "/tecidos" [] (h/html (tec/html-page data-obj)))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
