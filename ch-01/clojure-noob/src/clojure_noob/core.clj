(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "I'm a little teapot!"))

(println "Cleanliness is next to godliness")

(defn train
  []
  (println "Choo choo!"))

(+ 1 2 3 4 5 6)

(defn maybe-matching-part
  [part]
  (let [old-name (:name part)
        new-name (clojure.string/replace old-name #"^left-" "right-")]
    (when-not (= old-name new-name)
      {:name new-name
       :size (:size part)})))

(defn my-symmetrize-body-parts
  [asym-body-parts]
  (reduce into
          asym-body-parts
          (map #(let [p (maybe-matching-part %)]
                  (if p
                    [p]
                    []))
               asym-body-parts)))

(= (sort-by :name (symmetrize-body-parts asym-hobbit-body-parts))
   (sort-by :name (my-symmetrize-body-parts asym-hobbit-body-parts)))
