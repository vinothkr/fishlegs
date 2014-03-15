(ns fishlegs.push-stats
  (use [incanter.io])
  (require [monger.collection :as mongo]))

(defn create-metric [tags metric value]
  {"tags" tags
   "metric" metric
   "value" value})

(defn get-data 
  [file-name tag-colums]
  (let [dataset (read-dataset file-name :header true :delim \tab)
        tag-keys (take tag-columns (:column-names dataset))
        metrics (drop tag-columns (:column-names dataset))]
    (mapcat (fn [row]
              (let [tags (map (fn[tag-key](tag-key row)) tag-keys)]
             (map (fn [metric] (create-metric tags metric (metric row))) metrics)))
            (:rows dataset))))


(defn push-data 
  ([file-name] (push-data file-name 1))
  ([file-name tag-columns] 
     (mongo/insert-batch "metric" (get-data file-name tag-columns))))

