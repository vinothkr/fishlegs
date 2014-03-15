(ns fishlegs.query
  (require [monger.collection :as mongo]
           [monger.operators :refer :all]))

(def oplookup 
  {"<" $lt
   ">" $gt
   "<=" $lte
   ">=" $gte})


(defn metrics [metric & clauses]
  (mongo/find-maps "metric" (reduce conj (first clauses) {:metric metric})))

(defmacro having [field & opvalues]
  {field (reduce conj (map (fn[x] {(oplookup (str (first x))) (second x)}) opvalues))})
  
