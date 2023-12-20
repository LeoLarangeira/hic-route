(ns hic-route.pages.tecidos
  (:require [hiccup.page :as page]
            [hiccup.core :as h]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as string]))

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
;(def get-grande (filter #(= "grande" (:etiqueta %)) data-obj))
;(type get-grande)



(defn row-print [values]
  (let [label-values (first values)]
    [:div.row
     [:div.print
      [:p.code (get label-values :project)]
      [:p.animal-id (str "AVE Nº " (string/join " e " (get label-values :aves)) " de grupos " (string/join " e " (get label-values :grupos)))]
      [:p.content (:tipo (:matrizes label-values))]
      [:p.proof (:prova (:matrizes label-values))]
      [:p.date [:div.day (take 2 (get label-values :date))] (get label-values :date)]]]))



(defn row-print-big [values]
  (let [label-values (first values)]
    [:div.row
     [:div.print
      [:p.code (get label-values :project)]
      [:p {:class "group-id big"} (str "GRUPO " (string/join " e " (get label-values :grupos)))]
      [:p {:class "proof big"} (:prova (:matrizes label-values))]
      [:p.date [:div.day (take 2 (get label-values :date))] (get label-values :date)]]]))




(defn col-first [db]

  (for [values db]

    (cond
      (= (string/upper-case (:etiqueta values)) "PEQUENA")
      (let [overall-index (* (count (:matrizes values))
                             (* (+ (count (:aves values)) (count (:matrizes values)))))
            filter-label (filter #(= "pequena" (:etiqueta %)) db)]
        (cond
          (= (rem overall-index 21) 0)
          (row-print filter-label)
          (= (rem (+ overall-index 7) 21) 0)
          (row-print filter-label)
          :else
          (row-print filter-label)))

      (= (string/upper-case (:etiqueta values)) "MEDIA")
      (let [overall-index (* (+ (count (:matrizes values))) (+ (count (:offset values))))
            filter-label (filter #(= "media" (:etiqueta %)) db)] ;futuramente adicionar o etiqueta/offset
        (cond
          (= (rem overall-index 21) 0)
          (row-print filter-label)
          (= (rem (+ overall-index 7) 21) 0)
          (row-print filter-label)
          :else
          (row-print filter-label)))

      (= (string/upper-case (:etiqueta values)) "GRANDE")
      (let [overall-index (* (+ (count (:matrizes values))) (+ (count (:offset values))))
            filter-label (filter #(= "grande" (:etiqueta %)) db)] ;futuramente adicionar o etiqueta/offset
        (cond
          (= (rem overall-index 21) 0)
          (row-print-big filter-label)
          (= (rem (+ overall-index 7) 21) 0)
          (row-print-big filter-label)
          :else
          (row-print-big filter-label)))
      :else
      (println "B.O"))))





(def style (-> "public/tecidos.edn"
               (io/resource)
               (slurp)
               (edn/read-string)
               (first)))














(defn html-page [db]
  (page/html5 {:dir "ltr"}
              [:head
               [:meta {:charset "utf-8"}]
               [:style
                style]]
              [:body [:div.col-first (col-first db)]]))