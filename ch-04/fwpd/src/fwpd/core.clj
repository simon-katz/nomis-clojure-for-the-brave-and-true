(ns fwpd.core
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn my-mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [unmapped-rows]
  (letfn [(unmapped-row->map [unmapped-row]
            (into {}
                  (map (fn [vamp-key value]
                         [vamp-key (convert vamp-key value)])
                       vamp-keys
                       unmapped-row)))]
    (map unmapped-row->map
         unmapped-rows)))

(= (mapify (parse (slurp filename)))
   (my-mapify (parse (slurp filename))))
;; => true
